# Unit Test Case: StudentFrame - viewTopic()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_VT_01** | **Normal Path - Topic Selected** | A row is selected in the `topicTable`. | The dialog manager is asked to show a dialog containing the correct topic description ("Test Topic 1"). | None | N/A | The method correctly displays the topic description via the dialog manager. | N |
| **TC_VT_02** | **Abnormal - No Topic Selected** | No row is selected in the `topicTable`. | The dialog manager is asked to show the "Please select a topic!" error. | None | N/A | The application correctly handles the case where no topic is selected. | A |
| **TC_VT_03** | **Boundary - Empty Table** | The `topicTable` contains no rows. | The dialog manager is asked to show the "Please select a topic!" error. | None | N/A | The method handles an empty table without error. | B |

---
### **Notes on Testability**
The `viewTopic` method was refactored to use a `DialogManager` interface, following the Dependency Injection pattern. This decouples the method from the static `JOptionPane` class, allowing tests to provide a `MockDialogManager`. This enables fully automated testing of the dialog messages without creating blocking UI pop-ups, significantly improving the quality and reliability of the test suite.
