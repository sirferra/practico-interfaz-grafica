package negocio.Storage;

import java.util.ArrayList;

public interface IStorage {
    public void create(Object obj) throws Exception;
    public void delete(Object obj) throws Exception;
    public void update(Object obj) throws Exception;
    public boolean exists(Object obj) throws Exception;
    public Object get(int index, Class<?> type) throws Exception;
    public int getIndexOf(Object obj) throws Exception;
    // public Object findByIndex(int index) throws Exception;
    public ArrayList<?> getAll(Class<?> type) throws Exception;
    public <T> String generateCsv(Class<T> type);

}
