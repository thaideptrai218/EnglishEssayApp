**ID:** TC_LS_01
**Test condition:** File `submitted.txt` exists and contains valid data for the student.
**Steps:**
1. Create a temporary `submitted.txt` file with valid submitted entries for the student.
2. Instantiate `StudentFrame`.
3. Invoke the `loadSubmitted` method.
4. Access the `submittedTableModel`.
**Expected Result:** The `submittedTableModel` is populated with the student's submitted essays.
**Type:** N

**ID:** TC_LS_02
**Test condition:** File `submitted.txt` exists but contains no data for the student.
**Steps:**
1. Create a temporary `submitted.txt` file with submitted entries for other students.
2. Instantiate `StudentFrame`.
3. Invoke the `loadSubmitted` method.
4. Access the `submittedTableModel`.
**Expected Result:** The `submittedTableModel` remains empty.
**Type:** N

**ID:** TC_LS_03
**Test condition:** File `submitted.txt` is empty.
**Steps:**
1. Create an empty temporary `submitted.txt` file.
2. Instantiate `StudentFrame`.
3. Invoke the `loadSubmitted` method.
4. Access the `submittedTableModel`.
**Expected Result:** The `submittedTableModel` remains empty.
**Type:** A

**ID:** TC_LS_04
**Test condition:** File `submitted.txt` does not exist.
**Steps:**
1. Ensure no `submitted.txt` file exists in the test directory.
2. Instantiate `StudentFrame`.
3. Invoke the `loadSubmitted` method.
4. Access the `submittedTableModel`.
**Expected Result:** The `submittedTableModel` remains empty.
**Type:** B
