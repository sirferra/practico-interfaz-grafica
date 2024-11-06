package negocio.Storage;

import java.util.Map;
public interface IStorage {
    public boolean create(String selector,Map<String,String> obj) throws Exception;
    public boolean delete(String selector,Object obj) throws Exception;
    public boolean update(String selector,Map<String,String> obj, int id) throws Exception;
    public boolean exists(String selector, Object obj) throws Exception;
    public Object get(String selector,int index, Class<?> type) throws Exception;
    public int getId(String selector, String field, String value) throws Exception;
    // public Object findByIndex(int index) throws Exception;
    public Object[] getAll(Class<?> type) throws Exception;
    public <T> String generateCsv(Class<T> type);
}
