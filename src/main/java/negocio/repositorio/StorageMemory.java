package negocio.repositorio;

import java.util.ArrayList;

import negocio.Storage.IStorage;
import repositorio.RepositorioDeDatos;

/** esta debe guardar en disco, las otras no */
public class StorageMemory implements IStorage {

    public void create(Object obj) throws Exception{
        RepositorioDeDatos.add(obj);
    };
    public void delete(Object obj) throws Exception{
        int index =  this.getIndexOf(obj);
        RepositorioDeDatos.remove(index);
    };
    public void update(Object obj) throws Exception{
        int index =  this.getIndexOf(obj);
        RepositorioDeDatos.set(index, obj);
    };
    public boolean exists(Object obj) throws Exception{
        return RepositorioDeDatos.exists(obj);
    };
    public int getIndexOf(Object obj) throws Exception{
        return RepositorioDeDatos.indexOf(obj);
    };

    // public Object getById(Object obj) throws Exception{
    //     return RespositorioDeDatos.getById(id);
    // }
    // public Object findByIndex(int index) throws Exception{
    //     return RespositorioDeDatos.getByIndex(index);
    // };

    /**
     * Obtiene todos los objectos, se le debe pasar una clase para que pueda buscar por tipos
     * @param object
     * @return Object[]
     */
    public ArrayList<?> getAll(Class<?> type) throws Exception{
        return RepositorioDeDatos.getAll(type);
    }
    public Object get(int index, Class<?> type) throws Exception {
        return RepositorioDeDatos.get(index, type);
    }
    @Override
    public <T>String generateCsv(Class<T> clase) {
        return RepositorioDeDatos.generateCsv(clase);
    };
}
