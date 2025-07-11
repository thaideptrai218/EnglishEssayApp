**ID:** TC_LGE_01
**Test condition:** File `graded.txt` exists and contains graded essays.
**Steps:**
1. Create a temporary `graded.txt` file with graded essay entries.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadGradedEssays` method.
4. Access the `gradedTableModel`.
**Expected Result:** The `gradedTableModel` is populated with the graded essays.
**Type:** N

**ID:** TC_LGE_02
**Test condition:** File `graded.txt` is empty.
**Steps:**
1. Create an empty temporary `graded.txt` file.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadGradedEssays` method.
4. Access the `gradedTableModel`.
**Expected Result:** The `gradedTableModel` remains empty.
**Type:** A

**ID:** TC_LGE_03
**Test condition:** File `graded.txt` does not exist.
**Steps:**
1. Ensure no `graded.txt` file exists in the test directory.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadGradedEssays` method.
4. Access the `gradedTableModel`.
**Expected Result:** The `gradedTableModel` remains empty.
**Type:** B
