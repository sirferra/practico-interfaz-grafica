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
    JPanel mainPanel;
    JTextField nombre;
    JTextField apellido;
    JTextField dni;
    JTextField telefono;
    JTextField email;
    JTextField cuil;
    JTextField nombreFantasia;
    JTextField cuit;
    JTextField sucursal;
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
                JOptionPane.showMessageDialog(null, this.parentTable.getName() + " guardado con éxito.");
                Window parentWindow = SwingUtilities.getWindowAncestor(mainPanel);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }
            }catch(Exception ex){
                System.out.println("Error: " + ex.getLocalizedMessage());
                //ex.printStackTrace();
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
        nombre = new JTextField();
        nombre.setName("nombre");
        inputsPanel.add(nombre);
        inputsPanel.add(new JLabel("Apellido:"));
        apellido = new JTextField();
        apellido.setName("apellido");
        inputsPanel.add(apellido);
        inputsPanel.add(new JLabel("DNI:"));
        dni = new JTextField();
        dni.setName("dni");
        inputsPanel.add(dni);
        inputsPanel.add(new JLabel("Telefono:"));
        telefono = new JTextField();
        telefono.setName("telefono");
        inputsPanel.add(telefono);
        inputsPanel.add(new JLabel("Email:"));
        email = new JTextField();
        email.setName("email");
        inputsPanel.add(email);
        // Si es cliente
        if(parentTable == TABLES.CLIENTS){
            inputsPanel.add(new JLabel("CUIL:"));
            cuil = new JTextField();
            cuil.setName("cuil");
            inputsPanel.add(cuil);
        }
        // Si es vendedor
        if(parentTable == TABLES.PROVIDERS){
            inputsPanel.add(new JLabel("Nombre Fantasia:"));
            nombreFantasia = new JTextField();
            nombreFantasia.setName("nombreFantasia");
            inputsPanel.add(nombreFantasia);
            inputsPanel.add(new JLabel("CUIT:"));
            cuit = new JTextField();
            cuit.setName("cuit");
            inputsPanel.add(cuit);
        }
        // Si es proveedor
        if (parentTable == TABLES.SELLER) {
            inputsPanel.add(new JLabel("Sucursal:"));
            sucursal = new JTextField();
            sucursal.setName("sucursal");
            inputsPanel.add(sucursal);
        }
        return inputsPanel;
    }
    

    public Object getDataForCreatePersona() throws Error{
        if(nombre.getText().isEmpty() || apellido.getText().isEmpty() || dni.getText().isEmpty() || telefono.getText().isEmpty() || email.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Es necesario que completes todos los campos");
            throw new Error("Es necesario que completes todos los campos");
        }
        String nombre = this.nombre.getText();
        String apellido = this.apellido.getText();
        String dni = this.dni.getText();
        String telefono = this.telefono.getText();
        String email = this.email.getText();
        Object objeto = null;
        if(parentTable == TABLES.CLIENTS){
            if(cuil.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Es necesario que completes todos los campos");
                throw new Error("Es necesario que completes todos los campos");
            }
            String cuil = this.cuil.getText();
            objeto = new Cliente(cuil,nombre, apellido,Integer.parseInt(dni), telefono, email);
        }
        if(parentTable == TABLES.SELLER){
            if(sucursal.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Es necesario que completes todos los campos");
                throw new Error("Es necesario que completes todos los campos");
            }
            String sucursal = this.sucursal.getText();
            objeto = new Vendedor(sucursal,nombre, apellido,Integer.parseInt(dni), telefono, email);   
        }
        if(parentTable == TABLES.PROVIDERS){
            if(cuit.getText().isEmpty() || nombreFantasia.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Es necesario que completes todos los campos");
                throw new Error("Es necesario que completes todos los campos");
            }
            String cuit = this.cuit.getText();
            String nombreFantasia = this.nombreFantasia.getText();
            objeto = new Proveedor(nombreFantasia, cuit,nombre, apellido,Integer.parseInt(dni), telefono, email);
        }
        return objeto;
    }
}
