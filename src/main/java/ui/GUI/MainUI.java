package ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MainUI extends JFrame {
    Views currentView = Views.HOME;
    
    public MainUI(Views view) {
        
        // Configurar el marco principal
        setTitle("Ventas de bicicletas");
        setSize(400, 700);
        // Centrar la ventana en la pantalla
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        // setUndecorated(true);

        // Crear el panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(view.render());

        // Agregar panel principal al JFrame
        add(mainPanel, BorderLayout.CENTER);
        
        mainPanel.add(generateButtonPanel(), BorderLayout.SOUTH);
        // Mostrar la ventana
        setVisible(true);
    }

   
    public JPanel generateButtonPanel() {
        // Panel de botonera
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        // Botón Home
        JButton homeButton = new JButton("Home");
        homeButton.setIcon(new ImageIcon(getClass().getResource("/ui/GUI/resources/home.png"))); 
        homeButton.setMargin(new Insets(0, 0, 0, 0));
        homeButton.setIconTextGap(10);
        homeButton.setContentAreaFilled(false);
        homeButton.setOpaque(false);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        homeButton.setHorizontalAlignment(SwingConstants.CENTER);
        homeButton.setVerticalAlignment(SwingConstants.CENTER);
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                new MainUI(Views.HOME);
            }
        });
        buttonPanel.add(homeButton);

        // Botón People
        JButton peopleButton = new JButton("Gente");
        peopleButton.setIcon(new ImageIcon(getClass().getResource("/ui/GUI/resources/people.png"))); 
        peopleButton.setMargin(new Insets(0, 0, 0, 0));
        peopleButton.setIconTextGap(10);
        peopleButton.setContentAreaFilled(false);
        peopleButton.setOpaque(false);
        peopleButton.setBorderPainted(false);
        peopleButton.setFocusPainted(false);
        peopleButton.setHorizontalAlignment(SwingConstants.CENTER);
        peopleButton.setVerticalAlignment(SwingConstants.CENTER);
        peopleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                new MainUI(Views.CLIENTES);
            }
        });
        buttonPanel.add(peopleButton);

        // Botón Sales
        JButton salesButton = new JButton("Sales");
        salesButton.setIcon(new ImageIcon(getClass().getResource("/ui/GUI/resources/sales.png"))); 
        salesButton.setMargin(new Insets(0, 0, 0, 0));
        salesButton.setIconTextGap(10);
        salesButton.setContentAreaFilled(false);
        salesButton.setOpaque(false);
        salesButton.setBorderPainted(false);
        salesButton.setFocusPainted(false);
        salesButton.setHorizontalAlignment(SwingConstants.CENTER);
        salesButton.setVerticalAlignment(SwingConstants.CENTER);
        buttonPanel.add(salesButton);
        return buttonPanel;
    }

   

}
