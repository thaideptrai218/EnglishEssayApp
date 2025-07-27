
# Test Cases: Student Flow - View Drafts Tab

**Test Date:** 2025-07-27

---

## Draft Loading and Display

### `ST-STU-VD-01` - Successfully Load and Display Drafts
*   **Note**: Verifies that only the logged-in student's drafts are displayed.

**Precondition:**
*   The user is logged in as a Student (e.g., `student1`).
*   `drafts.txt` contains drafts for `student1` and other students.

**Test Procedure:**
1.  Navigate to the "View Drafts" tab.

**Test Data:**
*   `drafts.txt` contains:
    ```
    student1 | draft1 | T1 | Content for student 1 draft.
    student2 | draft2 | T2 | Content for student 2 draft.
    student1 | draft3 | T2 | More content for student 1.
    ```

**Expected Result:**
*   The table in the "View Drafts" tab is populated with two entries: `draft1` and `draft3`.
*   No drafts belonging to `student2` are visible.

---

### `ST-STU-VD-02` - Display When No Drafts Exist for Student
*   **Note**: Tests the UI state when the student has no saved work.

**Precondition:**
*   The user is logged in as a Student (e.g., `student3`).
*   `drafts.txt` contains drafts, but none for `student3`.

**Test Procedure:**
1.  Log in as `student3`.
2.  Navigate to the "View Drafts" tab.

**Test Data:**
*   N/A

**Expected Result:**
*   The drafts table remains empty. The application shows a blank table without errors.

---

## Draft Interaction

### `ST-STU-VD-03` - View a Draft's Full Content
*   **Note**: Tests the read-only viewing functionality.

**Precondition:**
*   The user is logged in as a Student and is on the "View Drafts" tab.
*   The drafts list is populated.

**Test Procedure:**
1.  Select a draft from the table.
2.  Click the "View Draft" button (or double-click the draft).

**Test Data:**
*   Select `draft1`.

**Expected Result:**
*   A dialog box appears with the title "Draft: draft1".
*   The dialog displays the full topic description and the complete, un-truncated content of the draft.

---

### `ST-STU-VD-04` - Load a Draft for Editing
*   **Note**: Core functionality for continuing work on an essay.

**Precondition:**
*   The user is logged in as a Student and is on the "View Drafts" tab.
*   The drafts list is populated.

**Test Procedure:**
1.  Select a draft from the table.
2.  Click the "Edit Draft" button.

**Test Data:**
*   Select `draft3` (linked to topic `T2`).

**Expected Result:**
*   A confirmation dialog appears: "Draft loaded into editor. Topic: [Description for T2]".
*   The application switches to the "Write Essay" tab.
*   The `essayArea` is populated with the full content of `draft3`.
*   The `selectedTopicId` is set to `T2` for the session, allowing the user to save or submit the edited work.

---

### `ST-STU-VD-05` - Delete a Draft Successfully
*   **Note**: Verifies the permanent removal of a draft.

**Precondition:**
*   The user is logged in as a Student and is on the "View Drafts" tab.
*   The drafts list is populated.

**Test Procedure:**
1.  Select a draft from the table.
2.  Click the "Delete Draft" button.
3.  A confirmation dialog "Delete this draft?" appears. Click "Yes".

**Test Data:**
*   Select `draft1`.

**Expected Result:**
*   A success dialog appears: "Draft deleted successfully!".
*   The selected draft is removed from the table in the UI.
*   The corresponding entry is removed from the `drafts.txt` file.

---

## Validation and Edge Cases

### `ST-STU-VD-06` - Attempt Interaction with No Draft Selected
*   **Note**: Input validation for all action buttons.

**Precondition:**
*   The user is logged in as a Student and is on the "View Drafts" tab.
*   No row is selected in the drafts table.

**Test Procedure:**
1.  Click the "View Draft" button.
2.  Click the "Edit Draft" button.
3.  Click the "Delete Draft" button.

**Test Data:**
*   N/A

**Expected Result:**
*   For all three button clicks, a warning dialog appears with the message "Please select a draft!".
*   No actions are performed.

---

### `ST-STU-VD-07` - Cancel a Deletion
*   **Note**: Ensures the cancel option in the confirmation dialog works as expected.

**Precondition:**
*   The user is logged in as a Student and is on the "View Drafts" tab.
*   A draft is selected.

**Test Procedure:**
1.  Click the "Delete Draft" button.
2.  A confirmation dialog "Delete this draft?" appears. Click "No".

**Test Data:**
*   N/A

**Expected Result:**
*   The confirmation dialog closes.
*   The draft is not deleted and remains visible in the UI table and present in the `drafts.txt` file.

