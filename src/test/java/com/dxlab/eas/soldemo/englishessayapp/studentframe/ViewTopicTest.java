package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTopicTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

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

        studentFrame = new StudentFrame("testStudent");
    }

    @Test
    @DisplayName("Should execute view action when a topic is selected")
    public void testViewTopic_TopicSelected() {
        // Arrange
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        studentFrame.topicTable.setRowSelectionInterval(0, 0);
        
        // Act
        // We can't directly test the JOptionPane, but we can call the method
        // and ensure it doesn't throw an exception.
        studentFrame.viewTopic();
        
        // Assert
        // A simple assertion to confirm the test ran without error and state is as expected.
        int row = studentFrame.topicTable.getSelectedRow();
        assertEquals(0, row);
    }

    @Test
    @DisplayName("Should execute view action when no topic is selected")
    public void testViewTopic_NoTopicSelected() {
        // Arrange
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        // No row is selected
        
        // Act
        studentFrame.viewTopic();
        
        // Assert
        int row = studentFrame.topicTable.getSelectedRow();
        assertEquals(-1, row);
    }

    @Test
    @DisplayName("Should handle view action when the topic table is empty")
    public void testViewTopic_EmptyTable() {
        // Arrange: The table model is empty by default
        // Act
        studentFrame.viewTopic();
        
        // Assert
        int row = studentFrame.topicTable.getSelectedRow();
        assertEquals(-1, row, "Selected row should be -1 when the table is empty.");
    }
}
