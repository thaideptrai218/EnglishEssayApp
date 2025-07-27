
# Test Cases: Teacher Flow - Grading History Tab

**Test Date:** 2025-07-27

---

## History Loading and Display

### `ST-TEA-GH-01` - Load and Display Grading History for the Current Teacher
*   **Note**: Verifies that the history view is correctly filtered and displays all grading actions, including initial grades and subsequent edits.

**Precondition:**
*   The user is logged in as a Teacher (e.g., `teacher1`).
*   `grading_history.txt` contains multiple entries from different teachers and for different actions (initial grades and edits).

**Test Procedure:**
1.  Navigate to the "Grading History" tab.

**Test Data:**
*   `grading_history.txt` contains:
    ```
    teacher1 | student1 | essay1 | T1 | 8 | 7 | 8 | 7 | First grade. | 123456789
    teacher2 | student2 | essay2 | T2 | 6 | 6 | 6 | 6 | Other teacher grade. | 123456799
    teacher1 | student1 | essay1 | T1 | 9 | 8 | 9 | 8 | Edited grade. | 123456899
    ```

**Expected Result:**
*   The table in the "Grading History" tab is populated with two entries for `teacher1`.
*   The table correctly displays the Essay ID, Student ID, Topic ID, and the unique Timestamp for each action.
*   No history belonging to `teacher2` is visible.

---

### `ST-TEA-GH-02` - Display When No Grading History Exists
*   **Note**: Tests the UI state for a new teacher with no grading activity.

**Precondition:**
*   The user is logged in as a new Teacher (e.g., `teacher3`) who has no entries in `grading_history.txt`.

**Test Procedure:**
1.  Log in as `teacher3`.
2.  Navigate to the "Grading History" tab.

**Test Data:**
*   N/A

**Expected Result:**
*   The history table remains empty. The application shows a blank table without errors.

---

## Viewing History Details

### `ST-TEA-GH-03` - View Details of a Historical Grading Action
*   **Note**: Verifies that the teacher can retrieve the full details of any past grading action.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grading History" tab.
*   The history list is populated.

**Test Procedure:**
1.  Select a record from the history table.
2.  Click the "View Grading Details" button (or double-click the row).

**Test Data:**
*   Select the history record for the edited grade of `essay1` (timestamp `123456899`).

**Expected Result:**
*   A dialog box appears with the title "Grading History".
*   The dialog displays a formatted string containing all details for that specific action: Essay ID, Student ID, Topic ID, all four scores (9, 8, 9, 8), the comments ("Edited grade."), and the timestamp.

---

## Validation

### `ST-TEA-GH-04` - Attempt to View Details with No Record Selected
*   **Note**: Input validation for the action button.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grading History" tab.
*   No row is selected in the history table.

**Test Procedure:**
1.  Click the "View Grading Details" button.

**Test Data:**
*   N/A

**Expected Result:**
*   A warning dialog appears with the message "Please select a grading record!".
*   No view dialog is shown.

