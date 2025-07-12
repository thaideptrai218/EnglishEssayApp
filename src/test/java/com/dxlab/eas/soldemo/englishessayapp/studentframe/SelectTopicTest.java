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
import static org.junit.jupiter.api.Assertions.assertNull;

public class SelectTopicTest {

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
    @DisplayName("Should update selectedTopicId when the first topic is selected")
    public void testSelectTopic_FirstTopicSelected() {
        // Arrange
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        studentFrame.topicTableModel.addRow(new Object[] { "T2", "Test Topic 2" });
        studentFrame.topicTable.setRowSelectionInterval(0, 0);
        
        // Act
        studentFrame.selectTopic();
        
        // Assert
        assertEquals("T1", studentFrame.selectedTopicId);
    }

    @Test
    @DisplayName("Should update selectedTopicId when the last topic is selected")
    public void testSelectTopic_LastTopicSelected() {
        // Arrange
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        studentFrame.topicTableModel.addRow(new Object[] { "T2", "Test Topic 2" });
        studentFrame.topicTable.setRowSelectionInterval(1, 1);
        
        // Act
        studentFrame.selectTopic();
        
        // Assert
        assertEquals("T2", studentFrame.selectedTopicId);
    }

    @Test
    @DisplayName("Should not update selectedTopicId when no topic is selected")
    public void testSelectTopic_NoTopicSelected() {
        // Arrange
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        // No row is selected
        
        // Act
        studentFrame.selectTopic();
        
        // Assert
        assertNull(studentFrame.selectedTopicId);
    }

    @Test
    @DisplayName("Should handle selection when the topic table is empty")
    public void testSelectTopic_EmptyTable() {
        // Arrange: The table model is empty by default
        // Act
        studentFrame.selectTopic();
        
        // Assert
        assertNull(studentFrame.selectedTopicId, "selectedTopicId should be null when the table is empty.");
    }
}
