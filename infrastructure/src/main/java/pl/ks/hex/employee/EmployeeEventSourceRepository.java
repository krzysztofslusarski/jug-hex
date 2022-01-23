package pl.ks.hex.employee;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import pl.ks.hex.common.event.DomainIncomingEvent;
import pl.ks.hex.common.event.DomainIncomingEventSerializer;

@Slf4j
@RequiredArgsConstructor
class EmployeeEventSourceRepository implements EmployeeRepository {
    private final EntityManager entityManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmployeeEventStreamRepositoryJpa employeeEventStreamRepositoryJpa;
    private final DomainIncomingEventSerializer domainIncomingEventSerializer;

    @Override
    public Employee getById(EmployeeId employeeId) {
        EmployeeEventStream eventStream = employeeEventStreamRepositoryJpa.getById(employeeId.getValue());
        return lockAndMap(eventStream);
    }

    @Override
    public List<Employee> findAll() {
        return employeeEventStreamRepositoryJpa.findAll().stream()
                .map(this::lockAndMap)
                .toList();
    }

    private Employee lockAndMap(EmployeeEventStream employeeEventStream) {
        entityManager.lock(employeeEventStream, LockModeType.OPTIMISTIC);

        return Employee.recreate(
                employeeEventStream.getEvents().stream()
                        .map(EmployeeEvent::getContent)
                        .map(domainIncomingEventSerializer::deserialize)
                        .sorted(Comparator.comparingInt(DomainIncomingEvent::getSequenceNumber))
                        .collect(Collectors.toList()),
                employeeEventStream.getVersion()
        );
    }

    @Override
    public void save(Employee employee) {
        EmployeeEventStream stream = employeeEventStreamRepositoryJpa
                .findById(employee.getId().getValue())
                .orElseGet(() -> {
                    EmployeeEventStream employeeEventStream = new EmployeeEventStream();
                    employeeEventStream.setEvents(new HashSet<>());
                    employeeEventStream.setEmployeeId(employee.getId().getValue());
                    return employeeEventStream;
                });

        stream.getEvents().addAll(
                employee.getPendingEvents().stream()
                        .map(domainIncomingEventSerializer::serialize)
                        .map(bytes -> {
                            EmployeeEvent employeeEvent = new EmployeeEvent();
                            employeeEvent.setContent(bytes);
                            return employeeEvent;
                        })
                        .toList()
        );

        employee.getPendingEvents().clear();

        employee.getEventsToPublish().forEach(event -> {
            try {
                log.info("Publishing: {}", event);
                applicationEventPublisher.publishEvent(event);
            } catch (RuntimeException e) {
                log.warn("Ignoring exception", e);
            }
        });
        employee.getEventsToPublish().clear();
        employeeEventStreamRepositoryJpa.save(stream);
    }
}
