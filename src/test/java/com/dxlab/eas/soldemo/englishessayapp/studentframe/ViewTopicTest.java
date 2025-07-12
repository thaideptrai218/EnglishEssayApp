package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.DialogManager;
import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ViewTopicTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;
    private MockDialogManager mockDialogManager;

    /**
     * A mock implementation of the DialogManager for testing purposes.
     * Instead of showing a real dialog, it captures the messages that would be
     * shown.
     */
    private static class MockDialogManager implements DialogManager {
        private String lastMessage;
        private String lastTitle;

        @Override
        public void showMessage(String message, String title, int messageType) {
            this.lastMessage = message;
            this.lastTitle = title;
        }

        @Override
        public int showConfirmDialog(String message, String title, int optionType) {
            this.lastMessage = message;
            this.lastTitle = title;
            // Default to 'No' for automated tests to prevent accidental changes.
            return JOptionPane.NO_OPTION;
        }

        public String getLastMessage() {
            return lastMessage;
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

        mockDialogManager = new MockDialogManager();
        studentFrame = new StudentFrame("testStudent", mockDialogManager);
    }

    @Test
    @DisplayName("Should show topic description when a topic is selected")
    public void testViewTopic_TopicSelected() {
        // Arrange
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        studentFrame.topicTable.setRowSelectionInterval(0, 0);

        // Act
        studentFrame.viewTopic();

        // Assert
        // Verify that the dialog manager was asked to show the correct topic
        // description.
        assertEquals("Test Topic 1", mockDialogManager.getLastMessage());
    }

    @Test
    @DisplayName("Should show an error message when no topic is selected")
    public void testViewTopic_NoTopicSelected() {
        // Arrange
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        // No row is selected

        // Act
        studentFrame.viewTopic();

        // Assert
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage());
    }

    @Test
    @DisplayName("Should show an error message when table is empty")
    public void testViewTopic_EmptyTable() {
        // Arrange: The table model is empty by default
        // Act
        studentFrame.viewTopic();

        // Assert
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage());
    }
}
