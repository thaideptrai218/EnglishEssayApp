package com.dxlab.eas.soldemo.englishessayapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherFrame extends JFrame {
    private String teacherId;
    private DefaultTableModel essayTableModel;
    private DefaultTableModel gradedTableModel;
    private DefaultTableModel historyTableModel;
    private JTextField studentFilterField;
    private JTextField topicFilterField;

    public TeacherFrame(String teacherId) {
        this.teacherId = teacherId;
        setTitle("Teacher Panel - ID: " + teacherId);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Toolbar with Logout
        JToolBar toolBar = new JToolBar();
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        toolBar.add(logoutButton);
        add(toolBar, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Grade Essays Tab (Main View)
        JPanel gradePanel = new JPanel(new BorderLayout());
        essayTableModel = new DefaultTableModel(new String[] { "Essay ID", "Student ID", "Topic ID" }, 0);
        JTable essayTable = new JTable(essayTableModel);

        JPanel filterPanel = new JPanel();
        studentFilterField = new JTextField(10);
        topicFilterField = new JTextField(10);
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> loadEssays());
        filterPanel.add(new JLabel("Student ID:"));
        filterPanel.add(studentFilterField);
        filterPanel.add(new JLabel("Topic ID:"));
        filterPanel.add(topicFilterField);
        filterPanel.add(filterButton);
        JPanel gradeButtonPanel = new JPanel();
        JButton viewButton = new JButton("View Essay");
        JButton gradeButton = new JButton("Grade Essay");

        gradeButtonPanel.add(viewButton);
        gradeButtonPanel.add(gradeButton);
        gradePanel.add(filterPanel, BorderLayout.NORTH);
        gradePanel.add(new JScrollPane(essayTable), BorderLayout.CENTER);
        gradePanel.add(gradeButtonPanel, BorderLayout.SOUTH);
        loadEssays();

        // Graded Essays Tab (Main View)
        JPanel gradedPanel = new JPanel(new BorderLayout());
        gradedTableModel = new DefaultTableModel(
                new String[] { "Essay ID", "Student ID", "Topic ID", "Task", "Coherence", "Lexical", "Grammar" }, 0);
        JTable gradedTable = new JTable(gradedTableModel);
        gradedTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewEssay(true, gradedTable, essayTable);
                }
            }
        });
        gradeButton.addActionListener(e -> gradeEssay(essayTable));
        viewButton.addActionListener(e -> viewEssay(false, gradedTable, essayTable));
        essayTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewEssay(false, gradedTable, essayTable);
                }
            }
        });
        JButton viewGradedButton = new JButton("View Graded Essay");
        JButton editGradeButton = new JButton("Edit Grade");
        viewGradedButton.addActionListener(e -> viewEssay(true, gradedTable, essayTable));
        editGradeButton.addActionListener(e -> editGrade(gradedTable));
        JPanel gradedButtonPanel = new JPanel();
        gradedButtonPanel.add(viewGradedButton);
        gradedButtonPanel.add(editGradeButton);
        gradedPanel.add(new JScrollPane(gradedTable), BorderLayout.CENTER);
        gradedPanel.add(gradedButtonPanel, BorderLayout.SOUTH);
        loadGradedEssays();

        // Grading History Tab (Main View)
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyTableModel = new DefaultTableModel(new String[] { "Essay ID", "Student ID", "Topic ID", "Timestamp" },
                0);
        JTable historyTable = new JTable(historyTableModel);
        historyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewGradingHistory(historyTable);
                }
            }
        });
        JButton viewHistoryButton = new JButton("View Grading Details");
        viewHistoryButton.addActionListener(e -> viewGradingHistory(historyTable));
        historyPanel.add(new JScrollPane(historyTable), BorderLayout.CENTER);
        historyPanel.add(viewHistoryButton, BorderLayout.SOUTH);
        loadGradingHistory();

        tabbedPane.addTab("Grade Essays", gradePanel);
        tabbedPane.addTab("Graded Essays", gradedPanel);
        tabbedPane.addTab("Grading History", historyPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void loadEssays() {
        essayTableModel.setRowCount(0);
        String studentFilter = studentFilterField.getText().trim();
        String topicFilter = topicFilterField.getText().trim();
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.SUBMITTED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 4);
                if (parts.length >= 4 &&
                        (studentFilter.isEmpty() || parts[0].equals(studentFilter)) &&
                        (topicFilter.isEmpty() || parts[2].equals(topicFilter))) {
                    essayTableModel.addRow(new Object[] { parts[1], parts[0], parts[2] });
                }
            }
        } catch (IOException ex) {
            // File may not exist yet
        }
    }

    private void loadGradedEssays() {
        gradedTableModel.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 8);
                if (parts.length >= 7) {
                    gradedTableModel.addRow(
                            new Object[] { parts[1], parts[0], parts[2], parts[3], parts[4], parts[5], parts[6] });
                }
            }
        } catch (IOException ex) {
            // File may not exist yet
        }
    }

    private void loadGradingHistory() {
        historyTableModel.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADING_HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 9);
                if (parts.length >= 9 && parts[0].equals(teacherId)) {
                    historyTableModel.addRow(new Object[] { parts[2], parts[1], parts[3], parts[8] });
                }
            }
        } catch (IOException ex) {
            // File may not exist yet
        }
    }

    private String getTopicContent(String topicId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.TOPICS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 2);
                if (parts.length >= 2 && parts[0].equals(topicId)) {
                    return parts[1];
                }
            }
        } catch (IOException ex) {
            // File may not exist
        }
        return "Unknown topic";
    }

    private void viewEssay(boolean isGraded, JTable gradedTable, JTable essayTable) {
        JTable table = isGraded ? gradedTable : essayTable;
        DefaultTableModel model = isGraded ? gradedTableModel : essayTableModel;
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an essay!");
            return;
        }
        String essayId = (String) model.getValueAt(row, 0);
        String studentId = (String) model.getValueAt(row, 1);
        String topicId = (String) model.getValueAt(row, 2);

        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.SUBMITTED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 4);
                if (parts.length >= 4 && parts[1].equals(essayId)) {
                    content = parts[3];
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading essay: " + ex.getMessage());
            return;
        }

        // Log view
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnglishEssayApp.VIEW_LOG_FILE, true))) {
            writer.write(String.format("%s | %s | %s | %s | %s",
                    teacherId, studentId, essayId, topicId, System.currentTimeMillis()));
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error logging view: " + ex.getMessage());
        }

        JTextArea contentArea = new JTextArea("Topic: " + getTopicContent(topicId) + "\n\nContent:\n" + content);
        contentArea.setEditable(false);
        JTextArea noteArea = new JTextArea(5, 30);
        JPanel viewPanel = new JPanel(new BorderLayout(10, 10));
        viewPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        viewPanel.add(new JLabel("View Notes:"), BorderLayout.SOUTH);
        viewPanel.add(new JScrollPane(noteArea), BorderLayout.SOUTH);

        if (isGraded) {
            try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADED_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" \\| ", 8);
                    if (parts.length >= 7 && parts[1].equals(essayId)) {
                        String gradeInfo = String.format(
                                "\n\nGrade:\nTask Achievement: %s\nCoherence and Cohesion: %s\n" +
                                        "Lexical Resource: %s\nGrammatical Range and Accuracy: %s\nComments: %s",
                                parts[3], parts[4], parts[5], parts[6], parts[7]);
                        contentArea.append(gradeInfo);
                        break;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading grade: " + ex.getMessage());
            }
        }

        int result = JOptionPane.showConfirmDialog(this, viewPanel, "View Essay: " + essayId,
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !noteArea.getText().trim().isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnglishEssayApp.VIEW_LOG_FILE, true))) {
                writer.write(String.format("%s | %s | %s | %s | %s | %s",
                        teacherId, studentId, essayId, topicId, System.currentTimeMillis(), noteArea.getText().trim()));
                writer.newLine();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving note: " + ex.getMessage());
            }
        }
    }

    private void gradeEssay(JTable essayTable) {
        int row = essayTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an essay!");
            return;
        }
        String essayId = (String) essayTableModel.getValueAt(row, 0);
        String studentId = (String) essayTableModel.getValueAt(row, 1);
        String topicId = (String) essayTableModel.getValueAt(row, 2);

        // Check if already graded
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 8);
                if (parts.length >= 7 && parts[1].equals(essayId)) {
                    JOptionPane.showMessageDialog(this,
                            "This essay has already been graded. Use 'Edit Grade' to modify.");
                    return;
                }
            }
        } catch (IOException ex) {
            // File may not exist
        }

        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.SUBMITTED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 4);
                if (parts.length >= 4 && parts[1].equals(essayId)) {
                    content = parts[3];
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading essay: " + ex.getMessage());
            return;
        }

        JTextArea contentArea = new JTextArea("Topic: " + getTopicContent(topicId) + "\n\nContent:\n" + content);
        contentArea.setEditable(false);
        JSlider taskAchievementSlider = new JSlider(JSlider.HORIZONTAL, 1, 9, 1);
        JSlider coherenceSlider = new JSlider(JSlider.HORIZONTAL, 1, 9, 1);
        JSlider lexicalSlider = new JSlider(JSlider.HORIZONTAL, 1, 9, 1);
        JSlider grammarSlider = new JSlider(JSlider.HORIZONTAL, 1, 9, 1);
        taskAchievementSlider.setMajorTickSpacing(1);
        coherenceSlider.setMajorTickSpacing(1);
        lexicalSlider.setMajorTickSpacing(1);
        grammarSlider.setMajorTickSpacing(1);
        taskAchievementSlider.setPaintTicks(true);
        coherenceSlider.setPaintTicks(true);
        lexicalSlider.setPaintTicks(true);
        grammarSlider.setPaintTicks(true);
        taskAchievementSlider.setPaintLabels(true);
        coherenceSlider.setPaintLabels(true);
        lexicalSlider.setPaintLabels(true);
        grammarSlider.setPaintLabels(true);
        JTextArea commentArea = new JTextArea(5, 30);
        JCheckBox finalGradeCheck = new JCheckBox("Mark as Final Grade");

        JPanel gradePanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.add(new JLabel("Task Achievement (1-9):"));
        inputPanel.add(taskAchievementSlider);
        inputPanel.add(new JLabel("Coherence and Cohesion (1-9):"));
        inputPanel.add(coherenceSlider);
        inputPanel.add(new JLabel("Lexical Resource (1-9):"));
        inputPanel.add(lexicalSlider);
        inputPanel.add(new JLabel("Grammatical Range and Accuracy (1-9):"));
        inputPanel.add(grammarSlider);
        inputPanel.add(new JLabel("Comments:"));
        inputPanel.add(new JScrollPane(commentArea));
        inputPanel.add(new JLabel());
        inputPanel.add(finalGradeCheck);

        gradePanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        gradePanel.add(inputPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(this, gradePanel,
                "Grade Essay: " + essayId, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String taskAchievement = String.valueOf(taskAchievementSlider.getValue());
            String coherence = String.valueOf(coherenceSlider.getValue());
            String lexical = String.valueOf(lexicalSlider.getValue());
            String grammar = String.valueOf(grammarSlider.getValue());
            String comments = commentArea.getText().trim();
            String status = finalGradeCheck.isSelected() ? "FINAL" : "DRAFT";

            try {
                // Save to grading history
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter(EnglishEssayApp.GRADING_HISTORY_FILE, true))) {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    writer.write(String.format("%s | %s | %s | %s | %s | %s | %s | %s | %s",
                            teacherId, studentId, essayId, topicId, taskAchievement, coherence, lexical, grammar,
                            comments, timestamp));
                    writer.newLine();
                    historyTableModel.addRow(new Object[] { essayId, studentId, topicId, timestamp });
                }

                // Save to final grades if marked as final
                if (status.equals("FINAL")) {
                    try (BufferedWriter writer = new BufferedWriter(
                            new FileWriter(EnglishEssayApp.GRADED_FILE, true))) {
                        writer.write(String.format("%s | %s | %s | %s | %s | %s | %s | %s",
                                studentId, essayId, topicId, taskAchievement, coherence, lexical, grammar, comments));
                        writer.newLine();
                        gradedTableModel.addRow(new Object[] { essayId, studentId, topicId, taskAchievement, coherence,
                                lexical, grammar });
                    }
                }

                JOptionPane.showMessageDialog(this, "Grade saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving grade: " + ex.getMessage());
            }
        }
    }

    private void editGrade(JTable gradedTable) {
        int row = gradedTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a graded essay!");
            return;
        }
        String essayId = (String) gradedTableModel.getValueAt(row, 0);
        String studentId = (String) gradedTableModel.getValueAt(row, 1);
        String topicId = (String) gradedTableModel.getValueAt(row, 2);

        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.SUBMITTED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 4);
                if (parts.length >= 4 && parts[1].equals(essayId)) {
                    content = parts[3];
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading essay: " + ex.getMessage());
            return;
        }

        JTextArea contentArea = new JTextArea("Topic: " + getTopicContent(topicId) + "\n\nContent:\n" + content);
        contentArea.setEditable(false);
        JSlider taskAchievementSlider = new JSlider(JSlider.HORIZONTAL, 1, 9,
                Integer.parseInt((String) gradedTableModel.getValueAt(row, 3)));
        JSlider coherenceSlider = new JSlider(JSlider.HORIZONTAL, 1, 9,
                Integer.parseInt((String) gradedTableModel.getValueAt(row, 4)));
        JSlider lexicalSlider = new JSlider(JSlider.HORIZONTAL, 1, 9,
                Integer.parseInt((String) gradedTableModel.getValueAt(row, 5)));
        JSlider grammarSlider = new JSlider(JSlider.HORIZONTAL, 1, 9,
                Integer.parseInt((String) gradedTableModel.getValueAt(row, 6)));
        taskAchievementSlider.setMajorTickSpacing(1);
        coherenceSlider.setMajorTickSpacing(1);
        lexicalSlider.setMajorTickSpacing(1);
        grammarSlider.setMajorTickSpacing(1);
        taskAchievementSlider.setPaintTicks(true);
        coherenceSlider.setPaintTicks(true);
        lexicalSlider.setPaintTicks(true);
        grammarSlider.setPaintTicks(true);
        taskAchievementSlider.setPaintLabels(true);
        coherenceSlider.setPaintLabels(true);
        lexicalSlider.setPaintLabels(true);
        grammarSlider.setPaintLabels(true);
        JTextArea commentArea = new JTextArea(5, 30);
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 8);
                if (parts.length >= 7 && parts[1].equals(essayId)) {
                    commentArea.setText(parts[7]);
                    break;
                }
            }
        } catch (IOException ex) {
            // File may not exist
        }

        JPanel gradePanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Task Achievement (1-9):"));
        inputPanel.add(taskAchievementSlider);
        inputPanel.add(new JLabel("Coherence and Cohesion (1-9):"));
        inputPanel.add(coherenceSlider);
        inputPanel.add(new JLabel("Lexical Resource (1-9):"));
        inputPanel.add(lexicalSlider);
        inputPanel.add(new JLabel("Grammatical Range and Accuracy (1-9):"));
        inputPanel.add(grammarSlider);
        inputPanel.add(new JLabel("Comments:"));
        inputPanel.add(new JScrollPane(commentArea));

        gradePanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        gradePanel.add(inputPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(this, gradePanel,
                "Edit Grade: " + essayId, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String taskAchievement = String.valueOf(taskAchievementSlider.getValue());
            String coherence = String.valueOf(coherenceSlider.getValue());
            String lexical = String.valueOf(lexicalSlider.getValue());
            String grammar = String.valueOf(grammarSlider.getValue());
            String comments = commentArea.getText().trim();

            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADED_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" \\| ", 8);
                    if (parts.length >= 7 && parts[1].equals(essayId)) {
                        lines.add(String.format("%s | %s | %s | %s | %s | %s | %s | %s",
                                studentId, essayId, topicId, taskAchievement, coherence, lexical, grammar, comments));
                    } else {
                        lines.add(line);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading grades: " + ex.getMessage());
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnglishEssayApp.GRADED_FILE))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error updating grade: " + ex.getMessage());
                return;
            }

            // Save to grading history
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(EnglishEssayApp.GRADING_HISTORY_FILE, true))) {
                String timestamp = String.valueOf(System.currentTimeMillis());
                writer.write(String.format("%s | %s | %s | %s | %s | %s | %s | %s | %s",
                        teacherId, studentId, essayId, topicId, taskAchievement, coherence, lexical, grammar, comments,
                        timestamp));
                writer.newLine();
                historyTableModel.addRow(new Object[] { essayId, studentId, topicId, timestamp });
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving to history: " + ex.getMessage());
            }

            gradedTableModel.setValueAt(taskAchievement, row, 3);
            gradedTableModel.setValueAt(coherence, row, 4);
            gradedTableModel.setValueAt(lexical, row, 5);
            gradedTableModel.setValueAt(grammar, row, 6);
            JOptionPane.showMessageDialog(this, "Grade updated successfully!");
        }
    }

    private void viewGradingHistory(JTable historyTable) {
        int row = historyTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a grading record!");
            return;
        }
        String essayId = (String) historyTableModel.getValueAt(row, 0);
        String studentId = (String) historyTableModel.getValueAt(row, 1);
        String topicId = (String) historyTableModel.getValueAt(row, 2);
        String timestamp = (String) historyTableModel.getValueAt(row, 3);

        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADING_HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ", 9);
                if (parts.length >= 9 && parts[0].equals(teacherId) && parts[2].equals(essayId)
                        && parts[8].equals(timestamp)) {
                    String details = String.format(
                            "Essay ID: %s\nStudent ID: %s\nTopic ID: %s\nTask Achievement: %s\nCoherence and Cohesion: %s\n"
                                    +
                                    "Lexical Resource: %s\nGrammatical Range and Accuracy: %s\nComments: %s\nTimestamp: %s",
                            parts[2], parts[1], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
                    JOptionPane.showMessageDialog(this, details, "Grading History", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading grading history: " + ex.getMessage());
        }
    }
}