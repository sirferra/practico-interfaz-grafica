package ui.GUI.Views;

import java.awt.*;
import javax.swing.*;

import modelo.persona.Cliente;
import modelo.persona.Proveedor;
import modelo.persona.Vendedor;
import ui.GUI.MainGUI;
import ui.GUI.Constants.*;

public class NewPersonaView implements IView{
    private TABLES parentTable;
    private JPanel mainPanel;
    public NewPersonaView(TABLES table){
        parentTable = table;
    }

    @Override
    public JPanel render() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel inputsPanel = configureInputPanel();
        mainPanel.add(inputsPanel);

        JButton saveButton = new JButton("Guardar");
        saveButton.setMargin(new Insets(10, 10, 10, 10));
        saveButton.setIconTextGap(10);
        saveButton.setContentAreaFilled(false);
        saveButton.setBackground(Colors.getMorningGlory("500"));
        saveButton.setOpaque(true);
        saveButton.setForeground(Colors.getMorningGlory("50"));
        saveButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setOpaque(false);
        saveButton.setBorderPainted(false);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Para el borde redondeado del botón
        saveButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(saveButton.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20); 
                super.paint(g2, c);
                g2.dispose();
            }
        });
        saveButton.addActionListener(e -> {
            try{
                MainGUI.db.create(getDataForCreatePersona());
            }catch(Exception ex){
                ex.printStackTrace();
            }

        });

        mainPanel.add(saveButton, BorderLayout.PAGE_END);
        this.mainPanel = mainPanel;
        return mainPanel;
    }
    public JPanel configureInputPanel(){
        JPanel inputsPanel = new JPanel();
        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
        inputsPanel.add(new JLabel("Nombre:"));
        JTextField nombre = new JTextField();
        nombre.setName("nombre");
        inputsPanel.add(nombre);
        inputsPanel.add(new JLabel("Apellido:"));
        JTextField apellido = new JTextField();
        apellido.setName("apellido");
        inputsPanel.add(apellido);
        inputsPanel.add(new JLabel("DNI:"));
        JTextField dni = new JTextField();
        dni.setName("dni");
        inputsPanel.add(dni);
        inputsPanel.add(new JLabel("Telefono:"));
        JTextField telefono = new JTextField();
        telefono.setName("telefono");
        inputsPanel.add(telefono);
        inputsPanel.add(new JLabel("Email:"));
        JTextField email = new JTextField();
        email.setName("email");
        inputsPanel.add(email);
        // Si es cliente
        if(parentTable == TABLES.CLIENTS){
            inputsPanel.add(new JLabel("CUIL:"));
            JTextField cuil = new JTextField();
            cuil.setName("cuil");
            inputsPanel.add(cuil);
        }
        // Si es vendedor
        if(parentTable == TABLES.PROVIDERS){
            inputsPanel.add(new JLabel("Nombre Fantasia:"));
            JTextField nombreFantasia = new JTextField();
            nombreFantasia.setName("nombreFantasia");
            inputsPanel.add(nombreFantasia);
            inputsPanel.add(new JLabel("CUIT:"));
            JTextField cuit = new JTextField();
            cuit.setName("cuit");
            inputsPanel.add(cuit);
        }
        // Si es proveedor
        if (parentTable == TABLES.SELLER) {
            inputsPanel.add(new JLabel("Sucursal:"));
            JTextField sucursal = new JTextField();
            sucursal.setName("sucursal");
            inputsPanel.add(sucursal);
        }
        return inputsPanel;
    }
    

    public Object getDataForCreatePersona(){
        String nombre = ((JTextField) this.mainPanel.getComponent(1)).getText();
        String apellido = ((JTextField) this.mainPanel.getComponent(3)).getText();
        String dni = ((JTextField) this.mainPanel.getComponent(5)).getText();
        String telefono = ((JTextField) this.mainPanel.getComponent(7)).getText();
        String email = ((JTextField) this.mainPanel.getComponent(9)).getText();
        Object objeto = null;
        if(parentTable == TABLES.CLIENTS){
            String cuil = ((JTextField) this.mainPanel.getComponent(11)).getText();
            objeto = new Cliente(cuil,nombre, apellido,Integer.parseInt(dni), telefono, email);
        }
        if(parentTable == TABLES.SELLER){
            String sucursal = ((JTextField) this.mainPanel.getComponent(11)).getText();
            objeto = new Vendedor(sucursal,nombre, apellido,Integer.parseInt(dni), telefono, email);
            
        }
        if(parentTable == TABLES.PROVIDERS){
            String cuit = ((JTextField) this.mainPanel.getComponent(11)).getText();
            String nombreFantasia = ((JTextField) this.mainPanel.getComponent(13)).getText();
            objeto = new Proveedor(nombreFantasia, cuit,nombre, apellido,Integer.parseInt(dni), telefono, email);
        }
        return objeto;
    }
}
