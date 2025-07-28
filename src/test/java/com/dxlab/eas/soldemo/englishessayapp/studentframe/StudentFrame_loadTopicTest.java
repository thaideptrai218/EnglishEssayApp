package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.DialogManager;
import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentFrame_loadTopicTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

    private static class MockDialogManager implements DialogManager {
        @Override
        public void showMessage(String message, String title, int messageType) {}
        @Override
        public int showConfirmDialog(String message, String title, int optionType) {
            return JOptionPane.NO_OPTION;
        }
    }

    @BeforeEach
    public void setUp() throws IOException {
        // Prepare the environment before constructing the StudentFrame
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        File draftsFile = tempDir.resolve("drafts.txt").toFile();
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        File gradedFile = tempDir.resolve("graded.txt").toFile();

        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());
        EnglishEssayApp.setDraftsFile(draftsFile.getAbsolutePath());
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Create empty files to prevent constructor errors
        topicsFile.createNewFile();
        draftsFile.createNewFile();
        submittedFile.createNewFile();
        gradedFile.createNewFile();
    }

    @Test
    @DisplayName("TC_LT_01: Normal Path - File Exists")
    public void shouldLoadTopicsSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
            writer.newLine();
            writer.write("T2 | Test Topic 2");
            writer.newLine();
            writer.write("T3 | Test Topic 3");
        }
        
        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertNotNull(model, "Topic table model should not be null");
        assertEquals(3, model.getRowCount(), "Should load 3 topics from file");
        assertEquals("T1", model.getValueAt(0, 0), "First topic ID should be T1");
        assertEquals("Test Topic 1", model.getValueAt(0, 1), "First topic description should match");
        assertEquals("T2", model.getValueAt(1, 0), "Second topic ID should be T2");
        assertEquals("Test Topic 2", model.getValueAt(1, 1), "Second topic description should match");
        assertEquals("T3", model.getValueAt(2, 0), "Third topic ID should be T3");
        assertEquals("Test Topic 3", model.getValueAt(2, 1), "Third topic description should match");
    }

    @Test
    @DisplayName("TC_LT_02: Boundary - Empty File")
    public void shouldNotLoadTopicsWhenFileIsEmpty() {
        // Arrange: The topics file is already created empty in setUp.
        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertNotNull(model, "Topic table model should not be null");
        assertEquals(0, model.getRowCount(), "Should have no topics when file is empty");
    }

    @Test
    @DisplayName("TC_LT_03: Boundary - Non-Existent File")
    public void shouldLoadDefaultTopicsWhenFileDoesNotExist() {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        topicsFile.delete();

        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertNotNull(model, "Topic table model should not be null");
        assertEquals(3, model.getRowCount(), "Should load 3 default topics when file does not exist");
        assertEquals("T1", model.getValueAt(0, 0), "First default topic ID should be T1");
        assertEquals("T2", model.getValueAt(1, 0), "Second default topic ID should be T2");
        assertEquals("T3", model.getValueAt(2, 0), "Third default topic ID should be T3");
        
        // Verify default topic descriptions are loaded (truncated versions)
        String firstDesc = (String) model.getValueAt(0, 1);
        String secondDesc = (String) model.getValueAt(1, 1);
        String thirdDesc = (String) model.getValueAt(2, 1);
        
        assertNotNull(firstDesc, "First topic description should not be null");
        assertNotNull(secondDesc, "Second topic description should not be null");
        assertNotNull(thirdDesc, "Third topic description should not be null");
    }

    @Test
    @DisplayName("TC_LT_04: Abnormal - Malformed Data")
    public void shouldIgnoreMalformedLinesWhenLoadingTopics() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Valid Topic 1");
            writer.newLine();
            writer.write("malformed-line-without-separator");
            writer.newLine();
            writer.write("T2 | Valid Topic 2");
            writer.newLine();
            writer.write("another-malformed-line");
            writer.newLine();
            writer.write("T3 | Valid Topic 3");
        }

        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertNotNull(model, "Topic table model should not be null");
        assertEquals(3, model.getRowCount(), "Should load only valid topics, ignoring malformed lines");
        assertEquals("T1", model.getValueAt(0, 0), "First valid topic ID should be T1");
        assertEquals("Valid Topic 1", model.getValueAt(0, 1), "First valid topic description should match");
        assertEquals("T2", model.getValueAt(1, 0), "Second valid topic ID should be T2");
        assertEquals("Valid Topic 2", model.getValueAt(1, 1), "Second valid topic description should match");
        assertEquals("T3", model.getValueAt(2, 0), "Third valid topic ID should be T3");
        assertEquals("Valid Topic 3", model.getValueAt(2, 1), "Third valid topic description should match");
    }

    @Test
    @DisplayName("Should handle file with only malformed lines")
    public void shouldHandleFileWithOnlyMalformedLines() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("malformed-line-1");
            writer.newLine();
            writer.write("malformed-line-2");
            writer.newLine();
            writer.write("another-malformed-line");
        }

        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertNotNull(model, "Topic table model should not be null");
        assertEquals(0, model.getRowCount(), "Should have no topics when file contains only malformed lines");
    }

    @Test
    @DisplayName("Should handle file with partial malformed data")
    public void shouldHandleFileWithPartialMalformedData() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Valid Topic 1");
            writer.newLine();
            writer.write("T2 | Valid Topic 2");
            writer.newLine();
            writer.write("malformed-line");
            writer.newLine();
            writer.write("T3 | Valid Topic 3");
        }

        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertNotNull(model, "Topic table model should not be null");
        assertEquals(3, model.getRowCount(), "Should load valid topics and ignore malformed line");
        assertEquals("T1", model.getValueAt(0, 0), "First topic ID should be T1");
        assertEquals("T2", model.getValueAt(1, 0), "Second topic ID should be T2");
        assertEquals("T3", model.getValueAt(2, 0), "Third topic ID should be T3");
    }
} 