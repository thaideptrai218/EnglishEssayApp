package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
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

public class LoadSubmittedTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

    @BeforeEach
    public void setUp() {
        studentFrame = new StudentFrame("testStudent");
    }

    @Test
    public void shouldLoadSubmittedSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("testStudent | essay1 | T1 | This is an essay.");
            writer.newLine();
            writer.write("otherStudent | essay2 | T2 | This is another essay.");
        }
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    public void shouldNotLoadSubmittedWhenNoSubmittedForStudent() throws IOException {
        // Arrange
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("otherStudent | essay2 | T2 | This is another essay.");
        }
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    public void shouldNotLoadSubmittedWhenFileIsEmpty() throws IOException {
        // Arrange
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        submittedFile.createNewFile();
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    public void shouldNotLoadSubmittedWhenFileDoesNotExist() {
        // Arrange
        File submittedFile = tempDir.resolve("non_existent_submitted.txt").toFile();
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount());
    }
}
