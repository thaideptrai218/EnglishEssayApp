package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.JTable;
import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EditGradeTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;
    private JTable gradedTable;

    @BeforeEach
    public void setUp() throws IOException {
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        File historyFile = tempDir.resolve("grading_history.txt").toFile();
        File topicsFile = tempDir.resolve("topics.txt").toFile();

        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());
        EnglishEssayApp.setGradingHistoryFile(historyFile.getAbsolutePath());
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        gradedFile.createNewFile();
        historyFile.createNewFile();
        topicsFile.createNewFile();

        teacherFrame = new TeacherFrame("T001");
        teacherFrame.gradedTableModel.setRowCount(0);
        teacherFrame.gradedTableModel.addRow(new Object[] {
                "essay1", "student1", "T1", "7", "8", "6", "7"
        });
        gradedTable = new JTable(teacherFrame.gradedTableModel);
        gradedTable.setRowSelectionInterval(0, 0);
    }

    @Test
    @DisplayName("Should skip edit if essay not found")
    public void shouldSkipEditIfEssayNotFound() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnglishEssayApp.GRADED_FILE))) {
            writer.write("studentX | essayX | T2 | 5 | 5 | 5 | 5 | Some comment");
        }

        assertDoesNotThrow(() -> teacherFrame.editGrade(gradedTable));

        // Ensure original line remains unchanged
        try (BufferedReader reader = new BufferedReader(new FileReader(EnglishEssayApp.GRADED_FILE))) {
            String line = reader.readLine();
            assertTrue(line.contains("essayX"));
        }
    }

    @Test
    @DisplayName("Should handle empty graded.txt gracefully")
    public void shouldHandleEmptyGradedFile() {
        assertDoesNotThrow(() -> teacherFrame.editGrade(gradedTable));
    }

    @Test
    @DisplayName("Should handle missing graded.txt gracefully")
    public void shouldHandleMissingGradedFile() {
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        gradedFile.delete();

        assertDoesNotThrow(() -> teacherFrame.editGrade(gradedTable));
    }
}
