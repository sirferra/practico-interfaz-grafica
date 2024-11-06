package negocio.mysql;

public interface IMysqlEntity {
    public static String table = "";
    public static boolean create(Object obj){return true;}
    public static boolean update(Object oldObj, Object newObj){return true;}
    public static boolean update(int id,Object newObj){return true;}
    public static boolean delete(Object obj){return true;}
    public static boolean exists(Object obj){return true;}
    public static Object get(int id) throws Exception{return true;}
    public static Object get(String something) throws Exception{return true;}
    public static Object getId(String something) throws Exception{return true;}
    public static Object getAll(){return true;}

}