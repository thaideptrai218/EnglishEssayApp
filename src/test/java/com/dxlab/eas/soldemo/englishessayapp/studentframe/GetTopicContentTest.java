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
 * Test suite for the getTopicContent(String topicId) method in StudentFrame.
 * This class ensures that topic descriptions are correctly retrieved from the topics.txt file.
 */
public class GetTopicContentTest {

    /**
     * @TempDir provides a temporary directory that is created before the tests
     * and cleaned up after, ensuring a clean testing environment.
     */
    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

    /**
     * This method runs before each test. It sets up the test environment.
     * Crucially, it redirects the application's static file paths to our temporary
     * directory BEFORE the StudentFrame is constructed. This prevents the constructor
     * from failing when it tries to load data from default file locations.
     * @throws IOException if an I/O error occurs
     */
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
    @DisplayName("TC_GTC_01: Should return the topic content when the topic ID exists.")
    public void testGetTopicContent_TopicExists_ReturnsContent() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | This is the first test topic.");
            writer.newLine();
            writer.write("T2 | This is the second test topic.");
            writer.newLine();
        }

        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("This is the first test topic.", content, "The content should match the entry in the file.");
    }

    @Test
    @DisplayName("TC_GTC_02: Should return 'Unknown topic' when the topic ID does not exist.")
    public void testGetTopicContent_TopicDoesNotExist_ReturnsUnknown() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T2 | This is another topic.");
            writer.newLine();
        }

        // Act
        String content = studentFrame.getTopicContent("T1"); // "T1" is not in the file.

        // Assert
        assertEquals("Unknown topic", content, "Should return 'Unknown topic' for a non-existent topic ID.");
    }

    @Test
    @DisplayName("TC_GTC_03: Should return 'Unknown topic' when the topics file is empty.")
    public void testGetTopicContent_FileIsEmpty_ReturnsUnknown() {
        // Arrange: The file is already created empty in setUp().
        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content, "Should return 'Unknown topic' when the file is empty.");
    }

    @Test
    @DisplayName("TC_GTC_04: Should return 'Unknown topic' when the topics file does not exist.")
    public void testGetTopicContent_FileDoesNotExist_ReturnsUnknown() {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        topicsFile.delete(); // Ensure the file does not exist.

        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content, "Should return 'Unknown topic' when the file does not exist.");
    }

    @Test
    @DisplayName("TC_GTC_05: Should return 'Unknown topic' for a malformed line in the topics file.")
    public void testGetTopicContent_MalformedData_ReturnsUnknown() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            // This line is malformed because it lacks the " | " separator.
            writer.write("T1 This is a malformed topic.");
            writer.newLine();
        }

        // Act
        String content = studentFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content, "Should return 'Unknown topic' for a malformed line.");
    }

    @Test
    @DisplayName("TC_GTC_06: Should retrieve the correct topic when it is the last entry in the file.")
    public void testGetTopicContent_LastEntry_ReturnsContent() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | First topic.");
            writer.newLine();
            writer.write("T2 | The very last topic."); // The target entry
            writer.newLine();
        }

        // Act
        String content = studentFrame.getTopicContent("T2");

        // Assert
        assertEquals("The very last topic.", content, "Should correctly retrieve the last topic in the file.");
    }
}
