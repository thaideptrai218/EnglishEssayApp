# EnglishEssayApp

[![Java CI with Maven](https://github.com/thaideptrai218/EnglishEssayApp/actions/workflows/build.yml/badge.svg)](https://github.com/thaideptrai218/EnglishEssayApp/actions/workflows/build.yml)

**EnglishEssayApp** is a desktop application built with Java Swing, designed to provide a focused environment for writing and analyzing English essays. It serves as a foundational tool with a clean user interface, aimed at helping users improve their writing by providing basic analytical feedback.

---

## ✨ Features

This application provides the following core features:

-   **📝 Rich Text Editor:** A central text area for composing and editing essays.
-   **📊 Real-time Analysis:** Instant feedback on word and character counts.
-   **💾 File Operations:** Easily open, save, and create new essay files.
-   **🖥️ Cross-Platform:** Runs on any operating system with a compatible Java installation.

_(This is a foundational project. Future features could include grammar checking, readability scores, and more advanced text analysis.)_

## 📸 Screenshots

_(Add screenshots of your application here to give users a visual preview.)_

## 🛠️ Technology Stack

-   **Core:** Java 17
-   **UI:** Java Swing (part of the standard library)
-   **Build & Dependencies:** Apache Maven
-   **Testing:** JUnit 5
-   **Continuous Integration:** GitHub Actions

## Table of Contents

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need the following software installed on your system:

-   ✅ **Java Development Kit (JDK) 17** or later.
-   ✅ **Apache Maven** to manage project dependencies and the build lifecycle.

### Building the Project

1.  **Clone the repository:**

    ```sh
    git clone https://github.com/YOUR_USERNAME/EnglishEssayApp.git
    cd EnglishEssayApp
    ```

    > **Note:** Remember to replace `YOUR_USERNAME` with your GitHub username or organization.

2.  **Compile, Test, and Package:**
    Use the Maven command to compile the source code, run all tests, and package the application into an executable JAR file. This single command ensures the project is in a good state.
    ```sh
    mvn verify
    ```
    This command will create a `target/` directory containing the final JAR: `EnglishEssayApp-1.0.jar`.

## ▶️ Usage

### How to Run the Application

Once the project has been successfully packaged, you can run the application from the command line:

```sh
java -jar target/EnglishEssayApp-1.0.jar
```

### Running Tests

To execute the unit tests for the project, run the following Maven command:

```sh
mvn test
```

## Continuous Integration

This project uses GitHub Actions for Continuous Integration. The CI pipeline is configured to automatically build and test the project on every push and pull request to the `main` branch. You can view the status of the builds via the badge at the top of this README.
