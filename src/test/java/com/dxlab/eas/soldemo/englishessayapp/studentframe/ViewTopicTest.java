package com.dxlab.eas.soldemo.englishessayapp.studentframe;

import com.dxlab.eas.soldemo.englishessayapp.StudentFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.table.DefaultTableModel;

public class ViewTopicTest {

    private StudentFrame studentFrame;

    @BeforeEach
    public void setUp() {
        studentFrame = new StudentFrame("testStudent");
        studentFrame.topicTableModel = new DefaultTableModel(new String[] { "Topic ID", "Description" }, 0);
        studentFrame.topicTableModel.addRow(new Object[] { "T1", "Test Topic 1" });
        studentFrame.topicTableModel.addRow(new Object[] { "T2", "Test Topic 2" });
    }

    @Test
    public void testViewTopic_TopicSelected() {
        studentFrame.topicTable.setRowSelectionInterval(0, 0);
        studentFrame.viewTopic();
        int row = studentFrame.topicTable.getSelectedRow();
        String description = (String) studentFrame.topicTableModel.getValueAt(row, 1);
        assertEquals("Test Topic 1", description);
    }

    @Test
    public void testViewTopic_NoTopicSelected() {
        studentFrame.viewTopic();
        int row = studentFrame.topicTable.getSelectedRow();
        assertTrue(row == -1);
    }
}
