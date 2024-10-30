package repositorio;

import modelo.persona.Cliente;
import modelo.persona.Persona;
import modelo.persona.Proveedor;
import modelo.persona.Vendedor;

import java.util.ArrayList;
import java.util.stream.Collectors;

import modelo.pedido.Pedido;

public abstract class RepositorioDeDatos {
    public static String[] names = {"Juan","Pablo","José","María","Carlos","Raquel","Marta","Ricardo","Miguel","Mercedez"};
    public static String[] lastNames = {"Perez", "Rodriguez", "Ramirez", "Rojas", "Lopez", "Garcia", "Sanchez", "Torres", "Hernandez", "Pinto"};
    public static String[] fantasyName = {"Frontier", "Star", "Infinite", "Soul", "BuildTeam", "R. SA", "Indigo", "Context", "Fusion", "Asociación"};
    public static String[] emailProviders ={"@gmail.com", "@hotmail.com", "@yahoo.com.ar"};
    public static String[] sucursal ={"Centro","Cerro", "Zona norte", "Zona Sur", "9 de julio","San martin", "Cordoba", "Villa Maria", "Mar del Plata", "Carlos Paz"};

    public static final int CANTIDAD_PERSONA_MAXIMA=10;
    public static ArrayList<Persona> arrPersonas= new ArrayList<Persona>();
    public static ArrayList<Pedido> arrPedidos = new ArrayList<Pedido>();

    public static void add(Object object){
        if(object instanceof Persona){
            arrPersonas.add((Persona)object);
        }else{
            arrPedidos.add((Pedido)object);
        }
    }
    public static void remove(Object object){
        if(object instanceof Persona){
            arrPersonas.remove((Persona)object);
        }else{
            arrPedidos.remove((Pedido)object);
        }
    }
    public static int indexOf(Object object){
        if(object instanceof Persona){
            return arrPersonas.indexOf(object);
        }else{
            return arrPedidos.indexOf(object);
        }
    }
    public static Object set(int index,Object object){
        if(object instanceof Persona){
            return arrPersonas.set(index, (Persona)object);
        }else{
            return arrPedidos.set(index,(Pedido) object);
        }
    }
    public static boolean exists(Object object){
        return RepositorioDeDatos.indexOf(object) != -1;
    }

    public static Object get(int index, Class<?> type){
        if(type == Persona.class){
            return arrPersonas.get(index);
        }else{
            return arrPedidos.get(index);
        }
    }

    public static ArrayList<?> getAll(Class<?> type){
        if(type == Persona.class){
            return arrPersonas;
        }else{
            return arrPedidos;
        }
    }
        

    public static String generateName(){
        return RepositorioDeDatos.names[(int)(Math.random()*10)];
    }

    public static String generateLastName(){
        return RepositorioDeDatos.lastNames[(int)(Math.random()*10)];
    }
    public static String generateFantasyName(){
        return RepositorioDeDatos.fantasyName[(int)(Math.random()*10)];
    }
    public static String generateEmail(String name){
        return name+RepositorioDeDatos.emailProviders[(int)(Math.random()*3)];
    }
    public static String generateCuil(boolean isProvider,int dni){
        return isProvider?"30":"20"+ "-" +dni+'-'+((int)Math.random()*99999999);
    }
    public static int generateDni(){
        return (int)(Math.random()*99999999);
    }
    public static String generateCode(){
        return String.valueOf((int)(Math.random()*99999999));
    }

    public static String generatePhone() {
        int[] validPhoneInitators = {3,5,6,7};
        return "351" + validPhoneInitators[(int)(Math.random()*4)] + ((int)(Math.random()*999999));
    }

    public static String generateSucursal(){
        return RepositorioDeDatos.sucursal[(int)(Math.random()*10)];
    }
    public static <T> String generateCsv(Class<T> type){
        if(type == Persona.class){ 
            String headers= "";
            Class<?> filter = null;
            if (type == Cliente.class) {
                headers = Cliente.getHeaders();
                filter = Cliente.class;
            } else if (type == Vendedor.class) {
                headers = Vendedor.getHeaders();
                filter = Vendedor.class;
            } else if (type == Proveedor.class) {
                headers = Proveedor.getHeaders();
                filter = Proveedor.class;
            }
            if(filter == null){
                throw new IllegalArgumentException("El tipo de persona no es valido");
            }
            final Class<?> finalFilter = filter;
            return headers
            +"\n"            +RepositorioDeDatos.arrPersonas
                .stream()
                .filter(p->finalFilter.isInstance(p))
                .map(p-> p.toString())
                .collect(Collectors.joining("\n"));
        }else{
            return "";
        }
    }
}
