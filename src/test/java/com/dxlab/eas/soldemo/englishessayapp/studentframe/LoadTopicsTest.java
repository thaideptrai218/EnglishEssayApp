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

public class LoadTopicsTest {

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
    @DisplayName("Should load topics successfully when file exists")
    public void shouldLoadTopicsSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
            writer.newLine();
            writer.write("T2 | Test Topic 2");
        }
        
        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertEquals(2, model.getRowCount());
        assertEquals("T1", model.getValueAt(0, 0));
        assertEquals("Test Topic 1", model.getValueAt(0, 1));
    }

    @Test
    @DisplayName("Should load no topics when file is empty")
    public void shouldNotLoadTopicsWhenFileIsEmpty() {
        // Arrange: The topics file is already created empty in setUp.
        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should load default topics when file does not exist")
    public void shouldLoadDefaultTopicsWhenFileDoesNotExist() {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        topicsFile.delete();

        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertEquals(3, model.getRowCount());
        assertEquals("T1", model.getValueAt(0, 0));
    }

    @Test
    @DisplayName("Should ignore malformed lines and load valid topics")
    public void shouldIgnoreMalformedLinesWhenLoadingTopics() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Valid Topic 1");
            writer.newLine();
            writer.write("malformed-line");
            writer.newLine();
            writer.write("T2 | Valid Topic 2");
        }

        // Act
        studentFrame = new StudentFrame("testStudent", new MockDialogManager());

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertEquals(2, model.getRowCount());
    }
}
