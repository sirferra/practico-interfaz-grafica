/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.GUI.Views;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import modelo.persona.Cliente;
import modelo.persona.Vendedor;
import ui.GUI.MainGUI;

/**
 *
 * @author sirle
 */
public class FilterByMonthView extends javax.swing.JPanel  implements IView{

    private javax.swing.JComboBox<Vendedor> VendedorComboBox;
    private javax.swing.JComboBox<Cliente> ClienteComboBox;
    private List<Vendedor> vendedores;
    private List<Cliente> clientes;
    private String montoDesde = "";
    private String montoHasta = "";
    private BigDecimal monthmin = new BigDecimal(0.0);
    private BigDecimal monthmax = new BigDecimal(0.0);
    private BigDecimal monthtotal = new BigDecimal(0.0);

    private String[] sellTableColumns = new String [] {
        "Año", "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };
    
    public FilterByMonthView() {
        initComponents();
    }

    public String[][] getSumOfEachYear(String vendedor, String cliente, String monto_desde, String monto_hasta) {
        String sql = "SELECT DISTINCT year(fecha) FROM pedido WHERE 1=1 ";
        if(vendedor != null && !vendedor.isEmpty()) {
            sql += " AND vendedor_id = " + vendedor;
        }
        if (cliente != null && !cliente.isEmpty()) {
            sql += " AND cliente_id = " + cliente;
        }
        if (monto_desde != null && !monto_desde.isEmpty()) {
            sql += " AND total >= " + monto_desde;
        }
        if (monto_hasta != null && !monto_hasta.isEmpty()) {
            sql += " AND total <= " + monto_hasta;
        }
        ArrayList<HashMap<String, Object>> sellByYearList = MainGUI.db.executeQuery(sql);
        String [][] sellByYear = new String[sellByYearList.size()][13];
        int count = 0;
        for (HashMap<String, Object> year : sellByYearList) {
            sellByYear[count][0] = year.get("year(fecha)").toString();
            for (int j = 1; j < 13; j++) {
                String month = String.valueOf(j);
                String sql_month = "SELECT SUM(total) FROM pedido WHERE year(fecha) = " + sellByYear[count][0] + " AND month(fecha) = "+ month;
                if(vendedor != null && !vendedor.isEmpty()) {
                    sql_month += " AND vendedor_id = " + vendedor;
                }
                if (cliente != null && !cliente.isEmpty()) {
                    sql_month += " AND cliente_id = " + cliente;
                }
                if (monto_desde != null && !monto_desde.isEmpty()) {
                    sql_month += " AND total >= " + monto_desde;
                }
                if (monto_hasta != null && !monto_hasta.isEmpty()) {
                    sql_month += " AND total <= " + monto_hasta;
                }
                ArrayList<HashMap<String, Object>> sellByMonth = MainGUI.db.executeQuery(sql_month);
                Object value =  sellByMonth.get(0).get("SUM(total)");
                sellByYear[count][j] = value == null ? "0" : value.toString();
            }
            count++;
        }
        return sellByYear;
    }

    public void setValueOfMinMaxTotal(String vendedor, String cliente, String monto_desde, String monto_hasta) {
        String sql = "SELECT MIN(total), MAX(total), SUM(total) FROM pedido WHERE 1=1 ";
        if(vendedor != null && !vendedor.isEmpty()) {
            sql += " AND vendedor_id = " + vendedor;
        }
        if (cliente != null && !cliente.isEmpty()) {
            sql += " AND cliente_id = " + cliente;
        }
        if (monto_desde != null && !monto_desde.isEmpty()) {
            sql += " AND total >= " + monto_desde;
        }
        if (monto_hasta != null && !monto_hasta.isEmpty()) {
            sql += " AND total <= " + monto_hasta;
        }
        ArrayList<HashMap<String, Object>> sellByYearList = MainGUI.db.executeQuery(sql);
        HashMap<String, Object> minMaxTotal = sellByYearList.get(0);
        this.monthmin = (BigDecimal) minMaxTotal.get("MIN(total)");
        this.monthmax = (BigDecimal) minMaxTotal.get("MAX(total)");
        this.monthtotal = (BigDecimal) minMaxTotal.get("SUM(total)");
    }

    public JPanel render(){
        this.sellTable.setModel(new javax.swing.table.DefaultTableModel(
            this.getSumOfEachYear("", "", "", ""),
            this.sellTableColumns
        ));
        setValueOfMinMaxTotal("", "", "", "");
        minMonthValue.setText(String.valueOf(this.monthmin));
        maxMonthValue.setText(String.valueOf(this.monthmax));
        total.setText(String.valueOf(this.monthtotal));
        return this;
    }

    private void updateTable(){
        Vendedor vendedor = (Vendedor) this.VendedorComboBox.getSelectedItem();
        Cliente cliente = (Cliente)this.ClienteComboBox.getSelectedItem();
        String vendedor_id = vendedor.getDni() == 0 ? "": String.valueOf(vendedor.getId());
        String cliente_id = cliente.getDni() == 0 ? "": String.valueOf(cliente.getId());
        this.sellTable.setModel(new javax.swing.table.DefaultTableModel(
            this.getSumOfEachYear(vendedor_id, cliente_id, montoDesde, montoHasta),
            this.sellTableColumns
        ));
        this.setValueOfMinMaxTotal(vendedor_id, cliente_id, montoDesde, montoHasta);
        minMonthValue.setText(String.valueOf(this.monthmin));
        maxMonthValue.setText(String.valueOf(this.monthmax));
        total.setText(String.valueOf(this.monthtotal));

    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("unchecked")
    private void initComponents() {

        filterPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sellTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();

        this.vendedores = new ArrayList<>();
        this.vendedores.add(new Vendedor("", "---- Todos ----", "", 0, "", ""));
        this.vendedores.addAll((List<Vendedor>)(MainGUI.db.getAll(Vendedor.class)));
        this.clientes = new ArrayList<>();
        this.clientes.add(new Cliente("","---- Todos ----","",0,"",""));
        this.clientes.addAll((List<Cliente>)(MainGUI.db.getAll(Cliente.class)));

        VendedorComboBox = new JComboBox<>(this.vendedores.toArray(new Vendedor[0]));
        ClienteComboBox = new JComboBox<>(this.clientes.toArray(new Cliente[0]));

        VendedorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTable();
            }
        });

        ClienteComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTable();
            }
        });

        Monto_desde = new javax.swing.JTextField();
        Monto_hasta = new javax.swing.JTextField();

        Monto_desde.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                montoDesde = Monto_desde.getText();
                updateTable();
            }
        });

        Monto_hasta.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                montoHasta = Monto_hasta.getText();
                updateTable();
            }
        });

        minMonthPanel = new javax.swing.JPanel();
        maxMonthPanel1 = new javax.swing.JPanel();
        totalPanel = new javax.swing.JPanel();

        minMonth = new javax.swing.JLabel();
        minMonthValue = new javax.swing.JLabel();

        maxMonth = new javax.swing.JLabel();
        maxMonthValue = new javax.swing.JLabel();

        total = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        filterPanel.setMaximumSize(new java.awt.Dimension(700, 180));
        filterPanel.setPreferredSize(new java.awt.Dimension(700, 180));

        jLabel1.setText("Vendedor");

        jLabel2.setText("Cliente");

        jLabel3.setText("Monto de venta desde");

        jLabel4.setText("Monto de venta hasta");

        javax.swing.GroupLayout filterPanelLayout = new javax.swing.GroupLayout(filterPanel);
        filterPanel.setLayout(filterPanelLayout);
        filterPanelLayout.setHorizontalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(267, 267, 267)
                        .addComponent(jLabel2))
                    .addGroup(filterPanelLayout.createSequentialGroup()
                        .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(Monto_desde, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(VendedorComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 296, Short.MAX_VALUE))
                            .addComponent(jLabel3))
                        .addGap(22, 22, 22)
                        .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(ClienteComboBox, 0, 331, Short.MAX_VALUE)
                            .addComponent(Monto_hasta))))
                .addGap(51, 51, 51))
        );
        filterPanelLayout.setVerticalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(16, 16, 16)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VendedorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClienteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Monto_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Monto_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        add(filterPanel);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        sellTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            this.sellTableColumns
        ));
        jScrollPane1.setViewportView(sellTable);
        if (sellTable.getColumnModel().getColumnCount() > 0) {
            sellTable.getColumnModel().getColumn(1).setResizable(false);
            sellTable.getColumnModel().getColumn(2).setResizable(false);
            sellTable.getColumnModel().getColumn(3).setResizable(false);
            sellTable.getColumnModel().getColumn(4).setResizable(false);
            sellTable.getColumnModel().getColumn(5).setResizable(false);
            sellTable.getColumnModel().getColumn(6).setResizable(false);
            sellTable.getColumnModel().getColumn(7).setResizable(false);
            sellTable.getColumnModel().getColumn(8).setResizable(false);
            sellTable.getColumnModel().getColumn(9).setResizable(false);
            sellTable.getColumnModel().getColumn(10).setResizable(false);
            sellTable.getColumnModel().getColumn(11).setResizable(false);
            sellTable.getColumnModel().getColumn(12).setResizable(false);
        }

        jPanel1.add(jScrollPane1);

        jPanel2.setMinimumSize(new java.awt.Dimension(725, 40));
        jPanel2.setPreferredSize(new java.awt.Dimension(725, 40));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 10, 20));

        minMonthPanel.setLayout(new javax.swing.BoxLayout(minMonthPanel, javax.swing.BoxLayout.X_AXIS));

        minMonth.setText("   Mes mínimo:   $");
        minMonthPanel.add(minMonth);

        minMonthValue.setText("0");
        minMonthPanel.add(minMonthValue);

        jPanel2.add(minMonthPanel);

        maxMonthPanel1.setLayout(new javax.swing.BoxLayout(maxMonthPanel1, javax.swing.BoxLayout.LINE_AXIS));

        maxMonth.setText("Mes Máximo:   $");
        maxMonthPanel1.add(maxMonth);

        maxMonthValue.setText("0");
        maxMonthPanel1.add(maxMonthValue);

        jPanel2.add(maxMonthPanel1);

        totalPanel.setLayout(new javax.swing.BoxLayout(totalPanel, javax.swing.BoxLayout.LINE_AXIS));

       // total.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total.setText("Total:   $");
        totalPanel.add(total);

        //total1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        total1.setText("0");
        totalPanel.add(total1);

        jPanel2.add(totalPanel);

        jPanel1.add(jPanel2);

        add(jPanel1);
    } // </editor-fold>//GEN-END:initComponents




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Monto_desde;
    private javax.swing.JTextField Monto_hasta;

    private javax.swing.JPanel filterPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel maxMonth;
    private javax.swing.JPanel maxMonthPanel1;
    private javax.swing.JLabel maxMonthValue;
    private javax.swing.JLabel minMonth;
    private javax.swing.JPanel minMonthPanel;
    private javax.swing.JLabel minMonthValue;
    private javax.swing.JTable sellTable;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    private javax.swing.JPanel totalPanel;
    // End of variables declaration//GEN-END:variables
}
