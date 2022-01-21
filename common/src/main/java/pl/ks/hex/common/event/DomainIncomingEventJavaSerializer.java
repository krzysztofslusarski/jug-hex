package pl.ks.hex.common.event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DomainIncomingEventJavaSerializer implements DomainIncomingEventSerializer {
    @Override
    public byte[] serialize(DomainIncomingEvent domainIncomingEvent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(domainIncomingEvent);
            objectOutputStream.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Cannot serialize", ex);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public DomainIncomingEvent deserialize(byte[] bytes) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (DomainIncomingEvent) objectInputStream.readObject();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot deserialize", ex);
        }
    }
}
