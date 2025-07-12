package com.dxlab.eas.soldemo.englishessayapp;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JComboBox<String> roleComboBox;
    private JTextField idField;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        roleComboBox = new JComboBox<>(new String[] { "Student", "Teacher" });
        idField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String id = idField.getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter ID");
                return;
            }
            if ("Student".equals(role)) {
                new StudentFrame(id, new DefaultDialogManager()).setVisible(true);
            } else {
                new TeacherFrame(id).setVisible(true);
            }
            dispose();
        });

        setLayout(new GridLayout(3, 2));
        add(new JLabel("Role:"));
        add(roleComboBox);
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel());
        add(loginButton);

        setVisible(true);
    }
}
