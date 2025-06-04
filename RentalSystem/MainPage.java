import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MainPage {

    public MainPage() {
        JFrame frame = new JFrame("PropertyInCyberjaya");
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel imageLabel = new JLabel();

        imageLabel.setIcon(new ImageIcon(new ImageIcon("Wallpaper.jpg").getImage().getScaledInstance(600, 120, Image.SCALE_DEFAULT)));
        panel2.add(imageLabel);
        panel.add(panel2);

        String spaces = "              ";
        panel1.add(new JLabel(spaces + "Find Cyberjaya property for rent " + spaces));
        Border blackline = BorderFactory.createTitledBorder("PropertyInCyberjaya.com.my");
        panel1.setBorder(blackline);
        panel.add(panel1);

        panel.setLayout(new GridLayout(7,1));
        JLabel label = new JLabel("Choose the role:");
        panel.add(label);

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                adminButton.setEnabled(true);
                frame.dispose();
                new Admin();
            }
        });
        JButton ownerButton = new JButton("Property Owner");
        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ownerButton.setEnabled(true);
                frame.dispose();
                new Owner();
            }
        });
        JButton tenantButton = new JButton("Potential Tenant");
        tenantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tenantButton.setEnabled(true);
                frame.dispose();
                new Tenant();
            }
        });

        JButton agentButton = new JButton("Property Agent");
        agentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agentButton.setEnabled(true);
                frame.dispose();
                new Agent();
            }
        });

        panel.add(adminButton);
        panel.add(ownerButton);
        panel.add(tenantButton);
        panel.add(agentButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setFocusable(true);
        frame.setVisible(true);
    }
}