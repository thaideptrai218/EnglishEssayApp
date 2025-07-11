**ID:** TC_LE_01
**Test condition:** File `submitted.txt` exists and contains essays.
**Steps:**
1. Create a temporary `submitted.txt` file with essay entries.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadEssays` method.
4. Access the `essayTableModel`.
**Expected Result:** The `essayTableModel` is populated with the essays.
**Type:** N

**ID:** TC_LE_02
**Test condition:** File `submitted.txt` exists and contains essays, but with filters applied.
**Steps:**
1. Create a temporary `submitted.txt` file with essay entries.
2. Instantiate `TeacherFrame`.
3. Set the student and topic filters.
4. Invoke the `loadEssays` method.
5. Access the `essayTableModel`.
**Expected Result:** The `essayTableModel` is populated with the filtered essays.
**Type:** N

**ID:** TC_LE_03
**Test condition:** File `submitted.txt` is empty.
**Steps:**
1. Create an empty temporary `submitted.txt` file.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadEssays` method.
4. Access the `essayTableModel`.
**Expected Result:** The `essayTableModel` remains empty.
**Type:** A

**ID:** TC_LE_04
**Test condition:** File `submitted.txt` does not exist.
**Steps:**
1. Ensure no `submitted.txt` file exists in the test directory.
2. Instantiate `TeacherFrame`.
3. Invoke the `loadEssays` method.
4. Access the `essayTableModel`.
**Expected Result:** The `essayTableModel` remains empty.
**Type:** B
