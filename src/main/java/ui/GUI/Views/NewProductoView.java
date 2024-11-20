package ui.GUI.Views;

import java.awt.Window;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import modelo.persona.Proveedor;
import modelo.producto.Categoria;
import modelo.producto.Modelo;
import ui.GUI.MainGUI;

public class NewProductoView implements IView {
    private JComboBox<Categoria> categoryComboBox;
    private JComboBox<Modelo> modelComboBox;
    private JComboBox<Proveedor> proveedorComboBox;
    private JTextField nombreTextField;
    private JTextField codigoTextField;
    private JTextField descripcionTextField;
    private JTextField precioTextField;
    private JTextField stockTextField;
    private List<?> categories;
    private List<?> modelos;
    private List<?> proveedores;

    public NewProductoView() {
        this.categories = MainGUI.db.getAll(Categoria.class);
        this.modelos =  MainGUI.db.getAll(Modelo.class);
        this.proveedores =  MainGUI.db.getAll(Proveedor.class);
    }

    public JPanel render() {
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel para los campos del formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Campo: Nombre
        formPanel.add(new JLabel("Nombre:"));
        nombreTextField = new JTextField(20);
        formPanel.add(nombreTextField);

        // Campo: Código
        formPanel.add(new JLabel("Código:"));
        codigoTextField = new JTextField(20);
        formPanel.add(codigoTextField);

        // Campo: Descripción
        formPanel.add(new JLabel("Descripción:"));
        descripcionTextField = new JTextField(20);
        formPanel.add(descripcionTextField);

        // Campo: Stock
        formPanel.add(new JLabel("Stock:"));
        stockTextField = new JTextField(10);
        formPanel.add(stockTextField);
        
        // Campo: Precio
        formPanel.add(new JLabel("Precio:"));
        precioTextField = new JTextField(20);
        formPanel.add(precioTextField);

        // Campo: Categoría
        formPanel.add(new JLabel("Categoría:"));
        categoryComboBox = new JComboBox<>(categories.toArray(new Categoria[0]));
        formPanel.add(categoryComboBox);

        // Campo: Proveedor
        formPanel.add(new JLabel("Proveedor:"));
        proveedorComboBox = new JComboBox<>(proveedores.toArray(new Proveedor[0]));
        formPanel.add(proveedorComboBox);

        // Campo: Modelo
        formPanel.add(new JLabel("Modelo:"));
        modelComboBox = new JComboBox<>(modelos.toArray(new Modelo[0]));
        formPanel.add(modelComboBox);

        // Botón de confirmación
        JButton confirmButton = new JButton("Guardar Producto");
        confirmButton.addActionListener(e -> {
            // Aquí manejas el evento del botón
            String nombre = nombreTextField.getText();
            String codigo = codigoTextField.getText();
            String descripcion = descripcionTextField.getText();
            String precioStr = precioTextField.getText();
            String stockStr = stockTextField.getText();
            Categoria categoria = (Categoria) categoryComboBox.getSelectedItem();
            Proveedor proveedor = (Proveedor) proveedorComboBox.getSelectedItem();
            Modelo modelo = (Modelo) modelComboBox.getSelectedItem();

            if (nombre.isEmpty() || codigo.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double precio = Double.parseDouble(precioStr);
                // Inserta el producto en la base de datos
                String sql = "INSERT INTO producto (nombre, codigo, descripcion, precio, categoria_id, proveedor_id, modelo_id, stock) " +
                             "VALUES ('" + nombre + "', '" + codigo + "', '" + descripcion + "', " + precio + ", " +
                             categoria.getId() + ", " + proveedor.getId() + ", " + modelo.getId()+ ",'" + stockStr +"')";
                MainGUI.db.execute(sql);
                JOptionPane.showMessageDialog(mainPanel, "Producto guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                Window parentWindow = SwingUtilities.getWindowAncestor(mainPanel);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Agregar los componentes al panel principal
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(confirmButton);

        return mainPanel;
    }
}
