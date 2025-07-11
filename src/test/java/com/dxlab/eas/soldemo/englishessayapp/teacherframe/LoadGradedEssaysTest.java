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

public class LoadGradedEssaysTest {

    @TempDir
    Path tempDir;

    private TeacherFrame teacherFrame;

    @BeforeEach
    public void setUp() {
        teacherFrame = new TeacherFrame("testTeacher");
    }

    @Test
    public void shouldLoadGradedEssaysSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | 7 | 8 | 7 | 8 | Well done.");
        }
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Act
        teacherFrame.loadGradedEssays();

        // Assert
        DefaultTableModel model = teacherFrame.gradedTableModel;
        assertEquals(2, model.getRowCount());
        assertEquals("essay1", model.getValueAt(0, 0));
    }

    @Test
    public void shouldNotLoadGradedEssaysWhenFileIsEmpty() throws IOException {
        // Arrange
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        gradedFile.createNewFile();
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Act
        teacherFrame.loadGradedEssays();

        // Assert
        DefaultTableModel model = teacherFrame.gradedTableModel;
        assertEquals(0, model.getRowCount());
    }

}