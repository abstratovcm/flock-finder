import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface GenericRepository<T, ID> {
    T save(T entity);
    T saveOrUpdate(T entity);
    public T updateAttributeById(ID id, Consumer<T> updateFunction);
    public T update(T entity);
    Optional<T> findById(ID id);
    void delete(T entity);
    void deleteById(ID id);
    public List<T> findAll();
}
