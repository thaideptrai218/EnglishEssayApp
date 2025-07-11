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

/**
 * Tests for the {@link StudentFrame#loadTopics()} method.
 */
public class LoadTopicsTest {

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
     * Invokes the private method {@link StudentFrame#loadTopics()} using reflection.
     *
     * @throws NoSuchMethodException if the method is not found
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws IllegalAccessException if this Method object is enforcing Java language access control and the underlying method is inaccessible
     */
    private void invokeLoadTopics() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        java.lang.reflect.Method method = StudentFrame.class.getDeclaredMethod("loadTopics");
        method.setAccessible(true);
        method.invoke(studentFrame);
    }

    /**
     * Tests that topics are loaded successfully when the topics file exists and contains valid data.
     *
     * @throws IOException if an I/O error occurs
     * @throws NoSuchMethodException if the method is not found
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws IllegalAccessException if this Method object is enforcing Java language access control and the underlying method is inaccessible
     */
    @Test
    public void shouldLoadTopicsSuccessfullyWhenFileExists() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

    /**
     * Tests that no topics are loaded when the topics file is empty.
     *
     * @throws IOException if an I/O error occurs
     * @throws NoSuchMethodException if the method is not found
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws IllegalAccessException if this Method object is enforcing Java language access control and the underlying method is inaccessible
     */
    @Test
    public void shouldNotLoadTopicsWhenFileIsEmpty() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

    /**
     * Tests that default topics are loaded when the topics file does not exist.
     *
     * @throws NoSuchMethodException if the method is not found
     * @throws InvocationTargetException if the underlying method throws an exception
     * @throws IllegalAccessException if this Method object is enforcing Java language access control and the underlying method is inaccessible
     */
    @Test
    public void shouldLoadDefaultTopicsWhenFileDoesNotExist() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
