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

public class LoadEssaysTest {

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
    @DisplayName("Should load all essays when no filters are applied")
    public void shouldLoadEssaysSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | Content 1");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | Content 2");
        }

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(2, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
        assertEquals("essay2", model.getValueAt(1, 0));
    }

    @Test
    @DisplayName("Should load only essays matching the student ID filter")
    public void shouldLoadEssaysWithStudentFilter() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | Content 1");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | Content 2");
        }
        teacherFrame.studentFilterField.setText("student1");

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    @DisplayName("Should load only essays matching the topic ID filter")
    public void shouldLoadEssaysWithTopicFilter() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | Content 1");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | Content 2");
        }
        teacherFrame.topicFilterField.setText("T2");

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay2", model.getValueAt(0, 0));
    }

    @Test
    @DisplayName("Should load only essays matching both student and topic ID filters")
    public void shouldLoadEssaysWithBothFilters() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | Content 1");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | Content 2");
            writer.newLine();
            writer.write("student1 | essay3 | T2 | Content 3");
        }
        teacherFrame.studentFilterField.setText("student1");
        teacherFrame.topicFilterField.setText("T2");

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay3", model.getValueAt(0, 0));
    }
    
    @Test
    @DisplayName("Should load no essays when filters match nothing")
    public void shouldLoadNoEssaysWhenFiltersMatchNothing() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | Content 1");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | Content 2");
        }
        teacherFrame.studentFilterField.setText("student3");

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load essays when file is empty")
    public void shouldNotLoadEssaysWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should not load essays when file does not exist")
    public void shouldNotLoadEssaysWhenFileDoesNotExist() {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        submittedFile.delete();

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    @DisplayName("Should ignore malformed lines and load valid ones")
    public void shouldIgnoreMalformedLinesWhenLoadingEssays() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | Content 1");
            writer.newLine();
            writer.write("malformed-line");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | Content 2");
        }

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(2, model.getRowCount());
    }
}
