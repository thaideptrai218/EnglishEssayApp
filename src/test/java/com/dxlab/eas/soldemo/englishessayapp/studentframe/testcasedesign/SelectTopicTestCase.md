# Unit Test Case: StudentFrame - selectTopic()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_ST_01** | **Normal Path - First Topic Selected** | The first row is selected in a populated `topicTable`. | The `selectedTopicId` field is updated to "T1". The dialog manager is asked to show the "Selected topic" message. | None | N/A | The `selectedTopicId` is correctly updated and the user is notified. | N |
| **TC_ST_02** | **Boundary - Last Topic Selected** | The last row is selected in a populated `topicTable`. | The `selectedTopicId` field is updated to "T2". The dialog manager is asked to show the "Selected topic" message. | None | N/A | The selection logic works at the boundary of the dataset. | B |
| **TC_ST_03** | **Abnormal - No Topic Selected** | The `topicTable` is populated, but no row is selected. | The `selectedTopicId` field remains null. The dialog manager is asked to show the "Please select a topic!" error. | None | N/A | The application correctly handles the case where no topic is selected. | A |
| **TC_ST_04** | **Boundary - Empty Table** | The `topicTable` contains no rows. | The `selectedTopicId` field remains null. The dialog manager is asked to show the "Please select a topic!" error. | None | N/A | The method handles an empty table without error. | B |

---
### **Notes on Testability**
The `selectTopic` method was refactored to use a `DialogManager` interface, following the Dependency Injection pattern. This decouples the method from the static `JOptionPane` class, allowing tests to provide a `MockDialogManager`. This enables fully automated testing of the dialog messages without creating blocking UI pop-ups, significantly improving the quality and reliability of the test suite.
