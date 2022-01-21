package pl.ks.hex.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

@RequiredArgsConstructor
public class InMemoryJpaRepository<T, ID> implements JpaRepository<T, ID> {
    protected final Map<ID, T> database = new HashMap<>();

    private final Function<T, ID> idGetter;

    @Override
    public List<T> findAll() {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public List<T> findAll(Sort sort) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void flush() {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void deleteAllInBatch(Iterable<T> entities) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ID> ids) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void deleteAllInBatch() {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public T getOne(ID id) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public T getById(ID id) {
        T value = database.get(id);
        if (value == null) {
            throw new EntityNotFoundException();
        }
        return value;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> S save(S entity) {
        database.put(idGetter.apply(entity), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public boolean existsById(ID id) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public long count() {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void deleteById(ID id) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void delete(T entity) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public void deleteAll() {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        throw new ImplementMePleaseException("please please please");
    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new ImplementMePleaseException("please please please");
    }
}
