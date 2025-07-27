
# System Test Cases: EnglishEssayApp

**Test Date:** 2025-07-27

---

## Login Flow

### `ST-LOG-01` - Successful Student Login
*   **Note**: Core functionality check.

**Test Procedure:**
1.  Launch the application.
2.  Select "Student" from the role dropdown.
3.  Enter a valid ID in the ID field.
4.  Click "Login".

**Test Data:**
*   **Role**: `Student`
*   **ID**: `student01`

**Expected Result:**
*   The `StudentFrame` appears with the title "Student Panel - ID: student01".

---

### `ST-LOG-02` - Successful Teacher Login
*   **Note**: Core functionality check.

**Test Procedure:**
1.  Launch the application.
2.  Select "Teacher" from the role dropdown.
3.  Enter a valid ID in the ID field.
4.  Click "Login".

**Test Data:**
*   **Role**: `Teacher`
*   **ID**: `teacher01`

**Expected Result:**
*   The `TeacherFrame` appears with the title "Teacher Panel - ID: teacher01".

---

### `ST-LOG-03` - Login with Empty ID
*   **Note**: Input validation test.

**Test Procedure:**
1.  Launch the application.
2.  Select either "Student" or "Teacher".
3.  Leave the ID field empty.
4.  Click "Login".

**Test Data:**
*   **Role**: `Student`
*   **ID**: (empty)

**Expected Result:**
*   A warning dialog appears with the message "Please enter ID".
*   The user remains on the `LoginFrame`.

---

## Student Flow

### `ST-STU-01` - View and Select a Topic
*   **Inter-test case Dependence**: `ST-LOG-01`

**Test Procedure:**
1.  Log in as a Student (`student01`).
2.  In the "Select Topic" tab, observe the list of topics.
3.  Select a topic from the table.
4.  Click the "Select Topic" button.

**Test Data:**
*   N/A

**Expected Result:**
*   The topics table is populated.
*   A confirmation dialog appears showing the selected topic's description.

---

### `ST-STU-02` - Save an Essay as a Draft
*   **Inter-test case Dependence**: `ST-STU-01`

**Test Procedure:**
1.  Log in as a Student (`student01`).
2.  Select a topic (e.g., "T1").
3.  Navigate to the "Write Essay" tab.
4.  Enter text into the essay area.
5.  Click "Save Draft".

**Test Data:**
*   **Essay Text**: "This is a draft essay."

**Expected Result:**
*   A success dialog appears.
*   The new draft is visible in the "View Drafts" tab.
*   The `drafts.txt` file contains the new entry.

---

### `ST-STU-03` - Submit an Essay
*   **Inter-test case Dependence**: `ST-STU-01`

**Test Procedure:**
1.  Log in as a Student (`student01`).
2.  Select a topic (e.g., "T1").
3.  Navigate to the "Write Essay" tab.
4.  Enter text into the essay area.
5.  Click "Submit Essay".

**Test Data:**
*   **Essay Text**: "This is a final essay."

**Expected Result:**
*   A success dialog appears.
*   The new submission is visible in the "View Submitted" tab with a "Not graded" status.
*   The `submitted.txt` file contains the new entry.

---

### `ST-STU-04` - View and Edit a Draft
*   **Inter-test case Dependence**: `ST-STU-02`

**Test Procedure:**
1.  Log in as a Student (`student01`).
2.  Navigate to the "View Drafts" tab.
3.  Select a draft and click "Edit Draft".
4.  The "Write Essay" tab becomes active with the draft content.
5.  Modify the text.
6.  Click "Save Draft" again.

**Test Data:**
*   **Modified Text**: "This is an updated draft."

**Expected Result:**
*   The draft content is loaded into the editor.
*   After saving, the `drafts.txt` file is updated with the new content.

---

### `ST-STU-05` - Delete a Draft
*   **Inter-test case Dependence**: `ST-STU-02`

**Test Procedure:**
1.  Log in as a Student (`student01`).
2.  Navigate to the "View Drafts" tab.
3.  Select a draft and click "Delete Draft".
4.  Click "Yes" in the confirmation dialog.

**Test Data:**
*   N/A

**Expected Result:**
*   The draft is removed from the table in the UI and from the `drafts.txt` file.

---

### `ST-STU-06` - View a Graded Essay
*   **Inter-test case Dependence**: `ST-TEA-02`
*   **Note**: Tests student-teacher interaction.

**Test Procedure:**
1.  Log in as a Student (`student01`).
2.  Navigate to the "View Submitted" tab.
3.  Select an essay that has been graded.
4.  Click "View Essay and Result".

**Test Data:**
*   N/A

**Expected Result:**
*   A dialog appears showing the full essay content, topic, and a detailed breakdown of the grade and comments.

---

### `ST-STU-07` - Attempt to Submit an Empty Essay
*   **Inter-test case Dependence**: `ST-STU-01`
*   **Note**: Input validation test.

**Test Procedure:**
1.  Log in as a Student (`student01`).
2.  Select a topic (e.g., "T1").
3.  Navigate to the "Write Essay" tab.
4.  Leave the essay area empty.
5.  Click "Submit Essay".

**Test Data:**
*   **Essay Text**: (empty)

**Expected Result:**
*   A warning dialog appears with the message "Please write something before submitting!".
*   The essay is not submitted and no new entry is added to `submitted.txt`.

---

## Teacher Flow

### `ST-TEA-01` - View and Filter Submitted Essays
*   **Inter-test case Dependence**: `ST-STU-03`

**Test Procedure:**
1.  Log in as a Teacher (`teacher01`).
2.  Observe the list of submitted essays in the "Grade Essays" tab.
3.  Enter a specific Student ID in the filter field.
4.  Click "Filter".

**Test Data:**
*   **Filter ID**: `student01`

**Expected Result:**
*   The table updates to show only essays from the specified student.

---

### `ST-TEA-02` - Grade a Submitted Essay
*   **Inter-test case Dependence**: `ST-STU-03`
*   **Note**: Core teacher functionality.

**Test Procedure:**
1.  Log in as a Teacher (`teacher01`).
2.  In the "Grade Essays" tab, select an ungraded essay.
3.  Click "Grade Essay".
4.  Set scores, add comments, and check "Mark as Final Grade".
5.  Click "OK".

**Test Data:**
*   **Scores**: Task: 8, Coherence: 7, Lexical: 8, Grammar: 7
*   **Comment**: "Good work."

**Expected Result:**
*   The grade is saved to `graded.txt`.
*   The essay appears in the "Graded Essays" tab and is removed from the "Grade Essays" tab.

---

### `ST-TEA-03` - Edit an Existing Grade
*   **Inter-test case Dependence**: `ST-TEA-02`

**Test Procedure:**
1.  Log in as a Teacher (`teacher01`).
2.  Navigate to the "Graded Essays" tab.
3.  Select an essay and click "Edit Grade".
4.  Change the scores and/or comments.
5.  Click "OK".

**Test Data:**
*   **New Scores**: Task: 9, Coherence: 8, Lexical: 9, Grammar: 8

**Expected Result:**
*   The `graded.txt` file is updated with the new grade.
*   A new entry is added to `grading_history.txt`.

---

### `ST-TEA-04` - View Grading History
*   **Inter-test case Dependence**: `ST-TEA-02`

**Test Procedure:**
1.  Log in as a Teacher (`teacher01`).
2.  Navigate to the "Grading History" tab.
3.  Select a record and click "View Grading Details".

**Test Data:**
*   N/A

**Expected Result:**
*   The history table shows all grading actions by `teacher01`.
*   A dialog appears with the full details of the selected record.

---

### `ST-TEA-05` - View Essay Content Without Grading
*   **Inter-test case Dependence**: `ST-STU-03`
*   **Note**: Read-only action test.

**Test Procedure:**
1.  Log in as a Teacher (`teacher01`).
2.  In the "Grade Essays" tab, select an ungraded essay.
3.  Click the "View Essay" button.
4.  After reviewing, click "Cancel" or close the dialog.

**Test Data:**
*   N/A

**Expected Result:**
*   A dialog appears showing the full content of the selected essay.
*   No grade is saved, and the essay remains in the "Grade Essays" tab as ungraded.

