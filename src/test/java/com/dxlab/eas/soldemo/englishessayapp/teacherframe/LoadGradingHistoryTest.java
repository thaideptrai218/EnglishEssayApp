package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setUp() {
        teacherFrame = new TeacherFrame("testTeacher");
    }

    @Test
    public void shouldLoadGradingHistorySuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File gradingHistoryFile = tempDir.resolve("grading_history.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradingHistoryFile))) {
            writer.write("testTeacher | student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job. | 123456789");
            writer.newLine();
            writer.write("otherTeacher | student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done. | 987654321");
        }
        EnglishEssayApp.setGradingHistoryFile(gradingHistoryFile.getAbsolutePath());

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    public void shouldNotLoadGradingHistoryWhenNoHistoryForTeacher() throws IOException {
        // Arrange
        File gradingHistoryFile = tempDir.resolve("grading_history.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradingHistoryFile))) {
            writer.write("otherTeacher | student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done. | 987654321");
        }
        EnglishEssayApp.setGradingHistoryFile(gradingHistoryFile.getAbsolutePath());

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    public void shouldNotLoadGradingHistoryWhenFileIsEmpty() throws IOException {
        // Arrange
        File gradingHistoryFile = tempDir.resolve("grading_history.txt").toFile();
        gradingHistoryFile.createNewFile();
        EnglishEssayApp.setGradingHistoryFile(gradingHistoryFile.getAbsolutePath());

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    public void shouldNotLoadGradingHistoryWhenFileDoesNotExist() {
        // Arrange
        File gradingHistoryFile = tempDir.resolve("non_existent_grading_history.txt").toFile();
        EnglishEssayApp.setGradingHistoryFile(gradingHistoryFile.getAbsolutePath());

        // Act
        teacherFrame.loadGradingHistory();

        // Assert
        DefaultTableModel model = teacherFrame.historyTableModel;
        assertEquals(0, model.getRowCount());
    }
}
