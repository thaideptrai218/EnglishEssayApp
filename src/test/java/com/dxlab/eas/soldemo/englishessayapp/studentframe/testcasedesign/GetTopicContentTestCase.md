# Unit Test Case: StudentFrame - getTopicContent()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_GTC_01** | **Normal Path - Topic Exists** | `topics.txt` contains a correctly formatted entry for the target `topicId`. | The method must return the exact topic description string. | None | N/A | The correct topic description is returned. | N |
| **TC_GTC_02** | **Normal Path - Topic Does Not Exist** | `topics.txt` contains other topics, but not the one being requested. | The method must return the string "Unknown topic". | None | N/A | The correct "Unknown topic" status is returned. | N |
| **TC_GTC_03** | **Boundary - Empty File** | `topics.txt` exists but is empty. | The method must return the string "Unknown topic". | None | N/A | The application handles an empty file without errors. | A |
| **TC_GTC_04** | **Boundary - Non-Existent File** | `topics.txt` does not exist. | The method must return the string "Unknown topic". | None | N/A | The application handles a missing file without crashing. | B |
| **TC_GTC_05** | **Abnormal - Malformed Data** | `topics.txt` contains a malformed line (e.g., missing separator). | The method must return the string "Unknown topic". | None | N/A | The application handles the parsing error gracefully. | A |
| **TC_GTC_06** | **Boundary - Last Entry in File** | `topics.txt` contains multiple entries, and the target topic is the last one. | The method must return the exact topic description string. | None | N/A | The file reading loop correctly processes the last line. | B |
