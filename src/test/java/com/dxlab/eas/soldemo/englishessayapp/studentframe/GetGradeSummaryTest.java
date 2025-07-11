package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suite for the getGradeSummary(String essayId) method in StudentFrame.
 * This class verifies that the grade summary is correctly retrieved from the graded.txt file.
 */
public class GetGradeSummaryTest {

    /**
     * @TempDir creates a temporary directory for test files, which is cleaned up automatically.
     * This ensures our tests don't interfere with real data or leave artifacts.
     */
    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

    @BeforeEach
    public void setUp() throws IOException {
        // Define paths for all required files within the temporary directory.
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        File draftsFile = tempDir.resolve("drafts.txt").toFile();
        File submittedFile = tempDir.resolve("submitted.txt").toFile();
        File gradedFile = tempDir.resolve("graded.txt").toFile();

        // Set the static file paths in the main app class to point to our temp files.
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());
        EnglishEssayApp.setDraftsFile(draftsFile.getAbsolutePath());
        EnglishEssayApp.setSubmittedFile(submittedFile.getAbsolutePath());
        EnglishEssayApp.setGradedFile(gradedFile.getAbsolutePath());

        // Create empty files to ensure the StudentFrame constructor doesn't throw an error.
        topicsFile.createNewFile();
        draftsFile.createNewFile();
        submittedFile.createNewFile();
        gradedFile.createNewFile();

        // Now that the environment is prepared, it's safe to create the StudentFrame.
        studentFrame = new StudentFrame("testStudent");
    }

    @Test
    @DisplayName("TC_GGS_01: Should return the correct grade summary when a grade exists for the essay.")
    public void testGetGradeSummary_GradeExists_ReturnsSummary() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            // The file contains grades for two different essays.
            writer.write("student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | 5 | 6 | 5 | 6 | Needs improvement.");
            writer.newLine();
        }

        // Act
        // We request the summary for "essay1".
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        // The summary string should be formatted correctly based on the file content.
        assertEquals("Task: 8, Coherence: 7, Lexical: 8, Grammar: 7", summary,
                "The grade summary should be correctly formatted.");
    }

    @Test
    @DisplayName("TC_GGS_02: Should return 'Not graded' when the essay ID does not exist in the file.")
    public void testGetGradeSummary_GradeDoesNotExist_ReturnsNotGraded() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay2 | T1 | 8 | 7 | 8 | 7 | Good job.");
            writer.newLine();
        }

        // Act
        // We request a summary for "essay1", which is not in the file.
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary, "Should return 'Not graded' for a non-existent essay ID.");
    }

    @Test
    @DisplayName("TC_GGS_03: Should return 'Not graded' when the graded file is empty.")
    public void testGetGradeSummary_FileIsEmpty_ReturnsNotGraded() {
        // Arrange: The file is already created empty in setUp().
        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        assertEquals("Not graded", summary, "Should return 'Not graded' when the file is empty.");
    }

    @Test
    @DisplayName("TC_GGS_04: Should return 'Not graded' when the graded file does not exist.")
    public void testGetGradeSummary_FileDoesNotExist_ReturnsNotGraded() {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        gradedFile.delete();

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        // The method should handle the missing file gracefully.
        assertEquals("Not graded", summary, "Should return 'Not graded' when the file does not exist.");
    }

    @Test
    @DisplayName("TC_GGS_05: Should return 'Not graded' for a line with insufficient parts.")
    public void testGetGradeSummary_MalformedData_ReturnsNotGraded() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            // This line matches the essayId but is malformed (missing grade parts).
            writer.write("student1 | essay1 | T1 | 8 | 7");
            writer.newLine();
        }

        // Act
        String summary = studentFrame.getGradeSummary("essay1");

        // Assert
        // The method should not throw an error and should return "Not graded".
        assertEquals("Not graded", summary, "Should return 'Not graded' if the line is malformed.");
    }

    @Test
    @DisplayName("TC_GGS_06: Should retrieve the correct grade when it is the last entry in the file.")
    public void testGetGradeSummary_LastEntry_ReturnsSummary() throws IOException {
        // Arrange
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write("student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.");
            writer.newLine();
            writer.write("student2 | essay2 | T2 | 9 | 9 | 9 | 9 | Excellent work."); // The target entry
            writer.newLine();
        }

        // Act
        String summary = studentFrame.getGradeSummary("essay2");

        // Assert
        assertEquals("Task: 9, Coherence: 9, Lexical: 9, Grammar: 9", summary,
                "Should correctly retrieve the last grade entry in the file.");
    }
}
