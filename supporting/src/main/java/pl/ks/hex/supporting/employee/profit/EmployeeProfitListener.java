package pl.ks.hex.supporting.employee.profit;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.ks.hex.common.model.Money;
import pl.ks.hex.employee.event.outgoing.NewInvoiceConfirmed;
import pl.ks.hex.supporting.pmo.PmoService;

@RequiredArgsConstructor
class EmployeeProfitListener {
    private final EmployeeProfitRepository employeeProfitRepository;
    private final PmoService pmoService;

    @EventListener
    public void listen(NewInvoiceConfirmed newInvoiceConfirmed) {
        EmployeeProfit profit = employeeProfitRepository.findById(newInvoiceConfirmed.getEmployeeId())
                .orElseGet(() -> {
                    EmployeeProfit newEntity = new EmployeeProfit();
                    newEntity.setId(newInvoiceConfirmed.getEmployeeId());
                    newEntity.setProfit(Money.of(BigDecimal.ZERO));
                    return newEntity;
                });

        Money pmoProfit = pmoService.calculateProfit(newInvoiceConfirmed.getEmployeeId(), newInvoiceConfirmed.getPayment());
        profit.setProfit(profit.getProfit().add(pmoProfit));
        employeeProfitRepository.save(profit);
    }
}
