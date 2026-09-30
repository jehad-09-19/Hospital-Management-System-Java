package gui;

import model.User;
import service.HospitalManager;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    HospitalManager m;
    JTextField id = new JTextField();
    JPasswordField pw = new JPasswordField();

    public LoginFrame(HospitalManager m) {
        this.m = m;
        setTitle("SEU Hospital Management System - Login");
        setSize(430, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(5, 2, 8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        JLabel t = new JLabel("HOSPITAL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        t.setFont(new Font("Arial", Font.BOLD, 17));
        p.add(t);
        p.add(new JLabel());
        p.add(new JLabel("User ID:"));
        p.add(id);
        p.add(new JLabel("Password:"));
        p.add(pw);
        JButton l = new JButton("Login"), r = new JButton("Reset Password");
        p.add(l);
        p.add(r);
        p.add(new JLabel("Demo: admin / 1234"));
        p.add(new JLabel("D001 / 1234"));
        add(p);
        l.addActionListener(e -> login());
        r.addActionListener(e -> reset());
        setVisible(true);
    }

    void login() {
        User u = m.login(id.getText().trim(), new String(pw.getPassword()));
        if (u == null) JOptionPane.showMessageDialog(this, "Invalid User ID or Password.");
        else {
            dispose();
            new MainFrame(m, u);
        }
    }

    void reset() {
        String i = JOptionPane.showInputDialog(this, "User ID:");
        if (i == null) return;
        User u = m.find(i.trim());
        if (u == null) {
            JOptionPane.showMessageDialog(this, "User not found.");
            return;
        }
        String p = JOptionPane.showInputDialog(this, "New password:");
        if (p != null && !p.isBlank()) {
            u.setPassword(p);
            try {
                m.save();
                JOptionPane.showMessageDialog(this, "Password reset successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
}
