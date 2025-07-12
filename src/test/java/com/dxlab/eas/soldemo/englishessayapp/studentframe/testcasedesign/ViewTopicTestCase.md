# Unit Test Case: StudentFrame - viewTopic()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_VT_01** | **Normal Path - Topic Selected** | A row is selected in the `topicTable`. | The method runs without error. | None | N/A | The method executes without error when a topic is selected. | N |
| **TC_VT_02** | **Abnormal - No Topic Selected** | No row is selected in the `topicTable`. | The method runs without error. | None | N/A | The application correctly handles the case where no topic is selected. | A |
| **TC_VT_03** | **Boundary - Empty Table** | The `topicTable` contains no rows. | The method runs without error. | None | N/A | The method handles an empty table without error. | B |

---
### **Notes on Testability**
The `viewTopic` method's primary function is to display a `JOptionPane`, which is a blocking UI dialog. Verifying the appearance and content of this dialog is outside the scope of standard JUnit unit testing and would require a dedicated UI testing framework (e.g., AssertJ-Swing). Therefore, these test cases focus on ensuring the method can be called safely under different conditions without throwing an exception.
