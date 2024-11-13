package ui.GUI.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.producto.Producto;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSelectionDialog extends JDialog {
    private JTextField filterField;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private List<?> allProducts;  // Lista original de productos
    private Producto selectedProduct;

    public ProductSelectionDialog(JFrame parent, List<?> products) {
        super(parent, "Seleccionar Producto", true);
        this.allProducts = products;
        this.selectedProduct = null;

        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Campo de texto para filtrar productos
        filterField = new JTextField();
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterProducts(filterField.getText());
            }
        });

        add(filterField, BorderLayout.NORTH);

        // Tabla de productos
        String[] columnNames = {"ID", "Name", "Precio"};
        productTableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(productTableModel);
        loadProducts(products); // Cargar todos los productos al inicio

        // Evento de clic en la tabla
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = productTable.getSelectedRow();
                if (row != -1) {
                    int productId = (int) productTableModel.getValueAt(row, 0);
                    double price = (double) productTableModel.getValueAt(row, 2);
                    selectedProduct = new Producto(productId, (String) productTableModel.getValueAt(row, 1), price);
                    dispose();  // Cerrar el diálogo después de seleccionar el producto
                }
            }
        });

        add(new JScrollPane(productTable), BorderLayout.CENTER);
    }

    // Filtrar productos y actualizar la tabla
    private void filterProducts(String filterText) {
        List<Producto> filteredProducts = allProducts.stream()
            .map(obj-> (Producto) obj)
            .filter(p -> p.getNombre().toLowerCase().contains(filterText.toLowerCase()))
            .collect(Collectors.toList());
        loadProducts(filteredProducts);
    }

    // Cargar productos en la tabla
    private void loadProducts(List<?> products) {
        productTableModel.setRowCount(0); // Limpiar la tabla
        for (Object obj : products) {
            Producto product = (Producto) obj;
            productTableModel.addRow(new Object[]{product.getId(), product.getNombre(), product.getPrecio()});
        }
    }

    public Producto getSelectedProduct() {
        return selectedProduct;
    }
}

