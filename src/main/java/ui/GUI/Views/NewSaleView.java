package ui.GUI.Views;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.persona.Cliente;
import modelo.persona.Vendedor;
import modelo.producto.Producto;
import ui.GUI.MainGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewSaleView implements IView {
    private JComboBox<String> clienteComboBox;
    private JComboBox<String> vendedorComboBox;
    private JTextField fechaTextField;
    private JTable productosTable;
    private DefaultTableModel productosTableModel;
    private JButton addProductButton;
    private JButton saveButton;
    private List<?> clientes;
    private List<?> vendedores;
    private Map<String, Cliente> clienteMap;
    private Map<String, Vendedor> vendedorMap;

    public NewSaleView() {
        this.clientes = MainGUI.db.getAll(Cliente.class);
        this.vendedores = MainGUI.db.getAll(Vendedor.class);
        clienteMap = new HashMap<>();
        vendedorMap = new HashMap<>();
        for (Object obj : this.clientes) {
            Cliente cliente = (Cliente) obj;
            String nombreCompleto = cliente.getId() + ";" + cliente.getNombre() + " " + cliente.getApellido();
            clienteMap.put(nombreCompleto, cliente);
        }
        for (Object obj : this.vendedores) {
            Vendedor vendedor = (Vendedor) obj;
            String nombreCompleto =vendedor.getId() + ";" + vendedor.getNombre() + " " + vendedor.getApellido();
            vendedorMap.put(nombreCompleto, vendedor);
        }
        // Inicializar comboBoxes para cliente y vendedor con objetos completos
        clienteComboBox = new JComboBox<String>(clienteMap.keySet().toArray(new String[0]));
        vendedorComboBox = new JComboBox<String>(vendedorMap.keySet().toArray(new String[0]));
        // Campo de texto para la fecha
        fechaTextField = new JTextField(java.time.LocalDate.now().toString(), 10);

        // Configurar la tabla de productos
        String[] columnNames = {"ID", "Producto", "Cantidad", "Precio"};
        productosTableModel = new DefaultTableModel(columnNames, 0);
        productosTable = new JTable(productosTableModel);

        // Botones
        addProductButton = new JButton("Agregar Producto");
        saveButton = new JButton("Guardar Venta");

        // Acción para agregar productos
        addProductButton.addActionListener(e -> addProduct());

        // Acción para guardar la venta
        saveButton.addActionListener(e -> saveSale());
    }

    @Override
    public JPanel render() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Sección de Cliente y Vendedor
        JPanel clienteVendedorPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        clienteVendedorPanel.add(new JLabel("Cliente:"));
        clienteVendedorPanel.add(clienteComboBox);
        clienteVendedorPanel.add(new JLabel("Vendedor:"));
        clienteVendedorPanel.add(vendedorComboBox);
        clienteVendedorPanel.add(new JLabel("Fecha (dd/mm/yyyy):"));
        clienteVendedorPanel.add(fechaTextField);

        mainPanel.add(clienteVendedorPanel);

        // Tabla de productos
        JScrollPane productosScrollPane = new JScrollPane(productosTable);
        productosScrollPane.setPreferredSize(new Dimension(400, 150));
        mainPanel.add(productosScrollPane);

        // Botones de agregar producto y guardar
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(addProductButton);
        buttonsPanel.add(saveButton);
        mainPanel.add(buttonsPanel);

        return mainPanel;
    }

    private void addProduct() {
         // Crear algunos productos de ejemplo para el diálogo (esto debería obtenerse de la base de datos)
        List<?> productos = MainGUI.db.getAll(Producto.class);

        // Crear y mostrar el diálogo
        ProductSelectionDialog dialog = new ProductSelectionDialog(null, (List<?>) productos);
        dialog.setVisible(true);

        // Obtener el producto seleccionado
        Producto selectedProduct = dialog.getSelectedProduct();
        if (selectedProduct != null) {
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad:"));
            double precio = selectedProduct.getPrecio();
            double total = cantidad * precio;
            productosTableModel.addRow(new Object[]{selectedProduct.getId(),selectedProduct.getNombre(), cantidad, total});
        }
    }

    private void saveSale() {
        String clienteId = clienteComboBox.getSelectedItem().toString().split(";")[0].trim(); // de esta forma obtengo el id que mapeo al principio
        String vendedorId = vendedorComboBox.getSelectedItem().toString().split(";")[0].trim();
        String fecha = fechaTextField.getText().trim();

        // Validar que todos los campos estén completos
        if (clienteId == null || vendedorId == null || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
            return;
        }

        // Verificar si hay productos agregados
        int rowCount = productosTableModel.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(null, "Debe agregar al menos un producto.");
            return;
        }
        // Guardar en la base de datos (esto es solo un ejemplo)
        try {
            MainGUI.db.saveSale(clienteId, vendedorId, fecha, getProductosFromTable());
            JOptionPane.showMessageDialog(null, "Venta guardada con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la venta: " + e.getMessage());
        }
    }

    private List<Object[]> getProductosFromTable() {
        List<Object[]> productos = new ArrayList<>();
        for (int i = 0; i < productosTableModel.getRowCount(); i++) {
            int id = (int) productosTableModel.getValueAt(i, 0);
            String producto = (String) productosTableModel.getValueAt(i, 1);
            int cantidad = (int) productosTableModel.getValueAt(i, 2);
            double precio = (double) productosTableModel.getValueAt(i, 3);
            double total = cantidad * precio;
            productos.add(new Object[]{id, producto, cantidad, total});
        }
        return productos;
    }
}
