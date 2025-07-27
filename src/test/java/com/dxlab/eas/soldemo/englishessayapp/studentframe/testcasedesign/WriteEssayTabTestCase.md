
# Test Cases: Student Flow - Write Essay Tab

**Test Date:** 2025-07-27

---

## Core Actions

### `ST-STU-WE-01` - Save a New Draft Successfully
*   **Note**: Verifies the primary success path for saving a work-in-progress.

**Precondition:**
*   The user is logged in as a Student.
*   A topic has been successfully selected from the "Select Topic" tab (e.g., `T1`).

**Test Procedure:**
1.  Navigate to the "Write Essay" tab.
2.  Enter a significant amount of text into the `essayArea`.
3.  Click the "Save Draft" button.

**Test Data:**
*   **Essay Content**: "This is the first draft of my essay about technology in education."

**Expected Result:**
*   A success dialog appears with the message "Draft saved successfully!".
*   The text in the `essayArea` is cleared.
*   The `selectedTopicId` for the session is reset to null.
*   A new entry corresponding to this draft is created in `drafts.txt`.
*   The new draft is visible in the "View Drafts" tab.

---

### `ST-STU-WE-02` - Submit a New Essay Successfully
*   **Note**: Verifies the primary success path for submitting a completed essay for grading.

**Precondition:**
*   The user is logged in as a Student.
*   A topic has been successfully selected (e.g., `T2`).

**Test Procedure:**
1.  Navigate to the "Write Essay" tab.
2.  Enter the complete essay text into the `essayArea`.
3.  Click the "Submit Essay" button.

**Test Data:**
*   **Essay Content**: "This is my final essay on the advantages and disadvantages of city life."

**Expected Result:**
*   A success dialog appears with the message "Essay submitted successfully!".
*   The text in the `essayArea` is cleared.
*   The `selectedTopicId` for the session is reset to null.
*   A new entry is created in `submitted.txt`.
*   The new submission is visible in the "View Submitted" tab with a "Not graded" status.

---

### `ST-STU-WE-03` - Edit and Update a Loaded Draft
*   **Note**: Tests the workflow of continuing work on a previously saved draft.

**Precondition:**
*   The user is logged in as a Student.
*   An existing draft has been loaded into the editor from the "View Drafts" tab.
*   The `essayArea` contains the draft's content, and `selectedTopicId` is set.

**Test Procedure:**
1.  Modify the text in the `essayArea`.
2.  Click the "Save Draft" button.
3.  A confirmation dialog "Save changes to this draft?" appears. Click "OK".

**Test Data:**
*   **Modified Content**: "This is the updated and improved version of my draft."

**Expected Result:**
*   A success dialog appears with the message "Draft updated successfully!".
*   The corresponding draft entry in `drafts.txt` is updated with the new content.
*   The `essayArea` is cleared and `selectedTopicId` is reset.

---

## Validation and Edge Cases

### `ST-STU-WE-04` - Attempt to Save Draft Without a Topic
*   **Note**: Input validation to ensure every draft is linked to a topic.

**Precondition:**
*   The user is logged in as a Student.
*   No topic has been selected in the current session (`selectedTopicId` is null).

**Test Procedure:**
1.  Navigate to the "Write Essay" tab.
2.  Enter text into the `essayArea`.
3.  Click the "Save Draft" button.

**Test Data:**
*   **Essay Content**: "Some text here."

**Expected Result:**
*   A warning dialog appears with the message "Please select a topic first!".
*   No new file entry is created in `drafts.txt`.

---

### `ST-STU-WE-05` - Attempt to Submit Essay Without a Topic
*   **Note**: Input validation to ensure every submission is linked to a topic.

**Precondition:**
*   The user is logged in as a Student.
*   No topic has been selected in the current session.

**Test Procedure:**
1.  Navigate to the "Write Essay" tab.
2.  Enter text into the `essayArea`.
3.  Click the "Submit Essay" button.

**Test Data:**
*   **Essay Content**: "Some text here."

**Expected Result:**
*   A warning dialog appears with the message "Please select a topic first!".
*   No new file entry is created in `submitted.txt`.

---

### `ST-STU-WE-06` - Attempt to Save an Empty Draft
*   **Note**: Input validation to prevent saving empty, meaningless drafts.

**Precondition:**
*   The user is logged in as a Student.
*   A topic has been selected.

**Test Procedure:**
1.  Navigate to the "Write Essay" tab.
2.  Ensure the `essayArea` is empty or contains only whitespace.
3.  Click the "Save Draft" button.

**Test Data:**
*   **Essay Content**: "   "

**Expected Result:**
*   A warning dialog appears with the message "Please write something before saving!".
*   No new file entry is created in `drafts.txt`.

---

### `ST-STU-WE-07` - Attempt to Submit an Empty Essay
*   **Note**: Input validation to prevent submitting empty essays.

**Precondition:**
*   The user is logged in as a Student.
*   A topic has been selected.

**Test Procedure:**
1.  Navigate to the "Write Essay" tab.
2.  Ensure the `essayArea` is empty or contains only whitespace.
3.  Click the "Submit Essay" button.

**Test Data:**
*   **Essay Content**: (empty)

**Expected Result:**
*   A warning dialog appears with the message "Please write something before submitting!".
*   No new file entry is created in `submitted.txt`.

