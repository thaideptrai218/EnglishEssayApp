
# Test Cases: Student Flow - View Submitted Tab

**Test Date:** 2025-07-27

---

## Submission Loading and Display

### `ST-STU-VS-01` - Load and Display Graded and Ungraded Submissions
*   **Note**: Verifies that all submissions for the logged-in student are displayed with the correct grade status.

**Precondition:**
*   The user is logged in as a Student (e.g., `student1`).
*   `submitted.txt` contains multiple submissions for `student1`.
*   `graded.txt` contains a grade for one of `student1`'s essays but not the other.

**Test Procedure:**
1.  Navigate to the "View Submitted" tab.

**Test Data:**
*   `submitted.txt` contains:
    ```
    student1 | essay1 | T1 | Content for first essay.
    student1 | essay2 | T2 | Content for second essay.
    student2 | essay3 | T1 | Content for other student.
    ```
*   `graded.txt` contains:
    ```
    student1 | essay1 | T1 | 8 | 7 | 8 | 7 | Good job.
    ```

**Expected Result:**
*   The table in the "View Submitted" tab is populated with two entries: `essay1` and `essay2`.
*   The "Grade Summary" for `essay1` displays the formatted scores (e.g., "Task: 8, Coherence: 7, Lexical: 8, Grammar: 7").
*   The "Grade Summary" for `essay2` displays "Not graded".
*   No submissions belonging to `student2` are visible.

---

### `ST-STU-VS-02` - Display When No Submissions Exist
*   **Note**: Tests the UI state when the student has not submitted any essays.

**Precondition:**
*   The user is logged in as a new Student (e.g., `student4`) who has no entries in `submitted.txt`.

**Test Procedure:**
1.  Log in as `student4`.
2.  Navigate to the "View Submitted" tab.

**Test Data:**
*   N/A

**Expected Result:**
*   The submitted essays table remains empty. The application shows a blank table without errors.

---

## Viewing Results

### `ST-STU-VS-03` - View Result of a Graded Essay
*   **Note**: Tests the primary feature of viewing detailed feedback.

**Precondition:**
*   The user is logged in as a Student and is on the "View Submitted" tab.
*   The list contains an essay that has been graded (as in `ST-STU-VS-01`).

**Test Procedure:**
1.  Select the graded essay (`essay1`) from the table.
2.  Click the "View Essay and Result" button (or double-click the row).

**Test Data:**
*   Select `essay1`.

**Expected Result:**
*   A dialog box appears with the title "Essay and Result: essay1".
*   The dialog displays the original topic, the full essay content, and a formatted section with the detailed scores and teacher's comments.

---

### `ST-STU-VS-04` - View an Ungraded Essay
*   **Note**: Verifies that students can still view the content of submissions that are pending a grade.

**Precondition:**
*   The user is logged in as a Student and is on the "View Submitted" tab.
*   The list contains an essay with a "Not graded" status.

**Test Procedure:**
1.  Select the ungraded essay (`essay2`) from the table.
2.  Click the "View Essay and Result" button.

**Test Data:**
*   Select `essay2`.

**Expected Result:**
*   A dialog box appears with the title "Essay: essay2 (Not graded)".
*   The dialog displays the original topic and the full essay content.
*   There is no grade or comment section.

---

## Validation

### `ST-STU-VS-05` - Attempt to View Result with No Essay Selected
*   **Note**: Input validation for the action button.

**Precondition:**
*   The user is logged in as a Student and is on the "View Submitted" tab.
*   No row is selected in the submitted essays table.

**Test Procedure:**
1.  Click the "View Essay and Result" button.

**Test Data:**
*   N/A

**Expected Result:**
*   A warning dialog appears with the message "Please select an essay!".
*   No view dialog is shown.

