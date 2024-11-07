package principal;

import negocio.mysql.ConnectionHolder;
import negocio.mysql.SimpleORM;
import ui.CLI;
import ui.IUi;

public class VentaBici {
    
    public static IUi renderUI;
    

    public static void main(String[] args) {

        if(args.length > 0){
            System.out.println("Se han recibido argumentos:");
            System.out.println(args[0]);
        }
        renderUI = new CLI(getOrm());
    }   
    public static SimpleORM getOrm() {
        return ConnectionHolder.getInstance();
    }
}
