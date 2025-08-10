package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.border.EmptyBorder;

public class DoctorDashboard extends JFrame {
    private String doctor_id;
    private String doctorName = "Dr. Smith"; // Default name
    private JPanel mainPanel;
    private JPanel sidebar;
    private JPanel contentArea;
    private JLabel timeLabel;
    private Timer timeUpdater;

    DoctorDashboard(String doctor_id) {
        this.doctor_id = doctor_id;

        // Fetch doctor name from database
        try {
            conn c = new conn();
            String query = "SELECT doctor_name FROM doctor_login WHERE doctor_id = '" + doctor_id + "'";
            ResultSet rs = c.statement.executeQuery(query);
            if (rs.next()) {
                doctorName = rs.getString("doctor_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeComponents();
        setupLayout();
        startTimeUpdater();

        setTitle("SmartCare Hospital - Doctor Dashboard");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        // Main panel with gradient background
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(240, 248, 255),
                        0, getHeight(), new Color(230, 240, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Sidebar with modern design
        sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient sidebar
                GradientPaint gradient = new GradientPaint(0, 0, new Color(44, 62, 80),
                        0, getHeight(), new Color(52, 73, 94));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add subtle pattern
                g2d.setColor(new Color(255, 255, 255, 10));
                for (int i = 0; i < getWidth(); i += 20) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
            }
        };
        sidebar.setPreferredSize(new Dimension(320, 900));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Content area
        contentArea = new JPanel();
        contentArea.setOpaque(false);
        contentArea.setLayout(new GridBagLayout());
    }

    private void setupLayout() {
        // Sidebar header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(30, 20, 20, 20));

        // Doctor avatar (placeholder)
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw circular avatar background
                g2d.setColor(new Color(52, 152, 219));
                g2d.fillOval(10, 10, 80, 80);

                // Draw doctor icon
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 36));
                FontMetrics fm = g2d.getFontMetrics();
                String initials = getInitials(doctorName);
                int x = (100 - fm.stringWidth(initials)) / 2;
                int y = (100 - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(initials, x, y);
            }
        };
        avatarPanel.setPreferredSize(new Dimension(100, 100));
        avatarPanel.setMaximumSize(new Dimension(100, 100));
        avatarPanel.setOpaque(false);
        avatarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Doctor info
        JLabel nameLabel = new JLabel(doctorName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("ID: " + doctor_id);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idLabel.setForeground(new Color(189, 195, 199));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Current time
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(189, 195, 199));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(avatarPanel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(nameLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(idLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(timeLabel);

        // Navigation buttons
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create navigation buttons
        String[] buttonTexts = {"📅 View Appointments", "💊 Prescribe Patient", "👥 Patient List",
                "📋 Medical Records", "⚙️ Settings", "🚪 Logout"};
        Color[] buttonColors = {
                new Color(52, 152, 219), new Color(46, 204, 113), new Color(155, 89, 182),
                new Color(230, 126, 34), new Color(149, 165, 166), new Color(231, 76, 60)
        };

        ActionListener[] actions = {
                e -> { new AppointmentList(doctor_id); setVisible(false); },
                e -> { new PrescribePatient(doctor_id); setVisible(false); },
                e -> showPatientList(),
                e -> showMedicalRecords(),
                e -> showSettings(),
                e -> { new DoctorLogin(); setVisible(false); }
        };

        for (int i = 0; i < buttonTexts.length; i++) {
            JButton btn = createModernButton(buttonTexts[i], buttonColors[i]);
            btn.addActionListener(actions[i]);
            navPanel.add(btn);
            if (i < buttonTexts.length - 1) {
                navPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        sidebar.add(headerPanel);
        sidebar.add(navPanel);
        sidebar.add(Box.createVerticalGlue());

        // Setup content area
        setupContentArea();

        // Add components to main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create rounded rectangle
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);

                // Fill with gradient
                Color startColor = getModel().isPressed() ? bgColor.darker() : bgColor;
                Color endColor = getModel().isRollover() ? bgColor.brighter() : bgColor.darker();
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fill(roundedRectangle);

                // Draw text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getHeight();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };

        button.setPreferredSize(new Dimension(280, 45));
        button.setMaximumSize(new Dimension(280, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private void setupContentArea() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Welcome panel
        JPanel welcomePanel = createWelcomePanel();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentArea.add(welcomePanel, gbc);

        // Quick stats panels
        JPanel appointmentsPanel = createStatsPanel("Today's Appointments", "8", new Color(52, 152, 219));
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentArea.add(appointmentsPanel, gbc);

        JPanel patientsPanel = createStatsPanel("Total Patients", "124", new Color(46, 204, 113));
        gbc.gridx = 1; gbc.gridy = 1;
        contentArea.add(patientsPanel, gbc);

        JPanel prescriptionsPanel = createStatsPanel("Prescriptions", "15", new Color(155, 89, 182));
        gbc.gridx = 0; gbc.gridy = 2;
        contentArea.add(prescriptionsPanel, gbc);

        JPanel emergencyPanel = createStatsPanel("Emergency Cases", "2", new Color(231, 76, 60));
        gbc.gridx = 1; gbc.gridy = 2;
        contentArea.add(emergencyPanel, gbc);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create rounded rectangle with gradient
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, 0, getHeight(), new Color(248, 249, 250));
                g2d.setPaint(gradient);
                g2d.fill(roundedRectangle);

                // Add subtle border
                g2d.setColor(new Color(220, 220, 220));
                g2d.setStroke(new BasicStroke(1));
                g2d.draw(roundedRectangle);
            }
        };

        panel.setPreferredSize(new Dimension(700, 120));
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel welcomeLabel = new JLabel("Welcome back, " + doctorName + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(44, 62, 80));

        JLabel messageLabel = new JLabel("Ready to help your patients today?");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageLabel.setForeground(new Color(127, 140, 141));

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(messageLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatsPanel(String title, String value, Color accentColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create rounded rectangle
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(Color.WHITE);
                g2d.fill(roundedRectangle);

                // Add colored left border
                g2d.setColor(accentColor);
                g2d.setStroke(new BasicStroke(4));
                g2d.drawLine(0, 10, 0, getHeight() - 10);

                // Add subtle shadow
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 15, 15);
            }
        };

        panel.setPreferredSize(new Dimension(320, 100));
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(127, 140, 141));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(accentColor);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private void startTimeUpdater() {
        timeUpdater = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm:ss");
            timeLabel.setText(now.format(formatter));
        });
        timeUpdater.start();
    }

    private String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "DR";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        return (parts[0].charAt(0) + "" + parts[parts.length - 1].charAt(0)).toUpperCase();
    }

    private void showPatientList() {
        JOptionPane.showMessageDialog(this, "Patient List feature will be implemented here.",
                "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMedicalRecords() {
        JOptionPane.showMessageDialog(this, "Medical Records feature will be implemented here.",
                "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSettings() {
        JOptionPane.showMessageDialog(this, "Settings feature will be implemented here.",
                "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void dispose() {
        if (timeUpdater != null) {
            timeUpdater.stop();
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DoctorDashboard("DOC001");
        });
    }
}