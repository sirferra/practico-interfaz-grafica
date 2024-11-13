package principal;

import negocio.mysql.ConnectionHolder;
import negocio.mysql.SimpleORM;
import ui.*;
import ui.GUI.MainGUI;

public class VentaBici {
    
    public static IUi renderUI;
    
    public static void main(String[] args) {
        SimpleORM db = getOrm();
        
        //renderUI = new CLI(db);
        renderUI = new MainGUI(db);
    }   
    public static SimpleORM getOrm() {
        return ConnectionHolder.getInstance();
    }
}
