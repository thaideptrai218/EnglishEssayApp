package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;
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

public class GetTopicContentTest {

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
    @DisplayName("Should return topic content when topic exists")
    public void shouldReturnTopicContentWhenTopicExists() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
            writer.newLine();
            writer.write("T2 | Test Topic 2");
        }

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Test Topic 1", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' when topic does not exist")
    public void shouldReturnUnknownTopicWhenTopicDoesNotExist() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T2 | Test Topic 2");
        }

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' when file is empty")
    public void shouldReturnUnknownTopicWhenFileIsEmpty() {
        // Arrange: File is created empty in setUp
        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' when file does not exist")
    public void shouldReturnUnknownTopicWhenFileDoesNotExist() {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        topicsFile.delete();

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    @DisplayName("Should return 'Unknown topic' for a malformed line")
    public void shouldReturnUnknownTopicForMalformedLine() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1-Test Topic 1"); // Malformed line
        }

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }
    
    @Test
    @DisplayName("Should retrieve the correct topic when it is the last entry")
    public void shouldRetrieveLastTopicCorrectly() throws IOException {
        // Arrange
        File topicsFile = new File(EnglishEssayApp.TOPICS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
            writer.newLine();
            writer.write("T2 | Test Topic 2");
        }

        // Act
        String content = teacherFrame.getTopicContent("T2");

        // Assert
        assertEquals("Test Topic 2", content);
    }
}
