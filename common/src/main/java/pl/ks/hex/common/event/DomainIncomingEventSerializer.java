package pl.ks.hex.common.event;

public interface DomainIncomingEventSerializer {
    byte[] serialize(DomainIncomingEvent domainIncomingEvent);

    DomainIncomingEvent deserialize(byte[] bytes);
}
