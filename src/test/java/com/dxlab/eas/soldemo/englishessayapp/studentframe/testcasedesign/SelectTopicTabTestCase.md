
# Test Cases: Student Flow - Select Topic Tab

**Test Date:** 2025-07-27

---

## Topic Loading

### `ST-STU-STL-01` - Successful Topic Loading from File
*   **Note**: Verifies the primary success path for populating the topic list.

**Precondition:**
*   The `topics.txt` file exists and contains valid, formatted topic entries.

**Test Procedure:**
1.  Log in as a Student.
2.  Navigate to the "Select Topic" tab.

**Test Data:**
*   `topics.txt` contains:
    ```
    T1 | Description for Topic 1
    T2 | Description for Topic 2
    ```

**Expected Result:**
*   The table in the "Select Topic" tab is populated with two rows, correctly displaying the Topic IDs and Descriptions from the file.

---

### `ST-STU-STL-02` - Fallback to Default Topics
*   **Note**: Ensures the application is usable even if the topics file is missing.

**Precondition:**
*   The `topics.txt` file does not exist.

**Test Procedure:**
1.  Log in as a Student.
2.  Navigate to the "Select Topic" tab.

**Test Data:**
*   No `topics.txt` file.

**Expected Result:**
*   The topics table is populated with the 3 default topics hardcoded in the application.
*   A new `topics.txt` file is created with the default topic content.

---

### `ST-STU-STL-03` - Handling of Empty Topics File
*   **Note**: Edge case for data integrity.

**Precondition:**
*   The `topics.txt` file exists but is empty.

**Test Procedure:**
1.  Log in as a Student.
2.  Navigate to the "Select Topic" tab.

**Test Data:**
*   An empty `topics.txt` file.

**Expected Result:**
*   The topics table remains empty. The application handles the empty file gracefully without errors.

---

## Topic Interaction

### `ST-STU-STV-01` - View Topic Description via Double-Click
*   **Note**: Tests the primary method for viewing topic details.

**Precondition:**
*   The user is logged in as a Student and is on the "Select Topic" tab.
*   The topic list is populated.

**Test Procedure:**
1.  Double-click on a row in the topics table.

**Test Data:**
*   Double-click the row for Topic `T1`.

**Expected Result:**
*   A dialog box appears with the title "Topic: T1" and displays the full description of that topic.

---

### `ST-STU-STS-01` - Successfully Select a Topic
*   **Note**: Core action required before writing or submitting an essay.

**Precondition:**
*   The user is logged in as a Student and is on the "Select Topic" tab.
*   The topic list is populated.

**Test Procedure:**
1.  Click on a row in the topics table to select it.
2.  Click the "Select Topic" button.

**Test Data:**
*   Select the row for Topic `T2`.

**Expected Result:**
*   A confirmation dialog appears with the message "Selected topic: [Description for Topic 2]".
*   The application internally stores `T2` as the `selectedTopicId` for the current session.

---

### `ST-STU-STS-02` - Attempt to Select with No Topic Chosen
*   **Note**: Input validation test.

**Precondition:**
*   The user is logged in as a Student and is on the "Select Topic" tab.
*   No row is selected in the table.

**Test Procedure:**
1.  Click the "Select Topic" button without first clicking on a topic in the table.

**Test Data:**
*   N/A

**Expected Result:**
*   A warning dialog appears with the message "Please select a topic!".
*   The `selectedTopicId` remains null.

---

### `ST-STU-STC-01` - Cancel a Topic Selection
*   **Note**: Verifies the functionality to clear a selected topic.

**Precondition:**
*   A topic has been successfully selected using the steps in `ST-STU-STS-01`.

**Test Procedure:**
1.  Click the "Cancel Selection" button.

**Test Data:**
*   N/A

**Expected Result:**
*   The `selectedTopicId` is set to null.
*   If the student now tries to save a draft or submit, they will be prompted to select a topic first.

