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
import static org.junit.jupiter.api.Assertions.assertNull;

public class StudentFrame_selectTopicTest {

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
    @DisplayName("TC_ST_01: Normal Path - First Topic Selected")
    public void shouldSelectFirstTopicSuccessfully() {
        // Arrange
        topicTable.setRowSelectionInterval(0, 0);
        mockDialogManager.reset();

        // Act
        studentFrame.selectTopic();

        // Assert
        assertEquals("T1", studentFrame.selectedTopicId, "selectedTopicId should be set to T1");
        assertEquals("Selected topic: Test Topic 1", mockDialogManager.getLastMessage(), "Should show correct selection message");
        assertEquals("Topic Selected", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.INFORMATION_MESSAGE, mockDialogManager.getLastMessageType(), "Should show information message type");
    }

    @Test
    @DisplayName("TC_ST_02: Boundary - Last Topic Selected")
    public void shouldSelectLastTopicSuccessfully() {
        // Arrange
        topicTable.setRowSelectionInterval(2, 2); // Select last row (T3)
        mockDialogManager.reset();

        // Act
        studentFrame.selectTopic();

        // Assert
        assertEquals("T3", studentFrame.selectedTopicId, "selectedTopicId should be set to T3");
        assertEquals("Selected topic: Test Topic 3", mockDialogManager.getLastMessage(), "Should show correct selection message");
        assertEquals("Topic Selected", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.INFORMATION_MESSAGE, mockDialogManager.getLastMessageType(), "Should show information message type");
    }

    @Test
    @DisplayName("TC_ST_03: Abnormal - No Topic Selected")
    public void shouldHandleNoTopicSelected() {
        // Arrange
        topicTable.clearSelection(); // Clear any selection
        mockDialogManager.reset();

        // Act
        studentFrame.selectTopic();

        // Assert
        assertNull(studentFrame.selectedTopicId, "selectedTopicId should remain null when no topic is selected");
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage(), "Should show error message");
        assertEquals("Warning", mockDialogManager.getLastTitle(), "Should show warning dialog title");
        assertEquals(JOptionPane.WARNING_MESSAGE, mockDialogManager.getLastMessageType(), "Should show warning message type");
    }

    @Test
    @DisplayName("TC_ST_04: Boundary - Empty Table")
    public void shouldHandleEmptyTable() throws IOException {
        // Arrange - Create new StudentFrame with empty topics file
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        topicsFile.delete();
        topicsFile.createNewFile(); // Create empty file

        StudentFrame emptyStudentFrame = new StudentFrame("testStudent", mockDialogManager);
        JTable emptyTopicTable = emptyStudentFrame.topicTable;
        mockDialogManager.reset();

        // Act
        emptyStudentFrame.selectTopic();

        // Assert
        assertNull(emptyStudentFrame.selectedTopicId, "selectedTopicId should remain null when table is empty");
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage(), "Should show error message");
        assertEquals("Warning", mockDialogManager.getLastTitle(), "Should show warning dialog title");
        assertEquals(JOptionPane.WARNING_MESSAGE, mockDialogManager.getLastMessageType(), "Should show warning message type");
    }

    @Test
    @DisplayName("Should select middle topic successfully")
    public void shouldSelectMiddleTopicSuccessfully() {
        // Arrange
        topicTable.setRowSelectionInterval(1, 1); // Select middle row (T2)
        mockDialogManager.reset();

        // Act
        studentFrame.selectTopic();

        // Assert
        assertEquals("T2", studentFrame.selectedTopicId, "selectedTopicId should be set to T2");
        assertEquals("Selected topic: Test Topic 2", mockDialogManager.getLastMessage(), "Should show correct selection message");
        assertEquals("Topic Selected", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.INFORMATION_MESSAGE, mockDialogManager.getLastMessageType(), "Should show information message type");
    }

    @Test
    @DisplayName("Should handle multiple row selection")
    public void shouldHandleMultipleRowSelection() {
        // Arrange
        topicTable.setRowSelectionInterval(0, 2); // Select all rows
        mockDialogManager.reset();

        // Act
        studentFrame.selectTopic();

        // Assert
        assertEquals("T1", studentFrame.selectedTopicId, "selectedTopicId should be set to first selected row (T1)");
        assertEquals("Selected topic: Test Topic 1", mockDialogManager.getLastMessage(), "Should show correct selection message");
        assertEquals("Topic Selected", mockDialogManager.getLastTitle(), "Should show correct dialog title");
        assertEquals(JOptionPane.INFORMATION_MESSAGE, mockDialogManager.getLastMessageType(), "Should show information message type");
    }

    @Test
    @DisplayName("Should update selectedTopicId when selecting different topics")
    public void shouldUpdateSelectedTopicIdWhenSelectingDifferentTopics() {
        // Arrange & Act - Select first topic
        topicTable.setRowSelectionInterval(0, 0);
        mockDialogManager.reset();
        studentFrame.selectTopic();

        // Assert first selection
        assertEquals("T1", studentFrame.selectedTopicId, "First selection should be T1");

        // Arrange & Act - Select second topic
        topicTable.setRowSelectionInterval(1, 1);
        mockDialogManager.reset();
        studentFrame.selectTopic();

        // Assert second selection
        assertEquals("T2", studentFrame.selectedTopicId, "Second selection should be T2");
        assertEquals("Selected topic: Test Topic 2", mockDialogManager.getLastMessage(), "Should show correct selection message for second topic");
    }

    @Test
    @DisplayName("Should handle invalid row selection")
    public void shouldHandleInvalidRowSelection() {
        // Arrange
        topicTable.clearSelection(); // No row is selected
        mockDialogManager.reset();

        // Act
        studentFrame.selectTopic();

        // Assert
        assertNull(studentFrame.selectedTopicId, "selectedTopicId should remain null for invalid selection");
        assertEquals("Please select a topic!", mockDialogManager.getLastMessage(), "Should show error message");
        assertEquals("Warning", mockDialogManager.getLastTitle(), "Should show warning dialog title");
        assertEquals(JOptionPane.WARNING_MESSAGE, mockDialogManager.getLastMessageType(), "Should show warning message type");
    }
}
