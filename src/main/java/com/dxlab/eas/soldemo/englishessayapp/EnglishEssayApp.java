package com.dxlab.eas.soldemo.englishessayapp;

import javax.swing.SwingUtilities;

public class EnglishEssayApp {
    public static final String TOPICS_FILE = "topics.txt";
    public static final String DRAFTS_FILE = "drafts.txt";
    public static final String SUBMITTED_FILE = "submitted.txt";
    public static final String GRADED_FILE = "graded.txt";
    public static final String GRADING_HISTORY_FILE = "grading_history.txt";
    public static final String VIEW_LOG_FILE = "view_log.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
