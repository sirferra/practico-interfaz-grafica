package ui.GUI.Views;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import modelo.pedido.DetallePedido;
import modelo.pedido.Pedido;
import ui.GUI.MainGUI;
import ui.GUI.Constants.Colors;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
public class SalesView implements IView {
    Object[][] ventas = new Object[][]{};
    Object[][] detalles = new Object[][]{};
    Object[][] filteredVentas = new Object[][]{};
    Object[][] filteredDetalles = new Object[][]{};
    JPanel ventasPanel = null;
    String total = "0.00";
    String mostSoldProduct = "-";
    int rowSelected = -1;
    String nameVendorFilter = "";
    String lastNameVendorFilter = "";
    String nameClientFilter = "";
    String lastNameClientFilter = "";
    @Override
    public JPanel render() {
        JPanel mainPanel = new JPanel();
        mostSoldProduct = MainGUI.db.getProductoMasVendido();
        setVentas();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(crearPanelFiltrosVendedorYCliente());
                
        JPanel ventaTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ventaTitle = new JLabel("Venta");
        ventaTitle.setFont(new Font("Arial", Font.BOLD, 14));
        ventaTitlePanel.add(ventaTitle); 

        JPanel ventaFilterPanel = new JPanel();
        ventaFilterPanel.setLayout(new BoxLayout(ventaFilterPanel, BoxLayout.Y_AXIS));
        ventaFilterPanel.add(ventaTitlePanel);
        
        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        filterPanel.add(createLabeledTextField("Producto", text -> {
            if(text.isEmpty()) {
                repaintPanel(this.detalles, this.ventas);
                return;
            };
            this.filteredDetalles = Arrays.stream(this.detalles)
                .filter(row -> row[0].toString().toLowerCase().contains(text.toLowerCase()))
                .toArray(Object[][]::new);
            repaintPanel(this.filteredDetalles, this.ventas);
        }));
        filterPanel.add(createLabeledTextField("fecha desde",text->{ 
            if(text.isEmpty() || !text.matches(new String("^[0-9]{4}-[0-9]{2}-[0-9]{2}$"))) {
                repaintPanel(this.detalles, this.ventas);
                return;
            };
            this.filteredVentas = Arrays.stream(this.ventas)
                .filter(row -> ((Date)row[4]).after(java.sql.Date.valueOf(text)) || ((Date)row[4]).equals(java.sql.Date.valueOf(text)))
                .toArray(Object[][]::new);
            repaintPanel(this.detalles, this.filteredVentas);
        }));
        filterPanel.add(createLabeledTextField("fecha hasta",text->{ 
            if(text.isEmpty() || !text.matches(new String("^[0-9]{4}-[0-9]{2}-[0-9]{2}$"))) {
                repaintPanel(this.detalles, this.ventas);
                return;
            };
            this.filteredVentas = Arrays.stream(this.ventas)
                .filter(row -> ((Date)row[4]).before(java.sql.Date.valueOf(text)) || ((Date)row[4]).equals(java.sql.Date.valueOf(text)))
                .toArray(Object[][]::new);
            repaintPanel(this.detalles, this.filteredVentas);
        }));
        ventaFilterPanel.add(filterPanel, BorderLayout.CENTER);
        mainPanel.add(ventaFilterPanel);
        JPanel ventaPanel = createVentaSection(this.detalles, this.ventas);
        mainPanel.add(ventaPanel);

        JButton newSaleButton = new JButton("Nueva venta");
        newSaleButton.setFont(new Font("Arial", Font.PLAIN, 12));
        newSaleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newSaleButton.setBackground(Colors.getMorningGlory("500"));
        newSaleButton.setOpaque(true);
        newSaleButton.setForeground(Colors.getMorningGlory("50"));
        newSaleButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newSaleButton.setFocusPainted(false);
        newSaleButton.setContentAreaFilled(true);

        newSaleButton.addActionListener(e -> {
            JFrame newSaleFrame = new JFrame("Nueva venta");
            newSaleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newSaleFrame.setSize(400, 300);
            newSaleFrame.setLocationRelativeTo(null);
            NewSaleView newSaleView = new NewSaleView();
            newSaleFrame.add(newSaleView.render());
            newSaleFrame.setVisible(true);
        });

        mainPanel.add(newSaleButton, BorderLayout.CENTER);
        return mainPanel;
    }

    private void setVentas(){
        String sql = "SELECT p.id,Concat(c.nombre, ' ', c.apellido) as cliente,Concat(v.nombre, ' ', v.apellido) as vendedor, p.total, p.fecha,p.estado "+
        "FROM pedido p "+
        "INNER JOIN vendedor v ON p.vendedor_id  = v.id "+
        "INNER JOIN cliente c ON p.cliente_id = c.id";
        ventas = MainGUI.db.getAll(Pedido.class,sql);
    }


    private JPanel crearPanelFiltrosVendedorYCliente() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JLabel filter2Title = new JLabel("Cliente");
        filter2Title.setAlignmentX(0.5f);
        filterPanel.add(filter2Title);
        filterPanel.add(createFilter("Cliente", "Nombre", "Apellido"));

        JLabel filterTitle = new JLabel("Vendedor");
        filterTitle.setAlignmentX(0.5f);
        filterPanel.add(filterTitle);
        filterPanel.add(createFilter("Vendedor", "Nombre", "Apellido"));

        return filterPanel;
    }

    private JPanel createFilter(String title, String label1, String label2) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel(label1);
        JTextField nameInput = new JTextField(15);
        nameInput.setName(title + "Name");
        nameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = nameInput.getText();
                filterByNameOrLastName(text, title, title + "Name");
            }
        });
        panel.add(nameLabel);
        panel.add(nameInput);

        JLabel lastNameLabel = new JLabel(label2);
        JTextField lastNameInput = new JTextField(15);
        lastNameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = lastNameInput.getText();
                filterByNameOrLastName(text, title, title + "LastName");
            }
        });
        panel.add(lastNameLabel);
        panel.add(lastNameInput);
        return panel;
    }
    
    private void filterByNameOrLastName(String text,String title, String fieldName) {
        switch (fieldName) {
            case "VendedorName":
                this.nameVendorFilter = text;
                break;
            case "VendedorLastName":
                this.lastNameVendorFilter = text;
                break;
            case "ClienteName":
                this.nameClientFilter = text;
                break;
            case "ClienteLastName":
                this.lastNameClientFilter = text;
                break;
            default:
                break;
        }
        if( nameClientFilter.isEmpty() 
           && lastNameClientFilter.isEmpty() 
           && nameVendorFilter.isEmpty() 
           && lastNameVendorFilter.isEmpty()) {
            repaintPanel(this.detalles, this.ventas);
            return;
        };
        this.filteredVentas = Arrays.stream(ventas)
            .filter(row -> filterByAllFilters(row))
            .toArray(Object[][]::new);
        repaintPanel(this.detalles, this.filteredVentas);
    }
    private boolean filterByAllFilters(Object[] row){
        int CLIENT_COLUMN = 1;
        int VENDOR_COLUMN = 2;
        //int rowNumber =  title == "Vendedor" ? 2 : 1;
        return row[CLIENT_COLUMN].toString().toLowerCase().contains(nameClientFilter.toLowerCase())
            && row[CLIENT_COLUMN].toString().toLowerCase().contains(lastNameClientFilter.toLowerCase())

            && row[VENDOR_COLUMN].toString().toLowerCase().contains(nameVendorFilter.toLowerCase())
            && row[VENDOR_COLUMN].toString().toLowerCase().contains(lastNameVendorFilter.toLowerCase());
    }

    private JPanel createVentaSection(Object[][] detalles, Object[][] ventas) {
        JPanel ventaPanel = new JPanel();
        ventaPanel.setLayout(new BoxLayout(ventaPanel, BoxLayout.Y_AXIS));
        this.ventasPanel = ventaPanel;
        String[] columnNamesResumen = {"Id", "Cliente", "Vendedor", "total", "fecha","estado"};
        JTable resumenTable = new JTable(ventas, columnNamesResumen);
        resumenTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        resumenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resumenTable.setDefaultEditor(Object.class, null); // Make table non-editable
        resumenTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(resumenTable.getModel()) {
            @Override
            protected boolean useToString(int column) {
                return column != 0;
            }
            @Override
            public Comparator<?> getComparator(int column) {
                if (column == 0) {
                    return Comparator.comparingInt(o -> (int) o);
                }
                return super.getComparator(column);
            }
        };
        resumenTable.setRowSorter(sorter);

        resumenTable.setName("SummaryTable");
        resumenTable.addMouseListener(
            new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                Object id = table.getValueAt(row, 0);
                String sql = "Select p.nombre, cantidad , dp.precio " + 
                            "from detalle_pedido dp " + 
                            "inner join producto p on dp.producto_id = p.id "+
                            "where pedido_id = "+id;
                Object[][] detalles = MainGUI.db.getAll(DetallePedido.class, sql);
                SalesView.this.detalles = detalles;
                SalesView.this.total = MainGUI.db.getTotalPedido(Integer.parseInt(id.toString()));
                SalesView.this.rowSelected = row;
                repaintPanel(detalles, isFiltered()?SalesView.this.filteredVentas: SalesView.this.ventas);
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
        JTable detallesTable = new JTable(detalles, columnNamesDetalles);
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

    private boolean isFiltered(){
        return this.filteredVentas != null && this.filteredVentas.length > 0;
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
        JPanel ventaPanel = this.ventasPanel;
        ventaPanel.removeAll();
        ventaPanel.add(createVentaSection(detalles, ventas));
        ventaPanel.revalidate();
        ventaPanel.repaint();
    }
}
