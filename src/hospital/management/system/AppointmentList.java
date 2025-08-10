package hospital.management.system;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AppointmentList extends JFrame {
    AppointmentList(String doctor_id) {
        setLayout(new BorderLayout());

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        try {
            conn c = new conn();
            String q = "SELECT * FROM appointments WHERE doctor_id = '" + doctor_id + "'";
            ResultSet rs = c.statement.executeQuery(q);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Your Appointments");
        setSize(600, 400);
        setLocation(500, 250);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AppointmentList("doctor1"); // Example doctor id
    }
}
