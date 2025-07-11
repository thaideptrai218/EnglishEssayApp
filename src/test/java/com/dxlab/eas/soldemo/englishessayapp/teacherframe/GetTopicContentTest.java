package com.dxlab.eas.soldemo.englishessayapp.teacherframe;

import com.dxlab.eas.soldemo.englishessayapp.EnglishEssayApp;
import com.dxlab.eas.soldemo.englishessayapp.TeacherFrame;

import org.junit.jupiter.api.BeforeEach;
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
    public void setUp() {
        teacherFrame = new TeacherFrame("testTeacher");
    }

    @Test
    public void shouldReturnTopicContentWhenTopicExists() throws IOException {
        // Arrange
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
        }
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Test Topic 1", content);
    }

    @Test
    public void shouldReturnUnknownTopicWhenTopicDoesNotExist() throws IOException {
        // Arrange
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T2 | Test Topic 2");
        }
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    public void shouldReturnUnknownTopicWhenFileIsEmpty() throws IOException {
        // Arrange
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        topicsFile.createNewFile();
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }

    @Test
    public void shouldReturnUnknownTopicWhenFileDoesNotExist() {
        // Arrange
        File topicsFile = tempDir.resolve("non_existent_topics.txt").toFile();
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Act
        String content = teacherFrame.getTopicContent("T1");

        // Assert
        assertEquals("Unknown topic", content);
    }
}
