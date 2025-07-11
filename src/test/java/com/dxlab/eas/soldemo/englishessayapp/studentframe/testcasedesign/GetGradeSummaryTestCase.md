**ID:** TC_GGS_01
**Test condition:** File `graded.txt` exists and contains a grade for the essay.
**Steps:**
1. Create a temporary `graded.txt` file with a grade for the essay.
2. Instantiate `StudentFrame`.
3. Invoke the `getGradeSummary` method with the essay ID.
**Expected Result:** The method returns the grade summary string.
**Type:** N

**ID:** TC_GGS_02
**Test condition:** File `graded.txt` exists but does not contain a grade for the essay.
**Steps:**
1. Create a temporary `graded.txt` file without a grade for the essay.
2. Instantiate `StudentFrame`.
3. Invoke the `getGradeSummary` method with the essay ID.
**Expected Result:** The method returns "Not graded".
**Type:** N

**ID:** TC_GGS_03
**Test condition:** File `graded.txt` is empty.
**Steps:**
1. Create an empty temporary `graded.txt` file.
2. Instantiate `StudentFrame`.
3. Invoke the `getGradeSummary` method with the essay ID.
**Expected Result:** The method returns "Not graded".
**Type:** A

**ID:** TC_GGS_04
**Test condition:** File `graded.txt` does not exist.
**Steps:**
1. Ensure no `graded.txt` file exists in the test directory.
2. Instantiate `StudentFrame`.
3. Invoke the `getGradeSummary` method with the essay ID.
**Expected Result:** The method returns "Not graded".
**Type:** B
