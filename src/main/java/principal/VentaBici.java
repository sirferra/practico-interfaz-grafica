package principal;

import negocio.mysql.ConnectionHolder;
import negocio.mysql.SimpleORM;
import ui.CLI;
import ui.IUi;

public class VentaBici {
    
    public static IUi renderUI;
    
    public static void main(String[] args) {
        SimpleORM db = getOrm();
        if(args.length > 0){
            System.out.println("Se han recibido argumentos:");
            System.out.println(args[0]);
        }
        // @TODO generate code to use cli or gui
        renderUI = new CLI(db);
    }   
    public static SimpleORM getOrm() {
        return ConnectionHolder.getInstance();
    }
}
