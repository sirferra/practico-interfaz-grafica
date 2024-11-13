package ui.GUI.Views;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;

import modelo.pedido.DetallePedido;
import modelo.pedido.Pedido;
import ui.GUI.MainGUI;
import ui.GUI.Constants.Colors;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SalesView implements IView {
    Object[][] ventas = new Object[][]{};
    Object[][] detalles = new Object[][]{};
    JPanel ventasPanel = null;
    String total = "0.00";
    String mostSoldProduct = "-";
    int rowSelected = -1;
    @Override
    public JPanel render() {
        JPanel mainPanel = new JPanel();
        mostSoldProduct = MainGUI.db.getProductoMasVendido();
        setVentas();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel vendedorPanel = createSection("Vendedor", "Nombre", "Apellido");
        mainPanel.add(vendedorPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel clientePanel = createSection("Cliente", "Nombre", "Apellido");
        mainPanel.add(clientePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel ventaPanel = createVentaSection();
        mainPanel.add(ventaPanel);
        return mainPanel;
    }

    private void setVentas(){
        String sql = "SELECT p.id,Concat(c.nombre, ' ', c.apellido) as cliente,Concat(v.nombre, ' ', v.apellido) as vendedor, p.total, p.fecha,p.estado "+
        "FROM pedido p "+
        "INNER JOIN vendedor v ON p.vendedor_id  = v.id "+
        "INNER JOIN cliente c ON p.cliente_id = c.id";
        ventas = MainGUI.db.getAll(Pedido.class,sql);
    }

    // Método para crear una sección básica (Vendedor, Cliente)
    private JPanel createSection(String title, String label1, String label2) {
        JPanel sectionPanel = new JPanel(new BorderLayout());

        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 14));
        sectionTitle.setHorizontalAlignment(SwingConstants.LEFT);
        sectionPanel.add(sectionTitle, BorderLayout.NORTH);

        JPanel fieldPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        fieldPanel.add(createLabeledTextField(label1,text->{ System.out.println("Texto actualizado: " + text);}));
        fieldPanel.add(createLabeledTextField(label2,text->{ System.out.println("Texto actualizado: " + text);}));
        sectionPanel.add(fieldPanel, BorderLayout.CENTER);

        return sectionPanel;
    }

    // Método para crear la sección de Venta
    private JPanel createVentaSection() {
        JPanel ventaTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel ventaTitle = new JLabel("Venta");
        ventaTitle.setFont(new Font("Arial", Font.BOLD, 14));
        ventaTitlePanel.add(ventaTitle);
        
        JPanel ventaPanel = new JPanel();
        this.ventasPanel = ventaPanel;
        ventaPanel.setName("VentaPanel");
        ventaPanel.setLayout(new BoxLayout(ventaPanel, BoxLayout.Y_AXIS));
        ventaPanel.add(ventaTitlePanel);
        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        filterPanel.add(createLabeledTextField("Producto",text->{ System.out.println("Texto actualizado: " + text);}));
        filterPanel.add(createLabeledTextField("fecha desde",text->{ System.out.println("Texto actualizado: " + text);}));
        filterPanel.add(createLabeledTextField("fecha hasta",text->{ System.out.println("Texto actualizado: " + text);}));
        ventaPanel.add(filterPanel, BorderLayout.CENTER);

        String[] columnNamesResumen = {"Id", "Cliente", "Vendedor", "total", "fecha","estado"};
        JTable resumenTable = new JTable(this.ventas, columnNamesResumen);
        resumenTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        resumenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resumenTable.setDefaultEditor(Object.class, null); // Make table non-editable
        resumenTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        resumenTable.setName("SummaryTable");
        resumenTable.addMouseListener(
            new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                Object id = table.getValueAt(row, 0);
                String sql = "Select p.nombre, cantidad , p.precio, dp.precio as precio_descuento " + 
                            "from detalle_pedido dp " + 
                            "inner join producto p on dp.producto_id = p.id "+
                            "where pedido_id = "+id;
                Object[][] detalles = MainGUI.db.getAll(DetallePedido.class, sql);
                SalesView.this.total = MainGUI.db.getTotalPedido(Integer.parseInt(id.toString()));
                SalesView.this.rowSelected = row;
                repaintPanel(detalles, SalesView.this.ventas);
            }
        });
        resumenTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == SalesView.this.rowSelected) {
                    c.setBackground(Colors.getMorningGlory("500"));
                    c.setForeground(new Color(255,255,255));
                }else{
                    c.setBackground(new Color(255,255,255));
                    c.setForeground(new Color(0,0,0));
                }
                return c;
            }
        });
        JScrollPane resumenScrollPane = new JScrollPane(resumenTable);
        ventaPanel.add(resumenScrollPane);

        String[] columnNamesDetalles = {"Producto", "Cantidad", "Precio"};
        JTable detallesTable = new JTable(this.detalles, columnNamesDetalles);
        detallesTable.setName("DetailsTable");
        detallesTable.setDefaultEditor(Object.class, null); // Make table non-editable
        detallesTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        JScrollPane detallesScrollPane = new JScrollPane(detallesTable);
        ventaPanel.add(Box.createVerticalStrut(10));
        ventaPanel.add(detallesScrollPane);

        JPanel footerPanel = new JPanel(new BorderLayout());
        JLabel mostSoldProductLabel = new JLabel("Producto Más vendido");
        JLabel mostSoldProduct = new JLabel(this.mostSoldProduct);
        JLabel totalLabel = new JLabel("Total", SwingConstants.RIGHT);
        JLabel total = new JLabel(this.total, SwingConstants.RIGHT);
        footerPanel.add(mostSoldProductLabel, BorderLayout.WEST);
        footerPanel.add(mostSoldProduct, BorderLayout.WEST);
        footerPanel.add(totalLabel, BorderLayout.EAST);
        footerPanel.add(total, BorderLayout.EAST);
        ventaPanel.add(footerPanel);

        return ventaPanel;
    }

    // Método para crear un JPanel con una etiqueta y un campo de texto
    private JPanel createLabeledTextField(String labelText, TextUpdateCallback callback) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(10);
        textField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                String text = textField.getText();
                callback.onTextUpdate(text);
            }
        });       
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private void repaintPanel(Object[][] detalles, Object[][] ventas) {
        this.detalles = detalles;
        this.ventas = ventas;
        JPanel ventaPanel = this.ventasPanel;
        ventaPanel.removeAll();
        ventaPanel.add(createVentaSection());
        ventaPanel.revalidate();
        ventaPanel.repaint();
    }
}
