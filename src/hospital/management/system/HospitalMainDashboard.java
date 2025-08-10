package hospital.management.system;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HospitalMainDashboard extends JFrame implements ActionListener {
    JButton adminLoginBtn, doctorLoginBtn, patientLoginBtn;

    public HospitalMainDashboard() {
        // Set up the main frame
        setTitle("SmartCare HOSPITAL");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Create background panel with gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 235),
                        0, getHeight(), new Color(70, 130, 180));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add medical background pattern
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < getWidth(); i += 50) {
                    for (int j = 0; j < getHeight(); j += 50) {
                        g2d.fillOval(i, j, 3, 3);
                    }
                }
            }
        };
        backgroundPanel.setBounds(0, 0, 1000, 700);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        // Hospital Name Header
        JLabel hospitalName = new JLabel("SmartCare HOSPITAL");
        hospitalName.setFont(new Font("Arial", Font.BOLD, 48));
        hospitalName.setForeground(Color.WHITE);
        hospitalName.setHorizontalAlignment(SwingConstants.CENTER);
        hospitalName.setBounds(150, 80, 700, 80);

        // Add shadow effect to hospital name
        JLabel shadowLabel = new JLabel("SmartCare HOSPITAL");
        shadowLabel.setFont(new Font("Arial", Font.BOLD, 48));
        shadowLabel.setForeground(new Color(0, 0, 0, 100));
        shadowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shadowLabel.setBounds(152, 82, 700, 80);

        backgroundPanel.add(shadowLabel);
        backgroundPanel.add(hospitalName);

        // Subtitle
        JLabel subtitle = new JLabel("Healthcare Management System");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitle.setForeground(Color.WHITE);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setBounds(150, 160, 700, 30);
        backgroundPanel.add(subtitle);

        // Create login buttons with modern styling
        adminLoginBtn = createStyledButton("ADMIN LOGIN", new Color(52, 152, 219), 200, 250);
        doctorLoginBtn = createStyledButton("DOCTOR LOGIN", new Color(46, 204, 113), 400, 250);
        patientLoginBtn = createStyledButton("PATIENT LOGIN", new Color(155, 89, 182), 600, 250);

        // Add icons to buttons (using text icons for now, you can replace with actual images)
        adminLoginBtn.setText("<html><div style='text-align: center;'>👨‍💼<br/>ADMIN LOGIN</div></html>");
        doctorLoginBtn.setText("<html><div style='text-align: center;'>👨‍⚕️<br/>DOCTOR LOGIN</div></html>");
        patientLoginBtn.setText("<html><div style='text-align: center;'>🏥<br/>PATIENT LOGIN</div></html>");

        backgroundPanel.add(adminLoginBtn);
        backgroundPanel.add(doctorLoginBtn);
        backgroundPanel.add(patientLoginBtn);

        // Add action listeners
        adminLoginBtn.addActionListener(this);
        doctorLoginBtn.addActionListener(this);
        patientLoginBtn.addActionListener(this);

        // Footer
        JLabel footer = new JLabel("© 2024 SmartCare Hospital - All Rights Reserved");
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(Color.WHITE);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBounds(150, 600, 700, 30);
        backgroundPanel.add(footer);

        // Add medical cross decoration
        drawMedicalCross(backgroundPanel, 80, 320, 50);
        drawMedicalCross(backgroundPanel, 860, 320, 50);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color backgroundColor, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 160, 120);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add rounded corners and hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = backgroundColor;

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void drawMedicalCross(JPanel panel, int x, int y, int size) {
        JPanel cross = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 80));

                // Draw cross
                int crossSize = size;
                int thickness = crossSize / 4;

                // Horizontal bar
                g2d.fillRect(0, (crossSize - thickness) / 2, crossSize, thickness);
                // Vertical bar
                g2d.fillRect((crossSize - thickness) / 2, 0, thickness, crossSize);
            }
        };
        cross.setBounds(x, y, size, size);
        cross.setOpaque(false);
        panel.add(cross);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminLoginBtn) {
            // Open Admin Login (your existing Login class)
            new Login();
            setVisible(false);
        } else if (e.getSource() == doctorLoginBtn) {
            // Open Doctor Login
            new DoctorLogin();
            setVisible(false);
        } else if (e.getSource() == patientLoginBtn) {
            // Open Patient Login
            new PatientLogin();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new HospitalMainDashboard();
    }
}