package hospital.management.system;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DoctorLogin extends JFrame implements ActionListener {
    JTextField username;
    JPasswordField password;
    JButton login, cancel, back;
    JLabel titleLabel, subtitleLabel;
    JPanel mainPanel, leftPanel, rightPanel, inputPanel;

    DoctorLogin() {
        setTitle("Doctor Login Portal - SmartCare HOSPITAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main container
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Left panel with medical theme
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(46, 204, 113),
                        0, getHeight(), new Color(39, 174, 96));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add medical pattern
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < getWidth(); i += 40) {
                    for (int j = 0; j < getHeight(); j += 40) {
                        // Draw medical cross pattern
                        g2d.fillRect(i + 15, j + 10, 2, 10);
                        g2d.fillRect(i + 10, j + 15, 10, 2);
                    }
                }
            }
        };
        leftPanel.setPreferredSize(new Dimension(350, 600));
        leftPanel.setLayout(null);

        // Left panel content
        JLabel hospitalLabel = new JLabel("SmartCare");
        hospitalLabel.setFont(new Font("Arial", Font.BOLD, 42));
        hospitalLabel.setForeground(Color.WHITE);
        hospitalLabel.setBounds(80, 150, 200, 50);
        leftPanel.add(hospitalLabel);

        JLabel hospitalLabel2 = new JLabel("HOSPITAL");
        hospitalLabel2.setFont(new Font("Arial", Font.BOLD, 24));
        hospitalLabel2.setForeground(Color.WHITE);
        hospitalLabel2.setBounds(80, 195, 200, 30);
        leftPanel.add(hospitalLabel2);

        JLabel welcomeLabel = new JLabel("Doctor Portal");
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        welcomeLabel.setForeground(new Color(255, 255, 255, 200));
        welcomeLabel.setBounds(80, 240, 200, 25);
        leftPanel.add(welcomeLabel);

        // Medical icon
        try {
            ImageIcon doctorIcon = new ImageIcon(ClassLoader.getSystemResource("icon/doctor.png"));
            Image img = doctorIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(img));
            iconLabel.setBounds(115, 300, 120, 120);
            leftPanel.add(iconLabel);
        } catch (Exception e) {
            // If icon not found, create a simple medical symbol
            JLabel iconLabel = new JLabel("⚕️");
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 60));
            iconLabel.setForeground(Color.WHITE);
            iconLabel.setBounds(140, 300, 80, 80);
            leftPanel.add(iconLabel);
        }

        // Right panel with login form
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());

        // Title section
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(new EmptyBorder(60, 0, 40, 0));

        titleLabel = new JLabel("Doctor Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(44, 62, 80));

        subtitleLabel = new JLabel("Access your medical dashboard");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        titlePanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        titlePanel.add(subtitleLabel, gbc);

        // Input section
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(0, 60, 0, 60));

        // Username field
        JLabel userLabel = new JLabel("Doctor ID");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(new Color(52, 73, 94));

        username = new JTextField();
        username.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        username.setPreferredSize(new Dimension(300, 45));
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Add focus effects
        username.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                username.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void focusLost(FocusEvent e) {
                username.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });

        // Password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(new Color(52, 73, 94));

        password = new JPasswordField();
        password.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        password.setPreferredSize(new Dimension(300, 45));
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        password.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                password.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void focusLost(FocusEvent e) {
                password.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });

        // Login button
        login = new JButton("LOGIN");
        login.setFont(new Font("Segoe UI", Font.BOLD, 14));
        login.setPreferredSize(new Dimension(300, 45));
        login.setBackground(new Color(46, 204, 113));
        login.setForeground(Color.WHITE);
        login.setBorder(BorderFactory.createEmptyBorder());
        login.setFocusPainted(false);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.addActionListener(this);

        // Add hover effect
        login.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                login.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(MouseEvent e) {
                login.setBackground(new Color(46, 204, 113));
            }
        });

        // Cancel button
        cancel = new JButton("CANCEL");
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancel.setPreferredSize(new Dimension(140, 40));
        cancel.setBackground(new Color(231, 76, 60));
        cancel.setForeground(Color.WHITE);
        cancel.setBorder(BorderFactory.createEmptyBorder());
        cancel.setFocusPainted(false);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.addActionListener(this);

        cancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                cancel.setBackground(new Color(192, 57, 43));
            }
            public void mouseExited(MouseEvent e) {
                cancel.setBackground(new Color(231, 76, 60));
            }
        });

        // Back button
        back = new JButton("← BACK");
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setPreferredSize(new Dimension(140, 40));
        back.setBackground(new Color(52, 152, 219));
        back.setForeground(Color.WHITE);
        back.setBorder(BorderFactory.createEmptyBorder());
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(this);

        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(MouseEvent e) {
                back.setBackground(new Color(52, 152, 219));
            }
        });

        // Layout input panel
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 8, 0);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(userLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        inputPanel.add(username, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 8, 0);
        inputPanel.add(passLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 35, 0);
        inputPanel.add(password, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        inputPanel.add(login, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(back);
        buttonPanel.add(cancel);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        inputPanel.add(buttonPanel, gbc);

        // Add components to right panel
        rightPanel.add(titlePanel, BorderLayout.NORTH);
        rightPanel.add(inputPanel, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            conn c = new conn();
            String user = username.getText();
            String pass = new String(password.getPassword());
            String q = "SELECT * FROM doctor_login WHERE doctor_id = '"+user+"' AND password = '"+pass+"'";
            ResultSet rs = c.statement.executeQuery(q);
            if(rs.next()) {
                new DoctorDashboard(user);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DoctorLogin();
    }
}
