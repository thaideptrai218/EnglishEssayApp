package com.dxlab.eas.soldemo.englishessayapp;

/**
 * An interface for managing UI dialogs. This allows for decoupling the dialog
 * logic from the application frames, making the frames easier to test.
 */
public interface DialogManager {

    /**
     * Shows an informational message dialog.
     *
     * @param message     the message to display.
     * @param title       the title of the dialog window.
     * @param messageType the type of message (e.g., JOptionPane.INFORMATION_MESSAGE).
     */
    void showMessage(String message, String title, int messageType);

    /**
     * Shows a confirmation dialog with options (e.g., Yes/No).
     *
     * @param message      the message to display.
     * @param title        the title of the dialog window.
     * @param optionType   the type of options to display (e.g., JOptionPane.YES_NO_OPTION).
     * @return an integer indicating the user's choice.
     */
    int showConfirmDialog(String message, String title, int optionType);
}
