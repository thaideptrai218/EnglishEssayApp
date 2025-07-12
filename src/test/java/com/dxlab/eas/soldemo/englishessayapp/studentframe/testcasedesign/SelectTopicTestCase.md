# Unit Test Case: StudentFrame - selectTopic()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_ST_01** | **Normal Path - First Topic Selected** | The first row is selected in a populated `topicTable`. | The `selectedTopicId` field is updated with the ID of the first topic ("T1"). | None | N/A | The `selectedTopicId` is correctly updated. | N |
| **TC_ST_02** | **Boundary - Last Topic Selected** | The last row is selected in a populated `topicTable`. | The `selectedTopicId` field is updated with the ID of the last topic ("T2"). | None | N/A | The selection logic works at the boundary of the dataset. | B |
| **TC_ST_03** | **Abnormal - No Topic Selected** | The `topicTable` is populated, but no row is selected. | The `selectedTopicId` field remains null. | None | N/A | The application correctly handles the case where no topic is selected. | A |
| **TC_ST_04** | **Boundary - Empty Table** | The `topicTable` contains no rows. | The `selectedTopicId` field remains null. | None | N/A | The method handles an empty table without error. | B |

---
### **Notes on Testability**
The `selectTopic` method invokes a `JOptionPane`, which is a blocking UI dialog. Verifying the appearance and content of this dialog is outside the scope of standard JUnit unit testing and would require a dedicated UI testing framework (e.g., AssertJ-Swing). Therefore, these test cases focus on verifying the application's state (`selectedTopicId`) and ensuring no exceptions are thrown before and after the UI call is made.
