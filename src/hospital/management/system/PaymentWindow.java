package hospital.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PaymentWindow extends JFrame {
    PaymentWindow(String patient_id) {
        setLayout(null);

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setBounds(50, 50, 100, 30);
        add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(150, 50, 150, 30);
        add(amountField);

        JButton pay = new JButton("Pay Now");
        pay.setBounds(150, 100, 100, 30);
        add(pay);

        pay.addActionListener(e -> {
            try {
                conn c = new conn();
                String amount = amountField.getText();
                String q = "INSERT INTO payments (patient_id, amount, date_paid) VALUES ('" + patient_id + "', '" + amount + "', CURDATE())";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Payment Successful");
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setSize(400, 300);
        setVisible(true);
        setLocation(500, 300);
    }
}
