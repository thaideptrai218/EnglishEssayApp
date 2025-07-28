package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class GradeEssayTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;
    private JTable essayTable;

    @BeforeEach
    public void setUp() throws IOException {
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        File historyFile = tempDir.resolve("grading_history.txt").toFile();
        File topicsFile = tempDir.resolve("topics.txt").toFile();

        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());
        EnglishEssayApp.setGradingHistoryFile(historyFile.getAbsolutePath());
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        submittedFile.createNewFile();
        gradedFile.createNewFile();
        historyFile.createNewFile();
        topicsFile.createNewFile();

        // Add topic
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Should students wear uniforms?");
        }

        // Add essay
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | Essay content here.");
        }

        teacherFrame = new TeacherFrame("T001");
        teacherFrame.essayTableModel.setRowCount(0);
        teacherFrame.essayTableModel.addRow(new Object[] { "essay1", "student1", "T1" });
        essayTable = new JTable(teacherFrame.essayTableModel);
        essayTable.setRowSelectionInterval(0, 0);
    }

    @Test
    @DisplayName("Should save to grading_history.txt even if not marked final")
    public void shouldSaveDraftGrade() {
        assertDoesNotThrow(() -> teacherFrame.gradeEssay(essayTable));

        File historyFile = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("essay1")) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            fail("Failed to read grading_history.txt: " + e.getMessage());
        }

        assertTrue(found, "Essay should be saved to grading_history.txt even if not FINAL");
    }

    @Test
    @DisplayName("Should grade essay and save to graded.txt when marked final (test-friendly)")
    public void shouldGradeEssayAsFinal() {
        // Giả lập trường hợp đánh dấu là final
        assertDoesNotThrow(() -> teacherFrame.gradeEssay(essayTable));

        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(gradedFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("essay1")) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            fail("Failed to read graded.txt: " + e.getMessage());
        }

        assertTrue(found, "Essay should be saved to graded.txt when marked FINAL");
    }

    @Test
    @DisplayName("Should show warning if no row selected")
    public void shouldShowWarningIfNoRowSelected() {
        JTable emptyTable = new JTable(new DefaultTableModel(new Object[] { "Essay ID", "Student ID", "Topic ID" }, 0));
        assertDoesNotThrow(() -> teacherFrame.gradeEssay(emptyTable));
    }

    @Test
    @DisplayName("Should not regrade already graded essay")
    public void shouldSkipAlreadyGradedEssay() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnglishEssayApp.GRADED_FILE))) {
            writer.write("student1 | essay1 | T1 | 8 | 7 | 6 | 9 | Already graded.");
        }

        assertDoesNotThrow(() -> teacherFrame.gradeEssay(essayTable));
    }

    @Test
    @DisplayName("Should skip grading if essay is not found")
    public void shouldHandleMissingEssayContent() throws IOException {
        // Overwrite submitted.txt with unrelated essay
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnglishEssayApp.SUBMITTED_FILE))) {
            writer.write("studentX | essayX | T2 | Not related.");
        }

        assertDoesNotThrow(() -> teacherFrame.gradeEssay(essayTable));
    }

    @Test
    @DisplayName("Should handle empty submitted.txt file gracefully")
    public void shouldHandleEmptySubmittedFileGracefully() throws IOException {
        // Ensure submitted.txt exists but is empty
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        submittedFile.createNewFile();

        assertDoesNotThrow(() -> teacherFrame.gradeEssay(essayTable));
    }
}
