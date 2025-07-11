package com.dxlab.eas.soldemo.englishessayapp;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel roleLabel = new JLabel("Role:");
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Student", "Teacher"});
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String role = (String) roleCombo.getSelectedItem();
            String id = idField.getText().trim();
            if (!id.isEmpty()) {
                if (role.equals("Student")) {
                    new StudentFrame(id).setVisible(true);
                } else {
                    new TeacherFrame(id).setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter ID");
            }
        });

        panel.add(roleLabel);
        panel.add(roleCombo);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel());
        panel.add(loginButton);
        add(panel);
        this.setVisible(true);
    }
}
