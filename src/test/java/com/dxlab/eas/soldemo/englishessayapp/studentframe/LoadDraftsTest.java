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

/**
 * Tests for the {@link StudentFrame#loadDrafts()} method.
 */
public class LoadDraftsTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        studentFrame = new StudentFrame("testStudent");
    }

    /**
     * Tests that drafts are loaded successfully when the drafts file exists and
     * contains drafts for the student.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void shouldLoadDraftsSuccessfullyWhenFileExists() throws IOException {
        // Arrange
        File draftsFile = tempDir.resolve("drafts.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(draftsFile))) {
            writer.write("testStudent | draft1 | T1 | This is a draft.");
            writer.newLine();
            writer.write("otherStudent | draft2 | T2 | This is another draft.");
        }
        EnglishEssayApp.setDraftsFile(draftsFile.getAbsolutePath());

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(1, model.getRowCount());
        assertEquals("draft1", model.getValueAt(0, 0));
    }

    /**
     * Tests that no drafts are loaded when the drafts file exists but contains no
     * drafts for the student.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void shouldNotLoadDraftsWhenNoDraftsForStudent() throws IOException {
        // Arrange
        File draftsFile = tempDir.resolve("drafts.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(draftsFile))) {
            writer.write("otherStudent | draft2 | T2 | This is another draft.");
        }
        EnglishEssayApp.setDraftsFile(draftsFile.getAbsolutePath());

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(0, model.getRowCount());
    }

    /**
     * Tests that no drafts are loaded when the drafts file is empty.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void shouldNotLoadDraftsWhenFileIsEmpty() throws IOException {
        // Arrange
        File draftsFile = tempDir.resolve("drafts.txt").toFile();
        draftsFile.createNewFile();
        EnglishEssayApp.setDraftsFile(draftsFile.getAbsolutePath());

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(0, model.getRowCount());
    }

    /**
     * Tests that no drafts are loaded when the drafts file does not exist.
     */
    @Test
    public void shouldNotLoadDraftsWhenFileDoesNotExist() {
        // Arrange
        File draftsFile = tempDir.resolve("non_existent_drafts.txt").toFile();
        EnglishEssayApp.setDraftsFile(draftsFile.getAbsolutePath());

        // Act
        studentFrame.loadDrafts();

        // Assert
        DefaultTableModel model = studentFrame.draftTableModel;
        assertEquals(0, model.getRowCount());
    }
}
