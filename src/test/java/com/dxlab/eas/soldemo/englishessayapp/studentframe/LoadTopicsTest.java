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
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadTopicsTest {

    @TempDir
    Path tempDir;

    private StudentFrame studentFrame;

    @BeforeEach
    public void setUp() {
        studentFrame = new StudentFrame("testStudent");
    }

    private void invokeLoadTopics() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        java.lang.reflect.Method method = StudentFrame.class.getDeclaredMethod("loadTopics");
        method.setAccessible(true);
        method.invoke(studentFrame);
    }

    @Test
    public void shouldLoadTopicsSuccessfullyWhenFileExists()
            throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(topicsFile))) {
            writer.write("T1 | Test Topic 1");
            writer.newLine();
            writer.write("T2 | Test Topic 2");
        }
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Act
        invokeLoadTopics();

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertEquals(2, model.getRowCount());
        assertEquals("T1", model.getValueAt(0, 0));
        assertEquals("Test Topic 1", model.getValueAt(0, 1));
    }

    @Test
    public void shouldNotLoadTopicsWhenFileIsEmpty()
            throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        File topicsFile = tempDir.resolve("topics.txt").toFile();
        topicsFile.createNewFile();
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Act
        invokeLoadTopics();

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertEquals(0, model.getRowCount());
    }

    @Test
    public void shouldLoadDefaultTopicsWhenFileDoesNotExist()
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        File topicsFile = tempDir.resolve("non_existent_topics.txt").toFile();
        EnglishEssayApp.setTopicsFile(topicsFile.getAbsolutePath());

        // Act
        invokeLoadTopics();

        // Assert
        DefaultTableModel model = studentFrame.topicTableModel;
        assertEquals(3, model.getRowCount());
        assertEquals("T1", model.getValueAt(0, 0));
    }
}
