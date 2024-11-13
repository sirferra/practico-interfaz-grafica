package ui.GUI.Views;

import javax.swing.*;

import java.awt.*;

import modelo.pedido.Pedido;
import modelo.persona.Cliente;
import modelo.persona.Vendedor;
import modelo.producto.Producto;
import ui.GUI.MainGUI;
import ui.GUI.Constants.Colors;

public class HomeView implements IView {

    private JPanel createStatPanel(String title, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245)); // Fondo claro
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        JLabel labelTitle = new JLabel(title);
        labelTitle.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelValue = new JLabel(value);
        labelValue.setFont(new Font("Arial", Font.BOLD, 24));
        labelValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(labelTitle);
        panel.add(labelValue);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    public JPanel render() {
        // Panel de la empresa (logo, nombre y descripción)
        JPanel companyPanel = new JPanel(new BorderLayout());
        companyPanel.setBackground(Color.WHITE);

        JLabel companyName = new JLabel("Ventas de bicicletas");
        companyName.setFont(new Font("Arial", Font.BOLD, 16));
        companyName.setHorizontalAlignment(SwingConstants.CENTER);


        JButton seedButton = new JButton("Seed Database");
        seedButton.setFont(new Font("Arial", Font.PLAIN, 12));
        seedButton.setSize(100, 20);
        seedButton.setMargin(new Insets(10, 10, 10, 10));
        seedButton.setIconTextGap(10);
        seedButton.setContentAreaFilled(false);
       
        seedButton.setBackground(Colors.getMorningGlory("500"));
        seedButton.setOpaque(true);
        seedButton.setForeground(Colors.getMorningGlory("50"));
        seedButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 
        seedButton.setFocusPainted(false);
        seedButton.setContentAreaFilled(false);
        seedButton.setOpaque(false);
        seedButton.setBorderPainted(false);
        // Para el borde redondeado del botón
        seedButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(seedButton.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20); 
                super.paint(g2, c);
                g2.dispose();
            }
        });
        seedButton.addActionListener(e -> {
            try {
                // Crear el diálogo de carga
                JDialog loadingDialog = new JDialog((JFrame) null, "Por favor, espera...", true);
                loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                loadingDialog.setSize(300, 100);
                loadingDialog.setLocationRelativeTo(null);
                JLabel messageLabel = new JLabel("Llenando la base de datos...", SwingConstants.CENTER);
                loadingDialog.add(messageLabel);

                // Mostrar el diálogo en el hilo de la interfaz gráfica
                SwingUtilities.invokeLater(() -> loadingDialog.setVisible(true));

                // Ejecutar la tarea en un hilo de fondo usando SwingWorker
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // Llenar la base de datos en segundo plano
                        MainGUI.db.seed();
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Cerrar el diálogo de carga
                        loadingDialog.dispose();
                        // Mostrar mensaje de éxito en el hilo de la interfaz gráfica
                        JOptionPane.showMessageDialog(null, "Database seeded successfully!");
                    }
                };

                worker.execute();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error seeding database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        companyPanel.add(seedButton, BorderLayout.NORTH);
        companyPanel.add(companyName, BorderLayout.CENTER);
        

        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBackground(Color.WHITE);

        statsPanel.add(createStatPanel("Clientes", getTotalEntity(Cliente.class)));
        statsPanel.add(createStatPanel("Vendedores", getTotalEntity(Vendedor.class)));
        statsPanel.add(createStatPanel("Productos", getTotalEntity(Producto.class)));
        statsPanel.add(createStatPanel("Pedidos", getTotalEntity(Pedido.class)));

        companyPanel.add(statsPanel, BorderLayout.SOUTH);
        return companyPanel;
    }

    public String getTotalEntity(Class<?> entityType) {
        try {
            int total = MainGUI.db.count(entityType);
            return String.valueOf(total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

}
