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

public class LoadGradingHistoryTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;
    private final String currentTeacherId = "testTeacher";

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

        teacherFrame = new TeacherFrame(currentTeacherId);
    }

    @Test
    @DisplayName("Should load grading history for the current teacher")
    public void shouldLoadGradingHistorySuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File gradingHistoryFile = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradingHistoryFile))) {
            writer.write(currentTeacherId + " | student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job. | 123456789");
            writer.newLine();
            writer.write("otherTeacher | student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done. | 987654321");
        }

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    @DisplayName("Should not load history when no entries exist for the current teacher")
    public void shouldNotLoadGradingHistoryWhenNoHistoryForTeacher() throws IOException {
        // Arrange
        File gradingHistoryFile = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradingHistoryFile))) {
            writer.write("otherTeacher | student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done. | 987654321");
        }

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load grading history when file is empty")
    public void shouldNotLoadGradingHistoryWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load grading history when file does not exist")
    public void shouldNotLoadGradingHistoryWhenFileDoesNotExist() {
        // Arrange
        File gradingHistoryFile = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        gradingHistoryFile.delete();

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should ignore malformed lines and load valid history entries")
    public void shouldIgnoreMalformedLinesWhenLoadingHistory() throws IOException {
        // Arrange
        File gradingHistoryFile = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradingHistoryFile))) {
            writer.write(currentTeacherId + " | student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job. | 123456789");
            writer.newLine();
            writer.write("malformed-line");
            writer.newLine();
            writer.write(currentTeacherId + " | student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done. | 987654321");
        }

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(2, model.getRowCount());
    }
}
