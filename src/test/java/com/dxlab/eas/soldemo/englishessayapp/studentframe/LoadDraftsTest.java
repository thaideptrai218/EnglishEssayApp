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

public class LoadDraftsTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;
    private final String currentStudentId = "testStudent";

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

        studentFrame = new StudentFrame(currentStudentId, new MockDialogManager());
    }

    @Test
    @DisplayName("Should load drafts for the current student")
    public void shouldLoadDraftsSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File draftsFile = new File(EnglishEssayApp.DRAFTS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(draftsFile))) {
            writer.write(currentStudentId + " | draft1 | T1 | This is a draft.");
            writer.newLine();
            writer.write("otherStudent | draft2 | T2 | This is another draft.");
        }

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("draft1", model.getValueAt(0, 0));
    }

    @Test
    @DisplayName("Should not load drafts when none exist for the student")
    public void shouldNotLoadDraftsWhenNoDraftsForStudent() throws IOException {
        // Arrange
        File draftsFile = new File(EnglishEssayApp.DRAFTS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(draftsFile))) {
            writer.write("otherStudent | draft2 | T2 | This is another draft.");
        }

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load drafts when file is empty")
    public void shouldNotLoadDraftsWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load drafts when file does not exist")
    public void shouldNotLoadDraftsWhenFileDoesNotExist() {
        // Arrange
        File draftsFile = new File(EnglishEssayApp.DRAFTS_FILE);
        draftsFile.delete();

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should ignore malformed lines and load valid drafts")
    public void shouldIgnoreMalformedLinesWhenLoadingDrafts() throws IOException {
        // Arrange
        File draftsFile = new File(EnglishEssayApp.DRAFTS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(draftsFile))) {
            writer.write(currentStudentId + " | draft1 | T1 | This is a valid draft.");
            writer.newLine();
            writer.write("malformed-line");
            writer.newLine();
            writer.write(currentStudentId + " | draft2 | T2 | This is another valid draft.");
        }

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(2, model.getRowCount());
    }
}
