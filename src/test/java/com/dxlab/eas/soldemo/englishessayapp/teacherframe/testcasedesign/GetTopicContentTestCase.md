**ID:** TC_GTC_01
**Test condition:** File `topics.txt` exists and contains the topic.
**Steps:**
1. Create a temporary `topics.txt` file with the topic.
2. Instantiate `TeacherFrame`.
3. Invoke the `getTopicContent` method with the topic ID.
**Expected Result:** The method returns the topic content string.
**Type:** N

**ID:** TC_GTC_02
**Test condition:** File `topics.txt` exists but does not contain the topic.
**Steps:**
1. Create a temporary `topics.txt` file without the topic.
2. Instantiate `TeacherFrame`.
3. Invoke the `getTopicContent` method with the topic ID.
**Expected Result:** The method returns "Unknown topic".
**Type:** N

**ID:** TC_GTC_03
**Test condition:** File `topics.txt` is empty.
**Steps:**
1. Create an empty temporary `topics.txt` file.
2. Instantiate `TeacherFrame`.
3. Invoke the `getTopicContent` method with the topic ID.
**Expected Result:** The method returns "Unknown topic".
**Type:** A

**ID:** TC_GTC_04
**Test condition:** File `topics.txt` does not exist.
**Steps:**
1. Ensure no `topics.txt` file exists in the test directory.
2. Instantiate `TeacherFrame`.
3. Invoke the `getTopicContent` method with the topic ID.
**Expected Result:** The method returns "Unknown topic".
**Type:** B
