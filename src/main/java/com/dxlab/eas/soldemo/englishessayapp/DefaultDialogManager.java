package com.dxlab.eas.soldemo.englishessayapp;

import javax.swing.JOptionPane;

/**
 * The default implementation of the DialogManager interface.
 * This class uses JOptionPane to show actual UI dialogs.
 */
public class DefaultDialogManager implements DialogManager {

    @Override
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    @Override
    public int showConfirmDialog(String message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(null, message, title, optionType);
    }
}
