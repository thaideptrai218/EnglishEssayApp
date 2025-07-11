package com.dxlab.eas.soldemo.englishessayapp;

import javax.swing.SwingUtilities;

public class EnglishEssayApp {
    public static String TOPICS_FILE = "topics.txt";
    public static String DRAFTS_FILE = "drafts.txt";
    public static String SUBMITTED_FILE = "submitted.txt";
    public static String GRADED_FILE = "graded.txt";
    public static String GRADING_HISTORY_FILE = "grading_history.txt";
    public static final String VIEW_LOG_FILE = "view_log.txt";

    public static void setTopicsFile(String path) {
        TOPICS_FILE = path;
    }

    public static void setDraftsFile(String path) {
        DRAFTS_FILE = path;
    }

    public static void setSubmittedFile(String path) {
        SUBMITTED_FILE = path;
    }

    public static void setGradedFile(String path) {
        GRADED_FILE = path;
    }

    public static void setGradingHistoryFile(String path) {
        GRADING_HISTORY_FILE = path;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
