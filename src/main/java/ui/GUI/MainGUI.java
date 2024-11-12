package ui.GUI;
import javax.swing.*;
import negocio.mysql.SimpleORM;
import ui.IUi;
public class MainGUI implements IUi {
    public static SimpleORM db;
    
    public MainGUI(SimpleORM db) {
        MainGUI.db = db;
        this.render();
    }

    @Override
    public void render() {
        SwingUtilities.invokeLater(() -> new MainUI(Views.HOME));
    }
    
}
