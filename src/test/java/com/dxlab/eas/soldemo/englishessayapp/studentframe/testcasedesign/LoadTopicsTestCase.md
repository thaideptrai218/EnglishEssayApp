# Unit Test Case: StudentFrame - loadTopics()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LT_01** | **Normal Path - File Exists** | `topics.txt` exists and contains valid topic entries. | The `topicTableModel` should be populated with the data from the file. | None | N/A | Topics are loaded correctly from the file. | N |
| **TC_LT_02** | **Boundary - Empty File** | `topics.txt` exists but is empty. | The `topicTableModel` should be empty. | None | N/A | The application handles an empty file without errors. | A |
| **TC_LT_03** | **Boundary - Non-Existent File** | `topics.txt` does not exist. | The `topicTableModel` should be populated with the 3 default topics. | None | N/A | The application correctly falls back to loading default topics. | B |
| **TC_LT_04** | **Abnormal - Malformed Data** | `topics.txt` contains a mix of valid and malformed lines. | The `topicTableModel` should contain only the valid topics. | None | N/A | The application ignores corrupted lines and does not crash. | A |
