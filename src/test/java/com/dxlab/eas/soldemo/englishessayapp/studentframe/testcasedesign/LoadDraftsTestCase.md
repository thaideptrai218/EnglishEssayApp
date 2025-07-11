**ID:** TC_LD_01
**Test condition:** File `drafts.txt` exists and contains valid data for the student.
**Steps:**
1. Create a temporary `drafts.txt` file with valid draft entries for the student.
2. Instantiate `StudentFrame`.
3. Invoke the `loadDrafts` method.
4. Access the `draftTableModel`.
**Expected Result:** The `draftTableModel` is populated with the student's drafts.
**Type:** N

**ID:** TC_LD_02
**Test condition:** File `drafts.txt` exists but contains no data for the student.
**Steps:**
1. Create a temporary `drafts.txt` file with draft entries for other students.
2. Instantiate `StudentFrame`.
3. Invoke the `loadDrafts` method.
4. Access the `draftTableModel`.
**Expected Result:** The `draftTableModel` remains empty.
**Type:** N

**ID:** TC_LD_03
**Test condition:** File `drafts.txt` is empty.
**Steps:**
1. Create an empty temporary `drafts.txt` file.
2. Instantiate `StudentFrame`.
3. Invoke the `loadDrafts` method.
4. Access the `draftTableModel`.
**Expected Result:** The `draftTableModel` remains empty.
**Type:** A

**ID:** TC_LD_04
**Test condition:** File `drafts.txt` does not exist.
**Steps:**
1. Ensure no `drafts.txt` file exists in the test directory.
2. Instantiate `StudentFrame`.
3. Invoke the `loadDrafts` method.
4. Access the `draftTableModel`.
**Expected Result:** The `draftTableModel` remains empty.
**Type:** B
