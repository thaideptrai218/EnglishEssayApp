package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetGradeSummaryTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

    @BeforeEach
    public void setUp() {
        studentFrame = new StudentFrame("testStudent");
    }

    @Test
    public void shouldReturnGradeSummaryWhenGradeExists() throws IOException {
        // Arrange
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
        }
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Task: 8, Coherence: 7, Lexical: 8, Grammar: 7", summary);
    }

    @Test
    public void shouldReturnNotGradedWhenGradeDoesNotExist() throws IOException {
        // Arrange
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay2 | T1 | 8 | 7 | 8 | 7 | Good job.");
        }
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary);
    }

    @Test
    public void shouldReturnNotGradedWhenFileIsEmpty() throws IOException {
        // Arrange
        File gradedFile = tempDir.resolve("graded.txt").toFile();
        gradedFile.createNewFile();
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary);
    }

    @Test
    public void shouldReturnNotGradedWhenFileDoesNotExist() {
        // Arrange
        File gradedFile = tempDir.resolve("non_existent_graded.txt").toFile();
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary);
    }
}
