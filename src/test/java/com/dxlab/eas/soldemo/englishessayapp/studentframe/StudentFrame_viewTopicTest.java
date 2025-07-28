package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.DialogManager;
import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentFrame_viewTopicTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;
    private JTable topicTable;

    private static class MockDialogManager implements DialogManager {
        private String lastMessage = "";
        private String lastTitle = "";
        private int lastMessageType = -1;

        @Override
        public void showMessage(String message, String title, int messageType) {
            this.lastMessage = message;
            this.lastTitle = title;
            this.lastMessageType = messageType;
        }

        @Override
        public int showConfirmDialog(String message, String title, int optionType) {
            return JOptionPane.NO_OPTION;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public String getLastTitle() {
            return lastTitle;
        }

        public int getLastMessageType() {
            return lastMessageType;
        }

        public void reset() {
            lastMessage = "";
            lastTitle = "";
            lastMessageType = -1;
        }
    }

    private MockDialogManager mockDialogManager;

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

        // Create topics file with test data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
            writer.newLine();
            writer.write("T2 | Test Topic 2");
            writer.newLine();
            writer.write("T3 | Test Topic 3");
        }

        // Create empty files to prevent constructor errors
        draftsFile.createNewFile();
        submittedFile.createNewFile();
        gradedFile.createNewFile();

        mockDialogManager = new MockDialogManager();
        studentFrame = new StudentFrame("testStudent", mockDialogManager);
        topicTable = studentFrame.topicTable;
    }

    @Test
    @DisplayName("TC_VT_01: Normal Path - Topic Selected")
    public void shouldViewTopicSuccessfullyWhenTopicSelected() {
        // Arrange
        topicTable.setRowSelectionInterval(0, 0); // Select first topic
        mockDialogManager.reset();

        // Act
        studentFrame.viewTopic();

        // Assert
        assertEquals("Test Topic 1", mockDialogManager.getLastMessage(), "Should show correct topic description");
        assertEquals("Topic: T1", mockDialogManager.getLastTitle(), "Should show correct dialog title with topic ID");
        assertEquals(JOptionPane.PLAIN_MESSAGE, mockDialogManager.getLastMessageType(), "Should show plain message type");
    }

    @Test
    @DisplayName("TC_VT_02: Abnormal - No Topic Selected")
    public void shouldHandleNoTopicSelected() {
        // Arrange
        topicTable.clearSelection(); // Clear any selection
        mockDialogManager.reset();

        // Act
        studentFrame.viewTopic();

        // Assert
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage(), "Should show error message");
        assertEquals("Warning", mockDialogManager.getLastTitle(), "Should show warning dialog title");
        assertEquals(JOptionPane.WARNING_MESSAGE, mockDialogManager.getLastMessageType(), "Should show warning message type");
    }

    @Test
    @DisplayName("TC_VT_03: Boundary - Empty Table")
    public void shouldHandleEmptyTable() throws IOException {
        // Arrange - Create new StudentFrame with empty topics file
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        topicsFile.delete();
        topicsFile.createNewFile(); // Create empty file

        StudentFrame emptyStudentFrame = new StudentFrame("testStudent", mockDialogManager);
        JTable emptyTopicTable = emptyStudentFrame.topicTable;
        mockDialogManager.reset();

        // Act
        emptyStudentFrame.viewTopic();

        // Assert
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage(), "Should show error message");
        assertEquals("Warning", mockDialogManager.getLastTitle(), "Should show warning dialog title");
        assertEquals(JOptionPane.WARNING_MESSAGE, mockDialogManager.getLastMessageType(), "Should show warning message type");
    }

    @Test
    @DisplayName("Should view different topics correctly")
    public void shouldViewDifferentTopicsCorrectly() {
        // Arrange & Act - View first topic
        topicTable.setRowSelectionInterval(0, 0);
        mockDialogManager.reset();
        studentFrame.viewTopic();

        // Assert first topic
        assertEquals("Test Topic 1", mockDialogManager.getLastMessage(), "Should show first topic description");
        assertEquals("Topic: T1", mockDialogManager.getLastTitle(), "Should show correct dialog title for first topic");

        // Arrange & Act - View second topic
        topicTable.setRowSelectionInterval(1, 1);
        mockDialogManager.reset();
        studentFrame.viewTopic();

        // Assert second topic
        assertEquals("Test Topic 2", mockDialogManager.getLastMessage(), "Should show second topic description");
        assertEquals("Topic: T2", mockDialogManager.getLastTitle(), "Should show correct dialog title for second topic");

        // Arrange & Act - View third topic
        topicTable.setRowSelectionInterval(2, 2);
        mockDialogManager.reset();
        studentFrame.viewTopic();

        // Assert third topic
        assertEquals("Test Topic 3", mockDialogManager.getLastMessage(), "Should show third topic description");
        assertEquals("Topic: T3", mockDialogManager.getLastTitle(), "Should show correct dialog title for third topic");
    }

    @Test
    @DisplayName("Should handle multiple row selection")
    public void shouldHandleMultipleRowSelection() {
        // Arrange
        topicTable.setRowSelectionInterval(0, 2); // Select all rows
        mockDialogManager.reset();

        // Act
        studentFrame.viewTopic();

        // Assert
        assertEquals("Test Topic 1", mockDialogManager.getLastMessage(), "Should show first selected topic description");
        assertEquals("Topic: T1", mockDialogManager.getLastTitle(), "Should show correct dialog title for first selected topic");
        assertEquals(JOptionPane.PLAIN_MESSAGE, mockDialogManager.getLastMessageType(), "Should show plain message type");
    }

    @Test
    @DisplayName("Should handle invalid row selection")
    public void shouldHandleInvalidRowSelection() {
        // Arrange
        topicTable.clearSelection(); // No row is selected
        mockDialogManager.reset();

        // Act
        studentFrame.viewTopic();

        // Assert
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage(), "Should show error message for invalid selection");
        assertEquals("Warning", mockDialogManager.getLastTitle(), "Should show warning dialog title");
        assertEquals(JOptionPane.WARNING_MESSAGE, mockDialogManager.getLastMessageType(), "Should show warning message type");
    }

    @Test
    @DisplayName("Should view topic with long description")
    public void shouldViewTopicWithLongDescription() throws IOException {
        // Arrange - Create topics file with long description
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | This is a very long topic description that contains many words and should be displayed properly in the dialog without any truncation or formatting issues");
        }

        StudentFrame longDescStudentFrame = new StudentFrame("testStudent", mockDialogManager);
        JTable longDescTopicTable = longDescStudentFrame.topicTable;
        longDescTopicTable.setRowSelectionInterval(0, 0);
        mockDialogManager.reset();

        // Act
        longDescStudentFrame.viewTopic();

        // Assert
        String expectedMessage = "This is a very long topic description that contains many words and should be displayed properly in the dialog without any truncation or formatting issues";
        assertEquals(expectedMessage, mockDialogManager.getLastMessage(), "Should show full long topic description");
        assertEquals("Topic: T1", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.PLAIN_MESSAGE, mockDialogManager.getLastMessageType(), "Should show plain message type");
    }

    @Test
    @DisplayName("Should view topic with special characters")
    public void shouldViewTopicWithSpecialCharacters() throws IOException {
        // Arrange - Create topics file with special characters
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Topic with special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?");
        }

        StudentFrame specialCharStudentFrame = new StudentFrame("testStudent", mockDialogManager);
        JTable specialCharTopicTable = specialCharStudentFrame.topicTable;
        specialCharTopicTable.setRowSelectionInterval(0, 0);
        mockDialogManager.reset();

        // Act
        specialCharStudentFrame.viewTopic();

        // Assert
        String expectedMessage = "Topic with special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?";
        assertEquals(expectedMessage, mockDialogManager.getLastMessage(), "Should show topic description with special characters");
        assertEquals("Topic: T1", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.PLAIN_MESSAGE, mockDialogManager.getLastMessageType(), "Should show plain message type");
    }

    @Test
    @DisplayName("Should handle topic with empty description")
    public void shouldHandleTopicWithEmptyDescription() throws IOException {
        // Arrange - Create topics file with empty description
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | ");
        }

        StudentFrame emptyDescStudentFrame = new StudentFrame("testStudent", mockDialogManager);
        JTable emptyDescTopicTable = emptyDescStudentFrame.topicTable;
        emptyDescTopicTable.setRowSelectionInterval(0, 0);
        mockDialogManager.reset();

        // Act
        emptyDescStudentFrame.viewTopic();

        // Assert
        assertEquals("", mockDialogManager.getLastMessage(), "Should show empty description");
        assertEquals("Topic: T1", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.PLAIN_MESSAGE, mockDialogManager.getLastMessageType(), "Should show plain message type");
    }

    @Test
    @DisplayName("Should handle topic with only whitespace description")
    public void shouldHandleTopicWithOnlyWhitespaceDescription() throws IOException {
        // Arrange - Create topics file with whitespace-only description
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 |   \t  \n  ");
        }

        StudentFrame whitespaceDescStudentFrame = new StudentFrame("testStudent", mockDialogManager);
        JTable whitespaceDescTopicTable = whitespaceDescStudentFrame.topicTable;
        whitespaceDescTopicTable.setRowSelectionInterval(0, 0);
        mockDialogManager.reset();

        // Act
        whitespaceDescStudentFrame.viewTopic();

        // Assert
        assertTrue(mockDialogManager.getLastMessage().trim().isEmpty(), "Should show whitespace-only description");
        assertEquals("Topic: T1", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.PLAIN_MESSAGE, mockDialogManager.getLastMessageType(), "Should show plain message type");
    }
}
