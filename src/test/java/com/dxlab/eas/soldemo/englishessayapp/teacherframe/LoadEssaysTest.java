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

public class LoadEssaysTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;

    @BeforeEach
    public void setUp() {
        teacherFrame = new TeacherFrame("testTeacher");
    }

    @Test
    public void shouldLoadEssaysSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | This is an essay.");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | This is another essay.");
        }
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(2, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    public void shouldLoadEssaysWithStudentFilter() throws IOException {
        // Arrange
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | This is an essay.");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | This is another essay.");
        }
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        teacherFrame.studentFilterField.setText("student1");

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    public void shouldLoadEssaysWithTopicFilter() throws IOException {
        // Arrange
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("student1 | essay1 | T1 | This is an essay.");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | This is another essay.");
        }
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        teacherFrame.topicFilterField.setText("T1");

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    public void shouldNotLoadEssaysWhenFileIsEmpty() throws IOException {
        // Arrange
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        submittedFile.createNewFile();
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    public void shouldNotLoadEssaysWhenFileDoesNotExist() {
        // Arrange
        File submittedFile = tempDir.resolve("non_existent_submitted.txt").toFile();
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());

        // Act
        teacherFrame.loadEssays();

        // Assert
        DefaultTableModel model = teacherFrame.essayTableModel;
        assertEquals(0, model.getRowCount());
    }
}
