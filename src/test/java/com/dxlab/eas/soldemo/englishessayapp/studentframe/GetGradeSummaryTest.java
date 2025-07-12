package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.DialogManager;
import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetGradeSummaryTest {

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

        studentFrame = new StudentFrame("testStudent", new MockDialogManager());
    }

    @Test
    @DisplayName("Should return grade summary when grade exists")
    public void shouldReturnGradeSummaryWhenGradeExists() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
        }
        
        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Task: 8, Coherence: 7, Lexical: 8, Grammar: 7", summary);
    }

    @Test
    @DisplayName("Should return 'Not graded' when grade does not exist")
    public void shouldReturnNotGradedWhenGradeDoesNotExist() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay2 | T1 | 8 | 7 | 8 | 7 | Good job.");
        }

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary);
    }

    @Test
    @DisplayName("Should return 'Not graded' when file is empty")
    public void shouldReturnNotGradedWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary);
    }

    @Test
    @DisplayName("Should return 'Not graded' when file does not exist")
    public void shouldReturnNotGradedWhenFileDoesNotExist() {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        gradedFile.delete();

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary);
    }

    @Test
    @DisplayName("Should return 'Not graded' for a malformed line")
    public void shouldReturnNotGradedForMalformedLine() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 8 | 7");
        }

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary);
    }

    @Test
    @DisplayName("Should return correct grade summary when multiple entries exist and target is last")
    public void shouldReturnGradeSummaryWhenMultipleEntriesAndTargetIsLast() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 5 | 5 | 5 | 5 | Average.\n");
            writer.write("student2 | essay2 | T2 | 9 | 9 | 9 | 9 | Excellent!\n");
            writer.write("student1 | essay3 | T3 | 8 | 7 | 8 | 7 | Good job.");
        }

        // Act
        String summary = studentFrame.getGradeSummary("essay3");

        // Assert
        assertEquals("Task: 8, Coherence: 7, Lexical: 8, Grammar: 7", summary);
    }
}
