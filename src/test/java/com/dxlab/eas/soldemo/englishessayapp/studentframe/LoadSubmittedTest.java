package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
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

/**
 * Test suite for the loadSubmitted() method in the StudentFrame class.
 * This class uses a temporary directory to create mock data files for testing,
 * ensuring that the tests are isolated and do not affect the actual application data.
 */
public class LoadSubmittedTest {

    /**
     * @TempDir is a JUnit 5 annotation that creates a temporary directory for the tests.
     * This directory is automatically created before the tests run and deleted afterwards.
     * It's useful for creating temporary files needed for testing file I/O operations.
     */
    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;
    private final String currentStudentId = "student123";

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
        studentFrame = new StudentFrame(currentStudentId);
    }

    @Test
    @DisplayName("TC_LS_01: Should load submitted essays correctly when the file contains entries for the student.")
    public void testLoadSubmitted_FileContainsStudentEssays_LoadsCorrectly() throws IOException {
        // Arrange: Set up the test conditions.
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        File gradedFile = new File(EnglishEssayApp.GRADED_FILE);

        // Write data to the files. One essay is graded, one is not.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write(currentStudentId + " | essay001 | T1 | This is the first essay content.");
            writer.newLine();
            writer.write("anotherStudent | essay002 | T2 | This is another student's essay.");
            writer.newLine();
            writer.write(currentStudentId + " | essay003 | T3 | This is a second essay for the student.");
            writer.newLine();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gradedFile))) {
            writer.write(currentStudentId + " | essay001 | T1 | 8 | 7 | 8 | 7 | Good job.");
            writer.newLine();
        }

        // Act: Call the method being tested.
        studentFrame.loadSubmitted();

        // Assert: Verify the outcome.
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(2, model.getRowCount(), "The table should contain two submitted essays for the current student.");
        
        assertEquals("essay001", model.getValueAt(0, 0), "The first essay ID should be 'essay001'.");
        assertEquals("T1", model.getValueAt(0, 1), "The first topic ID should be 'T1'.");
        assertEquals("Task: 8, Coherence: 7, Lexical: 8, Grammar: 7", model.getValueAt(0, 2), "The grade summary for the first essay should be correct.");

        assertEquals("essay003", model.getValueAt(1, 0), "The second essay ID should be 'essay003'.");
        assertEquals("T3", model.getValueAt(1, 1), "The second topic ID should be 'T3'.");
        assertEquals("Not graded", model.getValueAt(1, 2), "The grade summary for the second essay should be 'Not graded'.");
    }

    @Test
    @DisplayName("TC_LS_02: Should not load any essays when the file contains no entries for the student.")
    public void testLoadSubmitted_FileExcludesStudent_LoadsNothing() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write("anotherStudent | essay002 | T2 | This is another student's essay.");
            writer.newLine();
            writer.write("student999 | essay003 | T3 | Yet another essay.");
            writer.newLine();
        }

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount(), "The table should be empty as there are no essays for this student.");
    }

    @Test
    @DisplayName("TC_LS_03: Should handle an empty file gracefully.")
    public void testLoadSubmitted_FileIsEmpty_LoadsNothing() {
        // Arrange: The file is already created empty in setUp().
        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount(), "The table should be empty when the file is empty.");
    }

    @Test
    @DisplayName("TC_LS_04: Should handle a non-existent file gracefully.")
    public void testLoadSubmitted_FileDoesNotExist_LoadsNothing() {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        submittedFile.delete();

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(0, model.getRowCount(), "The table should be empty when the file does not exist.");
    }

    @Test
    @DisplayName("TC_LS_05: Should ignore malformed lines and load valid ones.")
    public void testLoadSubmitted_MalformedData_IgnoresMalformedLine() throws IOException {
        // Arrange
        File submittedFile = new File(EnglishEssayApp.SUBMITTED_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(submittedFile))) {
            writer.write(currentStudentId + " | essay001 | T1 | This is a valid essay.");
            writer.newLine();
            writer.write(currentStudentId + "malformed line without separators"); // Malformed line
            writer.newLine();
            writer.write(currentStudentId + " | essay002 | T2 | This is another valid essay.");
            writer.newLine();
        }

        // Act
        studentFrame.loadSubmitted();

        // Assert
        DefaultTableModel model = studentFrame.submittedTableModel;
        assertEquals(2, model.getRowCount(), "Should load 2 valid essays and ignore the malformed one.");
        assertEquals("essay001", model.getValueAt(0, 0));
        assertEquals("essay002", model.getValueAt(1, 0));
    }
}
