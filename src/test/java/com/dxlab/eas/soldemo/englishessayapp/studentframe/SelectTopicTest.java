package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.swing.table.DefaultTableModel;

public class SelectTopicTest {

    private StudentFrame studentFrame;

    @BeforeEach
    public void setUp() {
        studentFrame = new StudentFrame("testStudent");
        studentFrame.topicTableModel = new DefaultTableModel(new String[] { "Topic ID", "Description" }, 0);
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        studentFrame.topicTableModel.addRow(new Object[] { "T2", "Test Topic 2" });
    }

    @Test
    public void testSelectTopic_TopicSelected() {
        studentFrame.topicTable.setRowSelectionInterval(0, 0);
        studentFrame.selectTopic();
        assertEquals("T1", studentFrame.selectedTopicId);
    }

    @Test
    public void testSelectTopic_NoTopicSelected() {
        studentFrame.selectTopic();
        assertNull(studentFrame.selectedTopicId);
    }
}
