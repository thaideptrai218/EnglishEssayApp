
# Test Cases: Teacher Flow - Grade Essays Tab

**Test Date:** 2025-07-27

---

## Essay Loading and Filtering

### `ST-TEA-GE-01` - Load and Display All Submitted Essays
*   **Note**: Verifies that all submitted essays from all students are visible to the teacher by default.

**Precondition:**
*   The user is logged in as a Teacher.
*   `submitted.txt` contains multiple essays from different students.

**Test Procedure:**
1.  Navigate to the "Grade Essays" tab.

**Test Data:**
*   `submitted.txt` contains:
    ```
    student1 | essay1 | T1 | Content from student 1.
    student2 | essay2 | T2 | Content from student 2.
    ```

**Expected Result:**
*   The table in the "Grade Essays" tab is populated with entries for both `essay1` and `essay2`.

---

### `ST-TEA-GE-02` - Filter Essays by Student ID
*   **Note**: Tests the primary filtering mechanism for focusing on a single student.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grade Essays" tab.
*   The essay list is populated with submissions from multiple students.

**Test Procedure:**
1.  Enter a specific Student ID into the "Student ID" filter field.
2.  Click the "Filter" button.

**Test Data:**
*   **Filter by**: `student1`

**Expected Result:**
*   The table updates to show only the essays submitted by `student1`. Submissions from `student2` are no longer visible.

---

### `ST-TEA-GE-03` - Filter Essays by Topic ID
*   **Note**: Tests the filtering mechanism for focusing on a specific essay topic.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grade Essays" tab.
*   The essay list is populated with submissions for multiple topics.

**Test Procedure:**
1.  Enter a specific Topic ID into the "Topic ID" filter field.
2.  Click the "Filter" button.

**Test Data:**
*   **Filter by**: `T2`

**Expected Result:**
*   The table updates to show only the essays submitted for topic `T2`.

---

### `ST-TEA-GE-04` - Filter by Both Student and Topic ID
*   **Note**: Verifies that the filters can be combined for a more specific search.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grade Essays" tab.
*   `submitted.txt` contains multiple entries for `student1` on different topics.

**Test Procedure:**
1.  Enter `student1` into the "Student ID" filter field.
2.  Enter `T2` into the "Topic ID" filter field.
3.  Click the "Filter" button.

**Test Data:**
*   **Student Filter**: `student1`
*   **Topic Filter**: `T2`

**Expected Result:**
*   The table updates to show only the essays from `student1` that were submitted for topic `T2`.

---

## Essay Grading

### `ST-TEA-GE-05` - View an Essay's Content
*   **Note**: Tests the read-only view before committing to a grade.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grade Essays" tab.
*   The essay list is populated.

**Test Procedure:**
1.  Select an essay from the table.
2.  Click the "View Essay" button (or double-click the row).

**Test Data:**
*   Select `essay1`.

**Expected Result:**
*   A dialog appears showing the full topic description and the complete content of `essay1`.
*   A view log entry is created in `view_log.txt`.

---

### `ST-TEA-GE-06` - Grade an Essay Successfully (Final Grade)
*   **Note**: The primary success path for the grading workflow.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grade Essays" tab.
*   An ungraded essay is selected.

**Test Procedure:**
1.  Select an ungraded essay and click "Grade Essay".
2.  In the grading dialog, use the sliders to set scores for all criteria.
3.  Enter text into the "Comments" area.
4.  Check the "Mark as Final Grade" checkbox.
5.  Click "OK".

**Test Data:**
*   **Scores**: Task: 8, Coherence: 7, Lexical: 8, Grammar: 7
*   **Comments**: "A well-argued essay with strong examples."

**Expected Result:**
*   A success dialog appears: "Grade saved successfully!".
*   A new entry is created in `graded.txt` with the final grade.
*   A new entry is created in `grading_history.txt`.
*   The essay is removed from the table in the "Grade Essays" tab.
*   The essay now appears in the "Graded Essays" tab.

---

### `ST-TEA-GE-07` - Grade an Essay as a Draft (Not Final)
*   **Note**: Tests saving a preliminary grade without marking it as final.

**Precondition:**
*   The user is logged in as a Teacher and is on the "Grade Essays" tab.
*   An ungraded essay is selected.

**Test Procedure:**
1.  Select an ungraded essay and click "Grade Essay".
2.  Set scores and add comments.
3.  Leave the "Mark as Final Grade" checkbox **unchecked**.
4.  Click "OK".

**Test Data:**
*   **Scores**: Task: 6, Coherence: 6, Lexical: 6, Grammar: 6
*   **Comments**: "Preliminary thoughts."

**Expected Result:**
*   A success dialog appears.
*   A new entry is created in `grading_history.txt`.
*   **No entry** is created in the final `graded.txt` file.
*   The essay remains in the "Grade Essays" tab, as it is not yet considered finally graded.

---

## Validation

### `ST-TEA-GE-08` - Attempt to Grade an Already Graded Essay
*   **Note**: Prevents re-grading from the wrong tab.

**Precondition:**
*   An essay has already been marked as a "Final Grade".
*   The teacher has cleared filters and the essay is somehow still visible or selected.

**Test Procedure:**
1.  Select the already-graded essay in the "Grade Essays" tab.
2.  Click the "Grade Essay" button.

**Test Data:**
*   N/A

**Expected Result:**
*   A warning dialog appears with the message "This essay has already been graded. Use 'Edit Grade' to modify.".
*   The grading dialog does not open.

