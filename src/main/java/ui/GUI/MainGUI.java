package ui.GUI;
import javax.swing.*;
import negocio.mysql.SimpleORM;
import ui.IUi;
import ui.GUI.Views.HomeView;
public class MainGUI implements IUi {
    public static SimpleORM db;
    
    public MainGUI(SimpleORM db) {
        MainGUI.db = db;
        this.render();
    }

    @Override
    public void render() {
        SwingUtilities.invokeLater(() -> new MainUI(new HomeView()));
    }
    
}
