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

public class LoadSubmittedTest {

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
    @DisplayName("Should load submitted essays for the current student")
    public void shouldLoadSubmittedSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write(currentStudentId + " | essay1 | T1 | This is an essay.");
            writer.newLine();
            writer.write("otherStudent | essay2 | T2 | This is another essay.");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("testStudent | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
        }

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
        assertEquals("Task: 8, Coherence: 7, Lexical: 8, Grammar: 7", model.getValueAt(0, 2));
    }

    @Test
    @DisplayName("Should not load submitted essays when none exist for the student")
    public void shouldNotLoadSubmittedWhenNoEssaysForStudent() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("otherStudent | essay2 | T2 | This is another essay.");
        }

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load submitted essays when file is empty")
    public void shouldNotLoadSubmittedWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load submitted essays when file does not exist")
    public void shouldNotLoadSubmittedWhenFileDoesNotExist() {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        submittedFile.delete();

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should ignore malformed lines and load valid submitted essays")
    public void shouldIgnoreMalformedLinesWhenLoadingSubmitted() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write(currentStudentId + " | essay1 | T1 | This is a valid essay.");
            writer.newLine();
            writer.write("malformed-line");
            writer.newLine();
            writer.write(currentStudentId + " | essay2 | T2 | This is another valid essay.");
        }

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(2, model.getRowCount());
    }
}
