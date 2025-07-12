package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadGradedEssaysTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;

    @BeforeEach
    public void setUp() throws IOException {
        // Prepare the environment before constructing the TeacherFrame
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        File gradingHistoryFile = tempDir.resolve("grading_history.txt").toFile();
        File topicsFile = tempDir.resolve("topics.txt").toFile();

        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());
        EnglishEssayApp.setGradingHistoryFile(gradingHistoryFile.getAbsolutePath());
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Create empty files to prevent constructor errors
        submittedFile.createNewFile();
        gradedFile.createNewFile();
        gradingHistoryFile.createNewFile();
        topicsFile.createNewFile();

        teacherFrame = new TeacherFrame("testTeacher");
    }

    @Test
    @DisplayName("Should load all graded essays successfully")
    public void shouldLoadGradedEssaysSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done.");
        }

        // Act
        teacherFrame.loadGradedEssays();

        // Assert
        DefaultTableModel model = teacherFrame.gradedTableModel;
        assertEquals(2, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
        assertEquals("student2", model.getValueAt(1, 1));
    }

    @Test
    @DisplayName("Should not load graded essays when file is empty")
    public void shouldNotLoadGradedEssaysWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        teacherFrame.loadGradedEssays();

        // Assert
        DefaultTableModel model = teacherFrame.gradedTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load graded essays when file does not exist")
    public void shouldNotLoadGradedEssaysWhenFileDoesNotExist() {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        gradedFile.delete();

        // Act
        teacherFrame.loadGradedEssays();

        // Assert
        DefaultTableModel model = teacherFrame.gradedTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should ignore malformed lines and load valid graded essays")
    public void shouldIgnoreMalformedLinesWhenLoadingGradedEssays() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
            writer.newLine();
            writer.write("malformed-line");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done.");
        }

        // Act
        teacherFrame.loadGradedEssays();

        // Assert
        DefaultTableModel model = teacherFrame.gradedTableModel;
        assertEquals(2, model.getRowCount());
    }
}
