package gui;

import exception.Exceptions.InvalidData;
import model.User;
import model.Roles.*;
import model.Entities.*;
import service.HospitalManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class MainFrame extends JFrame {
    private static final Color NAVY = new Color(18, 52, 86);
    private static final Color BLUE = new Color(33, 150, 243);
    private static final Color LIGHT_BLUE = new Color(232, 244, 253);
    private static final Color GREEN = new Color(46, 170, 95);
    private static final Color PURPLE = new Color(126, 87, 194);
    private static final Color ORANGE = new Color(245, 158, 11);
    private static final Color TEAL = new Color(20, 170, 170);
    private static final Color TEXT = new Color(34, 47, 62);
    private static final Color MUTED = new Color(105, 120, 135);
    private static final Color BG = new Color(247, 249, 252);

    private final HospitalManager manager;
    private final User currentUser;
    private final JPanel contentPanel = new JPanel(new BorderLayout());
    private final JLabel pageTitle = new JLabel("Dashboard");

    public MainFrame(HospitalManager manager, User currentUser) {
        this.manager = manager;
        this.currentUser = currentUser;

        setTitle("Hospital Management System");
        setSize(1280, 760);
        setMinimumSize(new Dimension(1050, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();
        showDashboard();
        setVisible(true);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(225, 0));

        JPanel brand = new JPanel();
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setBackground(NAVY);
        brand.setBorder(new EmptyBorder(24, 15, 18, 15));

        JLabel logo = new JLabel("HMS", SwingConstants.CENTER);
        logo.setOpaque(true);
        logo.setBackground(BLUE);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 24));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setPreferredSize(new Dimension(65, 65));
        logo.setMaximumSize(new Dimension(65, 65));

        JLabel name = new JLabel("Hospital Management", SwingConstants.CENTER);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SansSerif", Font.BOLD, 15));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setBorder(new EmptyBorder(12, 0, 2, 0));

        JLabel subtitle = new JLabel("SYSTEM", SwingConstants.CENTER);
        subtitle.setForeground(new Color(160, 195, 225));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        brand.add(logo);
        brand.add(name);
        brand.add(subtitle);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(NAVY);
        menu.setBorder(new EmptyBorder(12, 10, 10, 10));

        addMenuButton(menu, "Dashboard", true, e -> showDashboard());
        if (canSeeUsers()) addMenuButton(menu, "Manage Users", false, e -> userListDialog());
        if (canSeeDoctors()) addMenuButton(menu, "Doctors", false, e -> showUserList("Doctor"));
        if (canSeePatients()) addMenuButton(menu, "Patients", false, e -> showUserList("Patient"));
        if (canSeeNurses()) addMenuButton(menu, "Nurses", false, e -> showUserList("Nurse"));
        if (canSeeAppointments()) addMenuButton(menu, "Appointments", false, e -> appointmentDialog());
        if (canSeeRecords()) addMenuButton(menu, "Medical Records", false, e -> medicalRecordDialog());
        if (canSeePayments()) addMenuButton(menu, "Payments", false, e -> paymentDialog());
        addMenuButton(menu, "My Profile", false, e -> profileDialog());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(NAVY);
        bottom.setBorder(new EmptyBorder(10, 10, 18, 10));

        JLabel course = new JLabel("CSE282 • Southeast University", SwingConstants.CENTER);
        course.setForeground(new Color(160, 185, 210));
        course.setFont(new Font("SansSerif", Font.PLAIN, 10));

        JButton logout = new JButton("Logout");
        styleSidebarButton(logout, false);
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame(manager);
        });

        bottom.add(logout, BorderLayout.NORTH);
        bottom.add(course, BorderLayout.SOUTH);

        sidebar.add(brand, BorderLayout.NORTH);
        sidebar.add(new JScrollPane(menu,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }

    private void addMenuButton(JPanel menu, String text, boolean selected, java.awt.event.ActionListener action) {
        JButton b = new JButton("  " + text);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(12, 15, 12, 10));
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        b.addActionListener(action);
        styleSidebarButton(b, selected);
        menu.add(b);
        menu.add(Box.createVerticalStrut(5));
    }

    private void styleSidebarButton(JButton b, boolean selected) {
        b.setBackground(selected ? BLUE : NAVY);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
    }

    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(16, 25, 16, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 23));
        pageTitle.setForeground(TEXT);
        JLabel subtitle = new JLabel("Hospital Management System  •  Better Care, Healthier Tomorrow");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        titlePanel.add(pageTitle);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        userPanel.setOpaque(false);
        JLabel avatar = new JLabel(currentUser.getRole().substring(0, 1), SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(LIGHT_BLUE);
        avatar.setForeground(BLUE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 19));
        avatar.setPreferredSize(new Dimension(43, 43));

        JPanel userText = new JPanel();
        userText.setLayout(new BoxLayout(userText, BoxLayout.Y_AXIS));
        userText.setOpaque(false);
        JLabel userName = new JLabel(currentUser.getName());
        userName.setFont(new Font("SansSerif", Font.BOLD, 13));
        userName.setForeground(TEXT);
        JLabel role = new JLabel(currentUser.getRole());
        role.setFont(new Font("SansSerif", Font.PLAIN, 11));
        role.setForeground(MUTED);
        userText.add(userName);
        userText.add(role);

        userPanel.add(avatar);
        userPanel.add(userText);
        top.add(titlePanel, BorderLayout.WEST);
        top.add(userPanel, BorderLayout.EAST);

        contentPanel.setBackground(BG);
        contentPanel.setBorder(new EmptyBorder(20, 25, 25, 25));

        main.add(top, BorderLayout.NORTH);
        main.add(contentPanel, BorderLayout.CENTER);
        return main;
    }

    private void showDashboard() {
        pageTitle.setText("Dashboard");
        contentPanel.removeAll();

        JPanel dashboard = new JPanel();
        dashboard.setLayout(new BoxLayout(dashboard, BoxLayout.Y_AXIS));
        dashboard.setBackground(BG);

        dashboard.add(createWelcomeCard());
        dashboard.add(Box.createVerticalStrut(16));
        dashboard.add(createSummaryCards());
        dashboard.add(Box.createVerticalStrut(16));

        JPanel middle = new JPanel(new GridLayout(1, 2, 16, 0));
        middle.setOpaque(false);
        middle.add(createDistributionCard());
        middle.add(createAppointmentsCard());
        dashboard.add(middle);
        dashboard.add(Box.createVerticalStrut(16));
        dashboard.add(createQuickActions());

        JScrollPane scroll = new JScrollPane(dashboard);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createWelcomeCard() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(LIGHT_BLUE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 220, 245)),
                new EmptyBorder(17, 20, 17, 20)));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        JLabel w = new JLabel("Welcome back, " + currentUser.getName() + "!");
        w.setFont(new Font("SansSerif", Font.BOLD, 20));
        w.setForeground(new Color(16, 58, 105));
        JLabel s = new JLabel("Here is today's overview of your hospital management system.");
        s.setFont(new Font("SansSerif", Font.PLAIN, 12));
        s.setForeground(new Color(75, 110, 145));
        left.add(w);
        left.add(Box.createVerticalStrut(6));
        left.add(s);

        JLabel date = new JLabel("CSE282  •  Summer 2026", SwingConstants.RIGHT);
        date.setFont(new Font("SansSerif", Font.BOLD, 12));
        date.setForeground(new Color(50, 90, 130));
        p.add(left, BorderLayout.CENTER);
        p.add(date, BorderLayout.EAST);
        return p;
    }

    private JPanel createSummaryCards() {
        JPanel p = new JPanel(new GridLayout(1, 5, 12, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

        p.add(summaryCard("Doctors", countRole(Doctor.class), BLUE));
        p.add(summaryCard("Nurses", countRole(Nurse.class), GREEN));
        p.add(summaryCard("Patients", countRole(Patient.class), PURPLE));
        p.add(summaryCard("Receptionists", countRole(Receptionist.class), ORANGE));
        p.add(summaryCard("Appointments", manager.data().appointments.size(), TEAL));
        return p;
    }

    private JPanel summaryCard(String label, int value, Color color) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(color);
        p.setBorder(new EmptyBorder(13, 15, 12, 15));

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        valueLabel.setForeground(Color.WHITE);
        JLabel name = new JLabel(label);
        name.setFont(new Font("SansSerif", Font.BOLD, 12));
        name.setForeground(Color.WHITE);
        JLabel small = new JLabel("View details  →");
        small.setFont(new Font("SansSerif", Font.PLAIN, 10));
        small.setForeground(new Color(240, 250, 255));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);
        text.add(valueLabel);
        text.add(name);
        text.add(Box.createVerticalStrut(6));
        text.add(small);
        p.add(text, BorderLayout.CENTER);
        return p;
    }

    private JPanel createDistributionCard() {
        JPanel p = cardPanel();
        p.setLayout(new BorderLayout());

        JLabel title = sectionTitle("User Distribution");
        p.add(title, BorderLayout.NORTH);

        JPanel bars = new JPanel();
        bars.setLayout(new BoxLayout(bars, BoxLayout.Y_AXIS));
        bars.setOpaque(false);
        addBar(bars, "Doctors", countRole(Doctor.class), BLUE);
        addBar(bars, "Nurses", countRole(Nurse.class), GREEN);
        addBar(bars, "Patients", countRole(Patient.class), PURPLE);
        addBar(bars, "Receptionists", countRole(Receptionist.class), ORANGE);
        addBar(bars, "Admins", countRole(Admin.class), TEAL);
        p.add(bars, BorderLayout.CENTER);
        return p;
    }

    private void addBar(JPanel parent, String name, int count, Color color) {
        JPanel row = new JPanel(new BorderLayout(8, 4));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        JLabel label = new JLabel(name);
        label.setPreferredSize(new Dimension(90, 20));
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setForeground(TEXT);
        JProgressBar bar = new JProgressBar(0, Math.max(10, totalUsers()));
        bar.setValue(count);
        bar.setForeground(color);
        bar.setBackground(new Color(235, 239, 244));
        bar.setBorderPainted(false);
        JLabel number = new JLabel(String.valueOf(count));
        number.setFont(new Font("SansSerif", Font.BOLD, 11));
        number.setForeground(MUTED);
        row.add(label, BorderLayout.WEST);
        row.add(bar, BorderLayout.CENTER);
        row.add(number, BorderLayout.EAST);
        parent.add(row);
        parent.add(Box.createVerticalStrut(5));
    }

    private JPanel createAppointmentsCard() {
        JPanel p = cardPanel();
        p.setLayout(new BorderLayout(0, 10));
        p.add(sectionTitle("Recent Appointments"), BorderLayout.NORTH);

        String[] columns = {"ID", "Patient", "Doctor", "Date", "Status"};
        DefaultTableModel m = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        List<Appointment> list = manager.data().appointments;
        int start = Math.max(0, list.size() - 5);
        for (int i = start; i < list.size(); i++) {
            Appointment a = list.get(i);
            m.addRow(new Object[]{a.appointmentId, a.patientId, a.doctorId, a.date, a.status});
        }
        JTable table = new JTable(m);
        table.setRowHeight(27);
        table.setFont(new Font("SansSerif", Font.PLAIN, 11));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        table.getTableHeader().setBackground(new Color(238, 244, 250));
        table.setGridColor(new Color(225, 231, 238));
        table.setFillsViewportHeight(true);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private JPanel createQuickActions() {
        JPanel p = cardPanel();
        p.setLayout(new BorderLayout(0, 12));
        p.add(sectionTitle("Quick Actions"), BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridLayout(1, 5, 10, 0));
        actions.setOpaque(false);
        addAction(actions, "Add User", BLUE, e -> {
            if (currentUser instanceof Admin) userListDialog();
            else profileDialog();
        });
        addAction(actions, "Book Appointment", GREEN, e -> appointmentDialog());
        addAction(actions, "View Records", PURPLE, e -> medicalRecordDialog());
        addAction(actions, "Make Payment", ORANGE, e -> paymentDialog());
        addAction(actions, "My Profile", TEAL, e -> profileDialog());
        p.add(actions, BorderLayout.CENTER);
        return p;
    }

    private void addAction(JPanel parent, String text, Color color, java.awt.event.ActionListener action) {
        JButton b = new JButton("  " + text + "  ");
        b.setFont(new Font("SansSerif", Font.BOLD, 11));
        b.setForeground(color.darker());
        b.setBackground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createLineBorder(new Color(215, 222, 230)));
        b.addActionListener(action);
        parent.add(b);
    }

    private JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 228, 235)),
                new EmptyBorder(15, 15, 15, 15)));
        return p;
    }

    private JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setForeground(new Color(21, 55, 95));
        return l;
    }

    private int totalUsers() {
        return manager.data().users.size();
    }

    private int countRole(Class<?> type) {
        int count = 0;
        for (User u : manager.data().users) if (type.isInstance(u)) count++;
        return count;
    }

    private boolean canSeeUsers() {
        return currentUser instanceof Admin;
    }

    private boolean canSeeDoctors() {
        return true;
    }

    private boolean canSeePatients() {
        return true;
    }

    private boolean canSeeNurses() {
        return currentUser instanceof Admin || currentUser instanceof Doctor || currentUser instanceof Nurse;
    }

    private boolean canSeeAppointments() {
        return true;
    }

    private boolean canSeeRecords() {
        return currentUser instanceof Admin || currentUser instanceof Doctor || currentUser instanceof Patient || currentUser instanceof Nurse;
    }

    private boolean canSeePayments() {
        return currentUser instanceof Admin || currentUser instanceof Doctor || currentUser instanceof Patient || currentUser instanceof Receptionist;
    }

    private void profileDialog() {
        JPanel p = new JPanel(new GridLayout(0, 2, 7, 7));
        p.setBorder(new EmptyBorder(8, 8, 8, 8));
        JTextField name = new JTextField(currentUser.getName());
        JTextField email = new JTextField(currentUser.getEmail());
        JTextField contact = new JTextField(currentUser.getContactNo());
        p.add(new JLabel("User ID:"));
        p.add(new JLabel(currentUser.getUserId()));
        p.add(new JLabel("Role:"));
        p.add(new JLabel(currentUser.getRole()));
        p.add(new JLabel("Name:"));
        p.add(name);
        p.add(new JLabel("Email:"));
        p.add(email);
        p.add(new JLabel("Contact:"));
        p.add(contact);
        int result = JOptionPane.showConfirmDialog(this, p, "My Profile - Update", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            currentUser.setName(name.getText());
            currentUser.setEmail(email.getText());
            currentUser.setContactNo(contact.getText());
            try {
                manager.save();
                JOptionPane.showMessageDialog(this, "Profile updated successfully.");
                showDashboard();
            } catch (Exception e) {
                showError(e);
            }
        }
    }

    private void userListDialog() {
        String[] choices = {"Admin", "Doctor", "Patient", "Receptionist", "Nurse"};
        String role = (String) JOptionPane.showInputDialog(this, "Select user type:", "Manage Users",
                JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        if (role == null) return;
        List<User> list = manager.data().users.stream().filter(u -> u.getRole().equals(role)).toList();
        String[] columns = {"ID", "Name", "Role", "Email", "Contact"};
        DefaultTableModel m = new DefaultTableModel(columns, 0);
        for (User u : list)
            m.addRow(new Object[]{u.getUserId(), u.getName(), u.getRole(), u.getEmail(), u.getContactNo()});
        JTable t = new JTable(m);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(720, 420));
        panel.add(new JScrollPane(t), BorderLayout.CENTER);
        JButton add = new JButton("Add");
        JButton update = new JButton("Update Selected");
        JButton delete = new JButton("Delete Selected");
        JPanel bp = new JPanel();
        bp.add(add);
        bp.add(update);
        bp.add(delete);
        panel.add(bp, BorderLayout.SOUTH);
        add.addActionListener(e -> addUserDialog(role, m));
        update.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) {
                showError("Select a row.");
                return;
            }
            User u = manager.find(m.getValueAt(row, 0).toString());
            if (u != null) updateUserDialog(u, m);
        });
        delete.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) {
                showError("Select a row.");
                return;
            }
            try {
                manager.delete(m.getValueAt(row, 0).toString());
                m.removeRow(row);
                showDashboard();
            } catch (Exception ex) {
                showError(ex);
            }
        });
        JOptionPane.showMessageDialog(this, panel, "Manage " + role + " Users", JOptionPane.PLAIN_MESSAGE);
    }

    private void addUserDialog(String role, DefaultTableModel m) {
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        JTextField id = new JTextField(), pass = new JTextField(), name = new JTextField(), email = new JTextField(), contact = new JTextField();
        JTextField d1 = new JTextField(), d2 = new JTextField(), d3 = new JTextField();
        p.add(new JLabel("User ID:"));
        p.add(id);
        p.add(new JLabel("Password:"));
        p.add(pass);
        p.add(new JLabel("Name:"));
        p.add(name);
        p.add(new JLabel("Email:"));
        p.add(email);
        p.add(new JLabel("Contact:"));
        p.add(contact);
        if (role.equals("Admin")) {
            p.add(new JLabel("Admin Type:"));
            p.add(d1);
        } else if (role.equals("Doctor")) {
            p.add(new JLabel("Specialist:"));
            p.add(d1);
            p.add(new JLabel("Educational Information:"));
            p.add(d2);
        } else if (role.equals("Patient")) {
            p.add(new JLabel("Gender:"));
            p.add(d1);
            p.add(new JLabel("Age:"));
            p.add(d2);
            p.add(new JLabel("Address:"));
            p.add(d3);
        } else if (role.equals("Receptionist")) {
            p.add(new JLabel("Receptionist ID:"));
            p.add(d1);
            p.add(new JLabel("Address:"));
            p.add(d2);
        } else {
            p.add(new JLabel("Nurse ID:"));
            p.add(d1);
            p.add(new JLabel("Qualification:"));
            p.add(d2);
            p.add(new JLabel("Department:"));
            p.add(d3);
        }
        if (JOptionPane.showConfirmDialog(this, p, "Add " + role, JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
            return;
        try {
            User u = makeUser(role, id.getText(), pass.getText(), name.getText(), email.getText(), contact.getText(), d1.getText(), d2.getText(), d3.getText());
            manager.add(u);
            m.addRow(new Object[]{u.getUserId(), u.getName(), u.getRole(), u.getEmail(), u.getContactNo()});
            showDashboard();
        } catch (Exception e) {
            showError(e);
        }
    }

    private User makeUser(String role, String id, String pass, String name, String email, String contact, String d1, String d2, String d3) throws InvalidData {
        try {
            return switch (role) {
                case "Admin" -> new Admin(id, pass, name, email, contact, d1);
                case "Doctor" -> new Doctor(id, pass, name, email, contact, d1, d2);
                case "Patient" -> new Patient(id, pass, name, email, contact, d1, Integer.parseInt(d2), d3);
                case "Receptionist" -> new Receptionist(id, pass, name, email, contact, d1, d2);
                default -> new Nurse(id, pass, name, email, contact, d1, d2, d3);
            };
        } catch (NumberFormatException e) {
            throw new InvalidData("Patient age must be a number.");
        }
    }

    private void updateUserDialog(User u, DefaultTableModel m) {
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        JTextField name = new JTextField(u.getName()), email = new JTextField(u.getEmail()), contact = new JTextField(u.getContactNo()), pass = new JTextField(u.getPassword());
        p.add(new JLabel("ID:"));
        p.add(new JLabel(u.getUserId()));
        p.add(new JLabel("Password:"));
        p.add(pass);
        p.add(new JLabel("Name:"));
        p.add(name);
        p.add(new JLabel("Email:"));
        p.add(email);
        p.add(new JLabel("Contact:"));
        p.add(contact);
        if (JOptionPane.showConfirmDialog(this, p, "Update User", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
            return;
        u.setPassword(pass.getText());
        u.setName(name.getText());
        u.setEmail(email.getText());
        u.setContactNo(contact.getText());
        try {
            manager.update(u);
            refreshTableModel(m, u.getRole());
            showDashboard();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void refreshTableModel(DefaultTableModel m, String role) {
        m.setRowCount(0);
        for (User u : manager.data().users)
            if (u.getRole().equals(role))
                m.addRow(new Object[]{u.getUserId(), u.getName(), u.getRole(), u.getEmail(), u.getContactNo()});
    }

    private void showUserList(String role) {
        List<User> list = manager.data().users.stream().filter(u -> u.getRole().equals(role)).toList();
        DefaultTableModel m = new DefaultTableModel(new Object[]{"ID", "Name", "Role", "Email", "Contact"}, 0);
        for (User u : list)
            m.addRow(new Object[]{u.getUserId(), u.getName(), u.getRole(), u.getEmail(), u.getContactNo()});
        JTable t = new JTable(m);
        t.setRowHeight(28);
        JScrollPane sp = new JScrollPane(t);
        sp.setPreferredSize(new Dimension(720, 400));
        JOptionPane.showMessageDialog(this, sp, role + " List", JOptionPane.PLAIN_MESSAGE);
    }

    private void appointmentDialog() {
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        JTextField id = new JTextField(), doctor = new JTextField(), patient = new JTextField(), date = new JTextField();
        JComboBox<String> status = new JComboBox<>(new String[]{"Scheduled", "Accepted", "Rescheduled", "Cancelled"});
        p.add(new JLabel("Appointment ID:"));
        p.add(id);
        p.add(new JLabel("Doctor ID:"));
        p.add(doctor);
        p.add(new JLabel("Patient ID:"));
        p.add(patient);
        p.add(new JLabel("Date / Time:"));
        p.add(date);
        p.add(new JLabel("Status:"));
        p.add(status);
        if (JOptionPane.showConfirmDialog(this, p, "Schedule Appointment", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                manager.addAppointment(new Appointment(id.getText(), doctor.getText(), patient.getText(), date.getText(), status.getSelectedItem().toString()));
                JOptionPane.showMessageDialog(this, "Appointment scheduled successfully.");
            } catch (Exception e) {
                showError(e);
            }
        }
        String[] columns = {"ID", "Doctor", "Patient", "Date", "Status"};
        DefaultTableModel m = new DefaultTableModel(columns, 0);
        for (Appointment a : manager.data().appointments)
            m.addRow(new Object[]{a.appointmentId, a.doctorId, a.patientId, a.date, a.status});
        JTable t = new JTable(m);
        t.setRowHeight(27);
        JButton cancel = new JButton("Cancel Selected");
        cancel.addActionListener(e -> {
            int row = t.getSelectedRow();
            if (row < 0) return;
            try {
                manager.status(m.getValueAt(row, 0).toString(), "Cancelled");
                m.setValueAt("Cancelled", row, 4);
                showDashboard();
            } catch (Exception ex) {
                showError(ex);
            }
        });
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(750, 430));
        panel.add(new JScrollPane(t), BorderLayout.CENTER);
        panel.add(cancel, BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(this, panel, "Appointment Information", JOptionPane.PLAIN_MESSAGE);
        showDashboard();
    }

    private void medicalRecordDialog() {
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        JTextField id = new JTextField(), patient = new JTextField(), details = new JTextField();
        p.add(new JLabel("Medical Record ID:"));
        p.add(id);
        p.add(new JLabel("Patient ID:"));
        p.add(patient);
        p.add(new JLabel("Details:"));
        p.add(details);
        if (JOptionPane.showConfirmDialog(this, p, "Add Medical Record", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                manager.addRecord(new MedicalRecord(id.getText(), patient.getText(), details.getText()));
                JOptionPane.showMessageDialog(this, "Medical record saved successfully.");
            } catch (Exception e) {
                showError(e);
            }
        }
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Record ID", "Patient ID", "Details"}, 0);
        for (MedicalRecord r : manager.data().records)
            m.addRow(new Object[]{r.medicalRecordsId, r.patientId, r.details});
        JTable t = new JTable(m);
        t.setRowHeight(27);
        JScrollPane sp = new JScrollPane(t);
        sp.setPreferredSize(new Dimension(700, 380));
        JOptionPane.showMessageDialog(this, sp, "Medical Records", JOptionPane.PLAIN_MESSAGE);
        showDashboard();
    }

    private void paymentDialog() {
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        JTextField id = new JTextField(), doctor = new JTextField(), patient = new JTextField(), amount = new JTextField();
        p.add(new JLabel("Payment ID:"));
        p.add(id);
        p.add(new JLabel("Doctor ID:"));
        p.add(doctor);
        p.add(new JLabel("Patient ID:"));
        p.add(patient);
        p.add(new JLabel("Amount:"));
        p.add(amount);
        if (JOptionPane.showConfirmDialog(this, p, "Doctor Fee Payment", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                manager.addPayment(new Payment(id.getText(), doctor.getText(), patient.getText(), Double.parseDouble(amount.getText())));
                JOptionPane.showMessageDialog(this, "Payment recorded successfully.");
            } catch (Exception e) {
                showError(e);
            }
        }
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Payment ID", "Doctor ID", "Patient ID", "Amount"}, 0);
        for (Payment x : manager.data().payments)
            m.addRow(new Object[]{x.paymentId, x.doctorId, x.patientId, x.amount});
        JTable t = new JTable(m);
        t.setRowHeight(27);
        JScrollPane sp = new JScrollPane(t);
        sp.setPreferredSize(new Dimension(700, 380));
        JOptionPane.showMessageDialog(this, sp, "Payment Information", JOptionPane.PLAIN_MESSAGE);
        showDashboard();
    }

    private void showError(Exception e) {
        showError(e.getMessage());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Hospital Management System - Error", JOptionPane.ERROR_MESSAGE);
    }
}
