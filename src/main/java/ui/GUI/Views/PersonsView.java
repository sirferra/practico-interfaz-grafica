package ui.GUI.Views;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import modelo.persona.*;
import ui.GUI.MainGUI;
import ui.GUI.Constants.Colors;
import ui.GUI.Constants.TABLES;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PersonsView implements IView {
   
    public TABLES renderedTable = TABLES.CLIENTS;
    @Override
    public JPanel render() {
        JPanel panel = configureMainPanel();
        panel.add(renderTable(renderedTable));
        panel.add(addNewPersona(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel configureMainPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245)); // Fondo claro
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));
        panel.setName("personsViewPanel");
        panel.add(generateSuperiorButtonContainer());
        JLabel instructionLabel = new JLabel("Se pueden editar las personas cambiando las celdas");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructionLabel);
        return panel;
    }

    private JScrollPane renderTable(TABLES table){
        String[] columnNames = new String[]{"ID"};
        Object[][] data =null;
        if (table == TABLES.CLIENTS) {
            String[] headers = Cliente.getHeaders().split(";");
            String[] newColumnNames = new String[(columnNames.length + headers.length)];
            System.arraycopy(columnNames, 0, newColumnNames, 0, columnNames.length);
            System.arraycopy(headers, 0, newColumnNames, columnNames.length, headers.length);
            columnNames = newColumnNames;
            data = MainGUI.db.getAll(Cliente.class, null);
        } else if (table == TABLES.PROVIDERS) {
            String[] headers = Proveedor.getHeaders().split(";");
            String[] newColumnNames = new String[columnNames.length + headers.length];
            System.arraycopy(columnNames, 0, newColumnNames, 0, columnNames.length);
            System.arraycopy(headers, 0, newColumnNames, columnNames.length, headers.length);
            columnNames = newColumnNames;
            data = MainGUI.db.getAll(Proveedor.class, null);
        } else{
            String[] headers = Vendedor.getHeaders().split(";");
            String[] newColumnNames = new String[columnNames.length + headers.length];
            System.arraycopy(columnNames, 0, newColumnNames, 0, columnNames.length);
            System.arraycopy(headers, 0, newColumnNames, columnNames.length, headers.length);
            columnNames = newColumnNames;
            data = MainGUI.db.getAll(Vendedor.class, null);
        }
        JTable clientsTable = new JTable(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        clientsTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        clientsTable.setFillsViewportHeight(true);
        clientsTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                Persona persona = null;
                switch (table) {
                    case CLIENTS:
                        persona = new Cliente(
                                (String) clientsTable.getValueAt(row, 6),
                                (String) clientsTable.getValueAt(row, 1),
                                (String) clientsTable.getValueAt(row, 2),
                                (int) clientsTable.getValueAt(row, 3),
                                (String) clientsTable.getValueAt(row, 4),
                                (String) clientsTable.getValueAt(row, 5));
                        break;
                    case PROVIDERS:
                        persona = new Proveedor(
                            (String) clientsTable.getValueAt(row, 7),
                            (String) clientsTable.getValueAt(row, 6),
                            (String) clientsTable.getValueAt(row, 1),
                            (String) clientsTable.getValueAt(row, 2),
                            (int) clientsTable.getValueAt(row, 3),
                            (String) clientsTable.getValueAt(row, 4),
                            (String) clientsTable.getValueAt(row, 5)
                        );
                        break;
                    case SELLER:
                        persona = new Vendedor(
                            (String) clientsTable.getValueAt(row, 6),
                            (String) clientsTable.getValueAt(row, 1),
                            (String) clientsTable.getValueAt(row, 2),
                            (int) clientsTable.getValueAt(row, 3),
                            (String) clientsTable.getValueAt(row, 4),
                            (String) clientsTable.getValueAt(row, 5)
                        );
                        break;
                }
                try {
                    TableModel model = (TableModel) e.getSource();
                    Object id = model.getValueAt(row, 0);
                    MainGUI.db.update(persona, persona.toMap(), (int) id);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        return scrollPane;
    }

    private JButton addNewPersona(){
        String textLabel = "Nuevo " + this.renderedTable.getName();
        JButton newPerson = new JButton(textLabel);
        newPerson.setFont(new Font("Arial", Font.PLAIN, 12));
        newPerson.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPerson.setBackground(Colors.getMorningGlory("500"));
        newPerson.setOpaque(true);
        newPerson.setForeground(Colors.getMorningGlory("50"));
        newPerson.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newPerson.setFocusPainted(false);
        newPerson.setContentAreaFilled(true);

        newPerson.addActionListener(e -> {
            JFrame newPersonFrame = new JFrame(textLabel);
            newPersonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newPersonFrame.setSize(400, 300);
            newPersonFrame.setLocationRelativeTo(null);
            NewPersonaView newPersonView = new NewPersonaView(this.renderedTable);
            newPersonFrame.add(newPersonView.render());
            newPersonFrame.setVisible(true);
        });

        return newPerson;
    }

    private JPanel generateSuperiorButtonContainer(){
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245)); // Fondo claro
        addAttributesToButton(new JButton("Clientes"),   TABLES.CLIENTS,  buttonPanel);
        addAttributesToButton(new JButton("Vendedores"), TABLES.SELLER,   buttonPanel);
        addAttributesToButton(new JButton("Proveedores"),TABLES.PROVIDERS,buttonPanel);
        return buttonPanel;
    }


    private void addAttributesToButton(JButton button, TABLES table,JPanel buttonPanel){
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setIconTextGap(10);
        button.setContentAreaFilled(false);
        if(table == renderedTable){
            button.setBackground(Colors.getMorningGlory("500"));
            button.setOpaque(true);
            button.setForeground(Colors.getMorningGlory("50"));
            button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(false);
            button.setBorderPainted(false);
            // Para el borde redondeado del botón
            button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(button.getBackground());
                    g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20); 
                    super.paint(g2, c);
                    g2.dispose();
                }
            });
        }
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                renderedTable = table;
                JPanel panel = (JPanel) SwingUtilities.getAncestorOfClass(JPanel.class, buttonPanel);
                repaintPanel(renderedTable, panel);
            }
        });
        buttonPanel.add(button);
    }
    private void repaintPanel(TABLES table, JPanel panel){
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245)); // Fondo claro
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 10));
        panel.add(generateSuperiorButtonContainer());
        JLabel instructionLabel = new JLabel("Se pueden editar las personas cambiando las celdas");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructionLabel);
        panel.add(renderTable(renderedTable));
        panel.add(addNewPersona(), BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();
    }
}
