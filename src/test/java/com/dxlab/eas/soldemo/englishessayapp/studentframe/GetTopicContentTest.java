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

public class GetTopicContentTest {

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
    @DisplayName("Should return topic content when topic exists")
    public void shouldReturnTopicContentWhenTopicExists() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
        }

        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Test Topic 1", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' when topic does not exist")
    public void shouldReturnUnknownTopicWhenTopicDoesNotExist() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T2 | Test Topic 2");
        }

        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' when file is empty")
    public void shouldReturnUnknownTopicWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' when file does not exist")
    public void shouldReturnUnknownTopicWhenFileDoesNotExist() {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        topicsFile.delete();

        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' for a malformed line")
    public void shouldReturnUnknownTopicForMalformedLine() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1-Test Topic 1");
        }

        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    @DisplayName("Should return correct topic content when multiple entries exist and target is last")
    public void shouldReturnTopicContentWhenMultipleEntriesAndTargetIsLast() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1\n");
            writer.write("T2 | Test Topic 2\n");
            writer.write("T3 | Test Topic 3");
        }

        // Act
        String content = studentFrame.getTopicContent("T3");

        // Assert
        assertEquals("Test Topic 3", content);
    }
}
