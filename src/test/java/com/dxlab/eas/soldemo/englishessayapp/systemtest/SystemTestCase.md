# System Test Cases for English Essay App

This document outlines the system-level test cases for the English Essay Application. These tests cover the end-to-end functionality of the application, including edge cases and interactions between the Student and Teacher roles.

---

### `ST-001`: Successful Student Login and Logout
*   **Description**: Verifies that a student can successfully log in with a unique ID and then log out, returning to the login screen.
*   **Preconditions**: No specific user accounts are required.
*   **Steps**:
    1. Launch the application.
    2. Select "Student" from the role dropdown.
    3. Enter a unique ID (e.g., "student1").
    4. Click "Login".
    5. From the Student Panel, click "Logout".
*   **Expected Results**:
    1. The `LoginFrame` appears initially.
    2. After login, the `StudentFrame` appears with the title "Student Panel - ID: student1".
    3. After logout, the `StudentFrame` closes and the `LoginFrame` reappears.

---

### `ST-002`: Successful Teacher Login and Logout
*   **Description**: Verifies that a teacher can successfully log in with a unique ID and then log out, returning to the login screen.
*   **Preconditions**: No specific user accounts are required.
*   **Steps**:
    1. Launch the application.
    2. Select "Teacher" from the role dropdown.
    3. Enter a unique ID (e.g., "teacher1").
    4. Click "Login".
    5. From the Teacher Panel, click "Logout".
*   **Expected Results**:
    1. The `LoginFrame` appears initially.
    2. After login, the `TeacherFrame` appears with the title "Teacher Panel - ID: teacher1".
    3. After logout, the `TeacherFrame` closes and the `LoginFrame` reappears.

---

### `ST-003`: Login Attempt with Empty ID
*   **Description**: Ensures the system prevents login attempts with an empty ID field.
*   **Preconditions**: The application is at the `LoginFrame`.
*   **Steps**:
    1. Launch the application.
    2. Select either "Student" or "Teacher".
    3. Leave the ID field empty.
    4. Click "Login".
*   **Expected Results**:
    * A warning dialog appears with the message "Please enter ID".
    * The user remains on the `LoginFrame`.

---

### `ST-004`: Student Views and Selects a Topic
*   **Description**: Verifies that a student can view the list of available topics and select one to work on.
*   **Preconditions**: The `topics.txt` file is populated, or the app is run for the first time to generate default topics.
*   **Steps**:
    1. Log in as a Student.
    2. In the "Select Topic" tab, select a topic from the list.
    3. Click the "Select Topic" button.
*   **Expected Results**:
    * The topics table is populated.
    * A confirmation dialog appears showing the selected topic's description.
    * The `selectedTopicId` is set internally for the student's session.

---

### `ST-005`: Student Submits an Essay Successfully
*   **Description**: Tests the end-to-end process of a student selecting a topic, writing an essay, and submitting it.
*   **Preconditions**: The student is logged in and has selected a topic (as in ST-004).
*   **Steps**:
    1. Navigate to the "Write Essay" tab.
    2. Enter a non-empty essay text.
    3. Click the "Submit Essay" button.
*   **Expected Results**:
    1. A success dialog appears.
    2. The essay content is appended to `submitted.txt`.
    3. The essay text area is cleared.
    4. The "View Submitted" tab now lists the new submission with a "Not graded" status.

---

### `ST-006`: Student Saves, Views, and Edits a Draft
*   **Description**: Verifies the complete draft management workflow: saving, viewing, and editing.
*   **Preconditions**: The student is logged in and has selected a topic.
*   **Steps**:
    1. Go to the "Write Essay" tab, enter text, and click "Save Draft".
    2. Go to the "View Drafts" tab.
    3. Select the newly created draft and click "View Draft".
    4. Click "Edit Draft".
    5. Modify the content in the "Write Essay" tab and save the changes.
*   **Expected Results**:
    1. The draft is saved to `drafts.txt` and appears in the "View Drafts" list.
    2. A dialog shows the full draft content.
    3. The draft content is loaded into the "Write Essay" text area.
    4. The `drafts.txt` file is updated with the new content.

---

### `ST-007`: Student Deletes a Draft
*   **Description**: Ensures a student can permanently remove a saved draft.
*   **Preconditions**: A student has at least one saved draft in `drafts.txt`.
*   **Steps**:
    1. Log in as the student who owns the draft.
    2. Go to the "View Drafts" tab.
    3. Select a draft and click "Delete Draft".
    4. Confirm the deletion in the dialog.
*   **Expected Results**:
    1. A confirmation dialog appears.
    2. The draft is removed from the `drafts.txt` file.
    3. The draft is removed from the table in the UI.

---

### `ST-008`: Student Views a Graded Essay Result
*   **Description**: Verifies that a student can view the detailed results of a graded essay.
*   **Preconditions**: An essay submitted by the student has been graded by a teacher.
*   **Steps**:
    1. Log in as the student.
    2. Go to the "View Submitted" tab.
    3. The "Grade Summary" for the essay shows scores.
    4. Select the graded essay and click "View Essay and Result".
*   **Expected Results**:
    * A dialog appears showing the full essay content, topic, and a detailed breakdown of the grade and comments.

---

### `ST-009`: Attempt to Submit an Essay Without Selecting a Topic
*   **Description**: Tests system validation to prevent submitting an essay without a selected topic.
*   **Preconditions**: The student is logged in.
*   **Steps**:
    1. Go to the "Write Essay" tab without first selecting a topic.
    2. Enter text into the essay area.
    3. Click "Submit Essay".
*   **Expected Results**:
    * A warning dialog appears with the message "Please select a topic first!".
    * The essay is not submitted.

---

### `ST-010`: Attempt to Submit an Empty Essay
*   **Description**: Tests system validation to prevent submitting an empty essay.
*   **Preconditions**: The student is logged in and has selected a topic.
*   **Steps**:
    1. Go to the "Write Essay" tab.
    2. Leave the essay area empty.
    3. Click "Submit Essay".
*   **Expected Results**:
    * A warning dialog appears with the message "Please write something before submitting!".
    * The essay is not submitted.

---

### `ST-011`: Teacher Views and Filters Submitted Essays
*   **Description**: Verifies the teacher's ability to filter the list of submitted essays by student ID and topic ID.
*   **Preconditions**: Multiple students have submitted essays for various topics.
*   **Steps**:
    1. Log in as a Teacher.
    2. The "Grade Essays" tab shows all submitted essays.
    3. Enter a specific "Student ID" in the filter field and click "Filter".
    4. Clear the student filter, enter a "Topic ID", and click "Filter".
*   **Expected Results**:
    1. The table updates to show only essays from the specified student.
    2. The table updates to show only essays for the specified topic.

---

### `ST-012`: Teacher Grades an Essay
*   **Description**: Tests the end-to-end workflow for a teacher grading a submitted essay.
*   **Preconditions**: An ungraded essay exists in `submitted.txt`.
*   **Steps**:
    1. Log in as a Teacher.
    2. In the "Grade Essays" tab, select an ungraded essay.
    3. Click "Grade Essay".
    4. Use the sliders to set scores, add comments, and check "Mark as Final Grade".
    5. Click "OK".
*   **Expected Results**:
    1. A grading dialog appears.
    2. The grade is saved to `graded.txt` and a record is added to `grading_history.txt`.
    3. The essay now appears in the "Graded Essays" tab.

---

### `ST-013`: Teacher Edits an Existing Grade
*   **Description**: Verifies that a teacher can modify a previously submitted grade.
*   **Preconditions**: An essay has already been graded and appears in the "Graded Essays" tab.
*   **Steps**:
    1. Log in as a Teacher.
    2. Go to the "Graded Essays" tab.
    3. Select an essay and click "Edit Grade".
    4. Change the scores and comments in the dialog.
    5. Click "OK".
*   **Expected Results**:
    1. The grade editing dialog appears, pre-filled with the previous grade.
    2. The `graded.txt` file is updated with the new grade information.
    3. A new entry is added to `grading_history.txt` to log the change.

---

### `ST-014`: Teacher Views Grading History
*   **Description**: Ensures a teacher can review their own past grading activities.
*   **Preconditions**: A teacher has graded or edited grades for multiple essays.
*   **Steps**:
    1. Log in as the Teacher.
    2. Go to the "Grading History" tab.
    3. Select a record from the history table.
    4. Click "View Grading Details".
*   **Expected Results**:
    1. The history table is populated with all grading actions performed by that teacher.
    2. A dialog appears showing the full details of the selected historical grading action, including scores, comments, and timestamp.

---

### `ST-015`: Data Isolation Between Students
*   **Description**: Verifies that a student can only see their own drafts and submissions, ensuring data privacy.
*   **Preconditions**: Two students, "student1" and "student2", exist. "student1" has saved drafts and submitted essays.
*   **Steps**:
    1. Log in as "student2".
    2. Navigate to the "View Drafts" tab.
    3. Navigate to the "View Submitted" tab.
*   **Expected Results**:
    * The tables for drafts and submitted essays are empty. "student2" cannot see any data belonging to "student1".
