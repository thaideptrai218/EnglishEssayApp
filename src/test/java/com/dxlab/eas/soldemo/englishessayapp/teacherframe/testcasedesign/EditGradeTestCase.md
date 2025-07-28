# Test Plan: `editGrade()` Method – `TeacherFrame.java`

## ✅ Objective

The purpose of this test plan is to verify the correctness and robustness of the `editGrade()` method in `TeacherFrame.java`. This method allows teachers to modify existing grades for a specific essay in the `graded.txt` file.

---

## 📁 Files Involved

| File Name             | Description                                     |
| --------------------- | ----------------------------------------------- |
| `graded.txt`          | Stores finalized grading records                |
| `grading_history.txt` | (Optional) Can be updated if history is tracked |

---

## 🧪 Test Cases

### 1. `shouldEditExistingGradeSuccessfully`

- **Description**: Ensure that an existing grade for a selected essay is correctly updated in `graded.txt`.
- **Preconditions**: The file contains a record matching the essay.
- **Expected Outcome**: The target line is updated; old content (e.g., comment) is replaced.

---

### 2. `shouldSkipEditIfEssayNotFound`

- **Description**: Handle cases where the essay to edit does not exist in `graded.txt`.
- **Preconditions**: Essay ID is not present in file.
- **Expected Outcome**: File remains unchanged; no exception thrown.

---

### 3. `shouldOnlyEditMatchingEssayLine`

- **Description**: If multiple essays are present, only the row matching the selected essay should be modified.
- **Preconditions**: `graded.txt` has multiple lines with different essay IDs.
- **Expected Outcome**: Only the correct line is changed; others are untouched.

---

### 4. `shouldHandleEmptyGradedFile`

- **Description**: Gracefully handle the case when `graded.txt` is empty.
- **Preconditions**: `graded.txt` exists but contains no content.
- **Expected Outcome**: No crash or exception; file remains empty or unchanged.

---

### 5. `shouldHandleMissingGradedFile`

- **Description**: Handle gracefully when `graded.txt` does not exist at all.
- **Preconditions**: `graded.txt` file is deleted before calling the method.
- **Expected Outcome**: No exception; no file is created unless needed.

---

## 🛠 Test Environment

- **Test Framework**: JUnit 5
- **Test Style**: File-based testing using `@TempDir` for isolation
- **UI Dependency**: Uses `JTable` for simulating row selections

---

## 📌 Notes

- The method does not return a value, so verification is done via file inspection (`graded.txt` content).
- If the method includes dialog interactions, mocking (e.g., `DialogManager`) is recommended to prevent GUI blocking.
- Be sure the essay ID, student ID, and topic ID match exactly in the test data to ensure a successful match during edit.

---
