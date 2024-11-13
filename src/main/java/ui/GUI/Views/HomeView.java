package ui.GUI.Views;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.*;

import modelo.pedido.Pedido;
import modelo.persona.Cliente;
import modelo.persona.Vendedor;
import modelo.producto.Producto;
import ui.GUI.MainGUI;

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
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon("path/to/logo.png")); // Cambiar por el logo deseado
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel companyName = new JLabel("Ventas de bicicletas");
        companyName.setFont(new Font("Arial", Font.BOLD, 16));
        companyName.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel companyDesc = new JLabel("Ventas de bicicletas de alta calidad");
        companyDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        companyDesc.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel companyInfo = new JLabel("Verificado · 100 empleados");
        companyInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        companyInfo.setHorizontalAlignment(SwingConstants.CENTER);

        companyPanel.add(logoLabel, BorderLayout.NORTH);
        companyPanel.add(companyName, BorderLayout.CENTER);
        companyPanel.add(companyDesc, BorderLayout.SOUTH);
        companyPanel.add(companyInfo, BorderLayout.SOUTH);

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
