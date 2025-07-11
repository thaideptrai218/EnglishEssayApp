# Unit Test Case: StudentFrame - getGradeSummary()

| UTCID | Condition | Precondition | Confirm/Return | Exception | Log Message | Result | Type |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_GGS_01** | **Normal Path - Grade Exists** | `graded.txt` contains a correctly formatted grade entry for the target `essayId`. | The method must return the correctly formatted grade summary string (e.g., "Task: 8, Coherence: 7, Lexical: 8, Grammar: 7"). | None | N/A | The correct grade summary is returned for a graded essay. | N |
| **TC_GGS_02** | **Normal Path - Grade Does Not Exist** | `graded.txt` contains entries, but none match the target `essayId`. | The method must return the default string "Not graded". | None | N/A | The correct "Not graded" status is returned. | N |
| **TC_GGS_03** | **Boundary - Empty File** | `graded.txt` exists but is empty. | The method must return "Not graded". | None | N/A | The application handles an empty file without errors. | A |
| **TC_GGS_04** | **Boundary - Non-Existent File** | `graded.txt` does not exist. | The method must return "Not graded". | None | N/A | The application handles a missing file without crashing. | B |
| **TC_GGS_05** | **Abnormal - Malformed Data** | `graded.txt` contains a line for the target `essayId`, but the line is malformed with too few parts. | The method must return "Not graded". | None | N/A | The application handles the parsing error gracefully and does not crash. | A |
| **TC_GGS_06** | **Boundary - Last Entry in File** | `graded.txt` contains multiple entries, and the target grade is the last one. | The method must return the correct, formatted grade summary for the last essay. | None | N/A | The file reading loop correctly processes the last line of the file. | B |
