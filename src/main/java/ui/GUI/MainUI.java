package ui.GUI;

import javax.swing.*;

import ui.GUI.Constants.Colors;
import ui.GUI.Views.FilterByMonthView;
import ui.GUI.Views.HomeView;
import ui.GUI.Views.IView;
import ui.GUI.Views.PersonsView;
import ui.GUI.Views.ProductsView;
import ui.GUI.Views.SalesView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MainUI extends JFrame {
    IView currentView = new HomeView();
    
    public MainUI(IView view) {
        currentView = view;
        setTitle("Ventas de bicicletas");
        if(currentView.getClass() == FilterByMonthView.class) {
            setSize(712,515);
        }else{
            setSize(600, 700);
        }
        // Centrar la ventana en la pantalla
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(view.render());

        add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(generateButtonPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }

   
    public JPanel generateButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        configureButton(new JButton("Home"), buttonPanel, "/ui/GUI/resources/home.png", new HomeView());
        configureButton(new JButton("Personas"), buttonPanel, "/ui/GUI/resources/people.png", new PersonsView());
        configureButton(new JButton("Productos"), buttonPanel,"/ui/GUI/resources/productos.png", new ProductsView());
        configureButton(new JButton("Sales"), buttonPanel,"/ui/GUI/resources/sales.png", new SalesView());
        configureButton(new JButton("Mensuales"), buttonPanel, "/ui/GUI/resources/monthly.png", new FilterByMonthView());
        return buttonPanel;
    }

   
    private void configureButton(JButton button, JPanel buttonPanel, String iconPath, IView view) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(iconPath)); //unscaled image
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // resize it here
        //imageIcon = new ImageIcon(newimg);

        button.setIcon(new ImageIcon(newimg)); 
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setIconTextGap(10);
        if(currentView.getClass() == view.getClass()){
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
        }else{
            button.setOpaque(false);
            button.setBorderPainted(false);
        }
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                new MainUI(view);
            }
        });
        buttonPanel.add(button);
    }
}
