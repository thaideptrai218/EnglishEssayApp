**ID:** TC_ST_01
**Test condition:** A topic is selected from the table.
**Steps:**
1. Instantiate `StudentFrame`.
2. Populate the `topicTableModel` with some data.
3. Select a row in the `topicTable`.
4. Invoke the `selectTopic` method.
5. Access the `selectedTopicId` field.
**Expected Result:** The `selectedTopicId` field is updated with the ID of the selected topic.
**Type:** N

**ID:** TC_ST_02
**Test condition:** No topic is selected from the table.
**Steps:**
1. Instantiate `StudentFrame`.
2. Invoke the `selectTopic` method without selecting a row.
3. Access the `selectedTopicId` field.
**Expected Result:** The `selectedTopicId` field remains null. A message dialog is shown.
**Type:** A
