package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ViewEssayTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;
    private JTable essayTable;
    private JTable gradedTable;

    @BeforeEach
    public void setUp() throws IOException {
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        File logFile = tempDir.resolve("view_log.txt").toFile();

        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());
        EnglishEssayApp.setViewLogFile(logFile.getAbsolutePath());

        submittedFile.createNewFile();
        gradedFile.createNewFile();
        topicsFile.createNewFile();
        logFile.createNewFile();

        teacherFrame = new TeacherFrame("T001");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Should students wear uniforms?");
        }

        teacherFrame.essayTableModel.setRowCount(0);
        teacherFrame.essayTableModel.addRow(new Object[] { "essay1", "student1", "T1" });
        essayTable = new JTable(teacherFrame.essayTableModel);

        teacherFrame.gradedTableModel.setRowCount(0);
        gradedTable = new JTable(teacherFrame.gradedTableModel);
    }

    @Test
    @DisplayName("Should view ungraded essay without crashing")
    public void shouldViewUngradedEssay() throws IOException {
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | This is the essay content.");
        }

        essayTable.setRowSelectionInterval(0, 0);

        assertDoesNotThrow(() -> {
            teacherFrame.viewEssay(false, gradedTable, essayTable);
        });
    }

    @Test
    @DisplayName("Should view graded essay with scores without crashing")
    public void shouldViewGradedEssay() throws IOException {
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | This is a graded essay.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 7 | 8 | 6 | 9 | Great job.");
        }

        teacherFrame.gradedTableModel.addRow(new Object[] { "essay1", "student1", "T1", "7", "8", "6", "9" });
        gradedTable.setRowSelectionInterval(0, 0);

        assertDoesNotThrow(() -> {
            teacherFrame.viewEssay(true, gradedTable, essayTable);
        });
    }

    @Test
    @DisplayName("Should handle missing essay in submitted.txt gracefully")
    public void shouldHandleMissingEssayGracefully() {
        essayTable.setRowSelectionInterval(0, 0);

        assertDoesNotThrow(() -> {
            teacherFrame.viewEssay(false, gradedTable, essayTable);
        });
    }

    @Test
    @DisplayName("Should handle missing submitted.txt file gracefully")
    public void shouldHandleMissingFileGracefully() {
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        submittedFile.delete();

        essayTable.setRowSelectionInterval(0, 0);

        assertDoesNotThrow(() -> {
            teacherFrame.viewEssay(false, gradedTable, essayTable);
        });
    }
}
