package hospital.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AppointmentBooking extends JFrame {
    AppointmentBooking(String patient_id) {
        setLayout(null);

        JLabel doctorLabel = new JLabel("Doctor ID:");
        doctorLabel.setBounds(50, 50, 100, 30);
        add(doctorLabel);

        JTextField doctorField = new JTextField();
        doctorField.setBounds(150, 50, 150, 30);
        add(doctorField);

        JButton book = new JButton("Book Appointment");
        book.setBounds(150, 100, 150, 30);
        add(book);

        book.addActionListener(e -> {
            try {
                conn c = new conn();
                String doctor_id = doctorField.getText();
                String q = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES ('" + patient_id + "', '" + doctor_id + "', CURDATE())";
                c.statement.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Appointment Booked Successfully");
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
