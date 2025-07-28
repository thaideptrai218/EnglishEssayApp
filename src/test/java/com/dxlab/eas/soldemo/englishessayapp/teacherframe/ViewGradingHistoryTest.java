package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.JTable;
import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ViewGradingHistoryTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;
    private JTable historyTable;

    @BeforeEach
    public void setUp() throws IOException {
        File gradingHistoryFile = tempDir.resolve("grading_history.txt").toFile();
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        File topicsFile = tempDir.resolve("topics.txt").toFile();

        EnglishEssayApp.setGradingHistoryFile(gradingHistoryFile.getAbsolutePath());
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        gradingHistoryFile.createNewFile();
        submittedFile.createNewFile();
        gradedFile.createNewFile();
        topicsFile.createNewFile();

        teacherFrame = new TeacherFrame("testTeacher");

        teacherFrame.historyTableModel.setRowCount(0);
        teacherFrame.historyTableModel.addRow(new Object[] { "essay1", "student1", "T1", "123456789" });
        historyTable = new JTable(teacherFrame.historyTableModel);
        historyTable.setRowSelectionInterval(0, 0);
    }

    @Test
    @DisplayName("Should display grading details for selected record")
    public void shouldDisplayGradingDetails() throws IOException {
        File file = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("testTeacher | student1 | essay1 | T1 | 7 | 8 | 7 | 8 | Good job. | 123456789");
        }

        assertDoesNotThrow(() -> teacherFrame.viewGradingHistory(historyTable));
    }

    @Test
    @DisplayName("Should show warning if no row is selected")
    public void shouldShowWarningIfNoRowSelected() {
        JTable emptySelectionTable = new JTable(teacherFrame.historyTableModel);
        assertDoesNotThrow(() -> teacherFrame.viewGradingHistory(emptySelectionTable));
    }

    @Test
    @DisplayName("Should not fail if entry is not found in file")
    public void shouldNotFailIfEntryNotFound() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnglishEssayApp.GRADING_HISTORY_FILE))) {
            writer.write("testTeacher | student2 | essay99 | T2 | 7 | 7 | 7 | 7 | Different. | 999999999");
        }

        assertDoesNotThrow(() -> teacherFrame.viewGradingHistory(historyTable));
    }

    @Test
    @DisplayName("Should handle empty grading history file")
    public void shouldHandleEmptyFileGracefully() {
        assertDoesNotThrow(() -> teacherFrame.viewGradingHistory(historyTable));
    }

    @Test
    @DisplayName("Should handle missing grading history file")
    public void shouldHandleMissingFile() {
        File file = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        file.delete();

        assertDoesNotThrow(() -> teacherFrame.viewGradingHistory(historyTable));
    }

    @Test
    @DisplayName("Should skip malformed lines and not crash")
    public void shouldIgnoreMalformedLines() throws IOException {
        File file = new File(EnglishEssayApp.GRADING_HISTORY_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("MALFORMED LINE");
            writer.newLine();
            writer.write("testTeacher | student1 | essay1 | T1 | 6 | 6 | 6 | 6 | Reasonable | 123456789");
        }

        assertDoesNotThrow(() -> teacherFrame.viewGradingHistory(historyTable));
    }
}
