**ID:** TC_LGH_01
**Test condition:** File `grading_history.txt` exists and contains grading history for the teacher.
**Steps:**
1. Create a temporary `grading_history.txt` file with grading history entries for the teacher.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadGradingHistory` method.
4. Access the `historyTableModel`.
**Expected Result:** The `historyTableModel` is populated with the teacher's grading history.
**Type:** N

**ID:** TC_LGH_02
**Test condition:** File `grading_history.txt` exists but contains no data for the teacher.
**Steps:**
1. Create a temporary `grading_history.txt` file with grading history entries for other teachers.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadGradingHistory` method.
4. Access the `historyTableModel`.
**Expected Result:** The `historyTableModel` remains empty.
**Type:** N

**ID:** TC_LGH_03
**Test condition:** File `grading_history.txt` is empty.
**Steps:**
1. Create an empty temporary `grading_history.txt` file.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadGradingHistory` method.
4. Access the `historyTableModel`.
**Expected Result:** The `historyTableModel` remains empty.
**Type:** A

**ID:** TC_LGH_04
**Test condition:** File `grading_history.txt` does not exist.
**Steps:**
1. Ensure no `grading_history.txt` file exists in the test directory.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadGradingHistory` method.
4. Access the `historyTableModel`.
**Expected Result:** The `historyTableModel` remains empty.
**Type:** B
