package hospital.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PrescribePatient extends JFrame {
    PrescribePatient(String doctor_id) {
        setLayout(null);

        JLabel patientLabel = new JLabel("Patient ID:");
        patientLabel.setBounds(50, 50, 100, 30);
        add(patientLabel);

        JTextField patientField = new JTextField();
        patientField.setBounds(150, 50, 200, 30);
        add(patientField);

        JLabel prescriptionLabel = new JLabel("Prescription:");
        prescriptionLabel.setBounds(50, 100, 100, 30);
        add(prescriptionLabel);

        JTextArea prescriptionArea = new JTextArea();
        prescriptionArea.setBounds(150, 100, 200, 100);
        add(prescriptionArea);

        JButton prescribeButton = new JButton("Prescribe");
        prescribeButton.setBounds(150, 220, 100, 30);
        add(prescribeButton);

        prescribeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    conn c = new conn();
                    String patient_id = patientField.getText();
                    String prescription = prescriptionArea.getText();
                    String q = "INSERT INTO prescriptions (patient_id, doctor_id, prescription_text, date_given) VALUES ('" + patient_id + "', '" + doctor_id + "', '" + prescription + "', CURDATE())";
                    c.statement.executeUpdate(q);
                    JOptionPane.showMessageDialog(null, "Prescription Saved Successfully");
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setTitle("Prescribe Patient");
        setSize(450, 350);
        setLocation(500, 250);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PrescribePatient("doctor1");
    }
}
