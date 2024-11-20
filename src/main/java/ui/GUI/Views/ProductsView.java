/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.GUI.Views;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import ui.GUI.MainGUI;
import ui.GUI.Constants.Colors;
import modelo.pedido.DetallePedido;
import modelo.persona.Proveedor;
import modelo.producto.Categoria;
import modelo.producto.Modelo;
import modelo.producto.Producto;

/**
 *
 * @author sirle
 */
public class ProductsView extends javax.swing.JPanel implements IView {
    String sql =  "SELECT producto.id, "+
    "producto.codigo, "+
    "producto.nombre, "+
    "producto.descripcion, "+
    "categoria.nombre as categoria, "+
    "proveedor.nombre as proveedor, "+
    "producto.precio, "+
    "producto.stock, "+
    "modelo.nombre as modelo "+
    "FROM producto "+
    "inner join categoria on producto.categoria_id = categoria.id "+
    "inner join proveedor on producto.proveedor_id = proveedor.id "+
    "inner join modelo on producto.modelo_id = modelo.id "+
    "ORDER BY producto.id";
    /**
     * Creates new form ProductsView
     */
    public ProductsView() {
        initComponents();
        
        Object[][] productModel = MainGUI.db.getAll(Producto.class, sql);
        
        String [] columnNames = {"ID","CODIGO","NOMBRE","DESCRIPCION","CATEGORIA","PROVEEDOR","PRECIO","STOCK","MODELO"};
        setjTable1(productModel,columnNames);
        jTable1.setPreferredScrollableViewportSize(new java.awt.Dimension(600, 400));
        jTable1.setFillsViewportHeight(true);
        jTable1.setMaximumSize(null);
        jTable1.setMinimumSize(null);
        jTable1.setPreferredSize(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 400));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        jTable1.getModel().addTableModelListener(mouseEvent -> {
            if (mouseEvent.getType() == TableModelEvent.UPDATE) {
                // edit row with new data
                TableModel table =(TableModel) mouseEvent.getSource();
                int row = mouseEvent.getFirstRow();
                //get all fields
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                String codigo = table.getValueAt(row, 1).toString();
                String nombre = table.getValueAt(row, 2).toString();
                String descripcion = table.getValueAt(row, 3).toString();
                String categoriaName = table.getValueAt(row, 4).toString();
                String proveedorName = table.getValueAt(row, 5).toString();
                double precio = Double.parseDouble(table.getValueAt(row, 6).toString());
                int stock = Integer.parseInt(table.getValueAt(row, 7).toString());
                String modeloName = table.getValueAt(row, 8).toString();
                try{
                    int categoria_id =(int) MainGUI.db.getIdFieldValue(new Categoria(), categoriaName,"nombre");
                    int proveedor_id = (int) MainGUI.db.getIdFieldValue(new Proveedor(), proveedorName,"nombre");
                    int modelo_id = (int) MainGUI.db.getIdFieldValue(new Modelo(),modeloName, "nombre" );
                    
                    String updateSql = "UPDATE producto SET "+
                    "codigo = '"+codigo+"', "+
                    "nombre = '"+nombre+"', "+
                    "descripcion = '"+descripcion+"', "+
                    "categoria_id = '"+categoria_id+"', "+
                    "proveedor_id = '"+proveedor_id+"', "+
                    "precio = '"+precio+"', "+
                    "stock = '"+stock+"', "+
                    "modelo_id = '"+modelo_id+"' "+
                    "WHERE id = "+id;
                    MainGUI.db.execute(updateSql);
                    
                    Object[][] data = MainGUI.db.getAll(DetallePedido.class, sql);
                    setjTable1(data, columnNames);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            }
        });


        jButton1.setFont(new Font("Arial", Font.PLAIN, 12));
        jButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton1.setBackground(Colors.getMorningGlory("500"));
        jButton1.setOpaque(true);
        jButton1.setForeground(Colors.getMorningGlory("50"));
        jButton1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jButton1.setFocusPainted(false);
        jButton1.setContentAreaFilled(true);
    }
    

    public void setjTable1(Object[][] data, String[] columnNames) {
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            data,
            columnNames
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        });
    }

    public javax.swing.JPanel render(){
        return this;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(600, 536));
        setPreferredSize(new java.awt.Dimension(600, 535));

        jPanel1.setPreferredSize(new java.awt.Dimension(580, 10));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Productos");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Se pueden editar los productos cambiando las celdas");
        jPanel1.add(jLabel2, java.awt.BorderLayout.PAGE_END);

        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String [] {
                "id", "codigo", "nombre", "descripcion", "categoria", "proveedor", "precio", "stock", "modelo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jButton1.setText("Nuevo producto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(515, 515, 515)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 9, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame newSaleFrame = new JFrame("Nuevo producto");
        newSaleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newSaleFrame.setSize(400, 400);
        newSaleFrame.setLocationRelativeTo(null);
        newSaleFrame.setResizable(false);
        NewProductoView newProductoView = new NewProductoView();
        newSaleFrame.add(newProductoView.render());
        newSaleFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                String [] columnNames = {"ID","CODIGO","NOMBRE","DESCRIPCION","CATEGORIA","PROVEEDOR","PRECIO","STOCK","MODELO"};
                Object[][] data = MainGUI.db.getAll(DetallePedido.class, sql);
                setjTable1(data, columnNames);
            }
        });
        newSaleFrame.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
