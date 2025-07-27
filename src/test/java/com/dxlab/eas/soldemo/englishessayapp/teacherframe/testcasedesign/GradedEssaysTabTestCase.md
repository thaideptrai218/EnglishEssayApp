
# Test Cases: Teacher Flow - Graded Essays Tab

**Test Date:** 2025-07-27

---

## Graded Essay Display and Viewing

### `ST-TEA-GDE-01` - Load and Display All Finally Graded Essays
*   **Note**: Verifies that this tab correctly shows only essays that have been marked as a final grade.

**Precondition:**
*   The user is logged in as a Teacher.
*   `graded.txt` contains multiple entries.
*   `grading_history.txt` may contain additional "draft" grades that should not appear here.

**Test Procedure:**
1.  Navigate to the "Graded Essays" tab.

**Test Data:**
*   `graded.txt` contains entries for `essay1` and `essay3`.

**Expected Result:**
*   The table in the "Graded Essays" tab is populated with the final grades for `essay1` and `essay3`.
*   The table correctly displays all score columns (Task, Coherence, Lexical, Grammar).

---

### `ST-TEA-GDE-02` - View Graded Essay Details
*   **Note**: Tests the ability to review the full context of a graded submission.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Graded Essays" tab.
*   The list of graded essays is populated.

**Test Procedure:**
1.  Select an essay from the table.
2.  Click the "View Graded Essay" button (or double-click the row).

**Test Data:**
*   Select `essay1`.

**Expected Result:**
*   A dialog appears showing the full essay content, topic, and a detailed breakdown of the final grade and comments.

---

## Grade Editing

### `ST-TEA-GDE-03` - Successfully Edit an Existing Grade
*   **Note**: Verifies the primary path for correcting or updating a final grade.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Graded Essays" tab.
*   An essay is selected.

**Test Procedure:**
1.  Select an essay and click the "Edit Grade" button.
2.  The grading dialog appears, pre-filled with the current scores and comments.
3.  Modify the scores using the sliders and update the comments.
4.  Click "OK".

**Test Data:**
*   **New Scores**: Task: 9, Coherence: 8, Lexical: 9, Grammar: 8
*   **New Comments**: "Excellent improvement. The structure is much clearer now."

**Expected Result:**
*   A success dialog appears: "Grade updated successfully!".
*   The corresponding entry in `graded.txt` is updated with the new scores and comments.
*   A new, separate entry is added to `grading_history.txt` to log the edit action, including the new scores and a new timestamp.
*   The table in the "Graded Essays" tab updates in real-time to show the new scores.

---

### `ST-TEA-GDE-04` - Cancel a Grade Edit
*   **Note**: Ensures that backing out of an edit does not save any changes.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Graded Essays" tab.
*   An essay is selected.

**Test Procedure:**
1.  Select an essay and click "Edit Grade".
2.  Modify the scores in the dialog.
3.  Click the "Cancel" button (or close the dialog).

**Test Data:**
*   N/A

**Expected Result:**
*   The dialog closes.
*   No changes are saved. The grade information in the UI and in `graded.txt` remains unchanged.
*   No new entry is created in `grading_history.txt`.

---

## Validation

### `ST-TEA-GDE-05` - Attempt Interaction with No Essay Selected
*   **Note**: Input validation for the action buttons.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Graded Essays" tab.
*   No row is selected in the table.

**Test Procedure:**
1.  Click the "View Graded Essay" button.
2.  Click the "Edit Grade" button.

**Test Data:**
*   N/A

**Expected Result:**
*   For both button clicks, a warning dialog appears with the message "Please select a graded essay!".
*   No actions are performed.

