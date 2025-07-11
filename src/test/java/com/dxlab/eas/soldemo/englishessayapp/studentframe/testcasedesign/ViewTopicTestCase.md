**ID:** TC_VT_01
**Test condition:** A topic is selected from the table.
**Steps:**
1. Instantiate `StudentFrame`.
2. Populate the `topicTableModel` with some data.
3. Select a row in the `topicTable`.
4. Invoke the `viewTopic` method.
**Expected Result:** A message dialog is shown with the topic description.
**Type:** N

**ID:** TC_VT_02
**Test condition:** No topic is selected from the table.
**Steps:**
1. Instantiate `StudentFrame`.
2. Invoke the `viewTopic` method without selecting a row.
**Expected Result:** A message dialog is shown asking the user to select a topic.
**Type:** A
