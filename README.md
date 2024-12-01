# ToDo List with Compose

This project is a simple **ToDo List** application developed using **Jetpack Compose**. The app provides basic functionalities such as adding tasks, deleting tasks, and managing the task list. It is structured using modern Android development techniques and the MVVM architecture.

## Features

- **Add Task:** Users can add new tasks through the app.
- **Delete Task:** Users can delete completed tasks.
- **Jetpack Compose:** UI components are built using Jetpack Compose.
- **MVVM Architecture:** The app is structured using **Model-View-ViewModel (MVVM)** architecture.
- **Testing:** Both **UI** and **ViewModel** tests are written to ensure the correctness of the app.

## Technologies

- **Kotlin**
- **Jetpack Compose**
- **MVVM Architecture**
- **Room (Database)**
- **Koin** (Dependency Injection)
- **Mockito** (For testing)
- **JUnit**
- **Coroutines** (For asynchronous operations)

## Setup

Follow these steps to run the project locally:

1. Clone the repository:

    ```bash
    git clone https://github.com/remziakgoz/todolistwithcompose.git
    ```

2. Open the project in Android Studio.
3. Wait for the **Gradle** files to sync.
4. Run the app on a physical device or emulator.

## Usage

1. When the app opens, tap the **FAB (Floating Action Button)** at the bottom right.
2. Enter a task name in the "Enter ToDo" field.
3. Tap the "Add" button to add the task to the list.
4. The added tasks will be displayed in the list.
5. You can delete completed tasks by updating the list.

## Tests

This project includes two main types of tests:

### UI Tests
- **AddItemScreenTest:** Verifies that the add task button works as expected.
- **AddItemScreenTest:** Verifies that no task is saved when the input is empty.

### ViewModel Tests
- **ItemViewModelTest:** Ensures that the `saveItem()` and `deleteItem()` functions work correctly in the repository.

## Running Tests

To run the tests, follow these steps:

1. In Android Studio, click on **Run** in the menu and select **Test**.
2. To verify the tests run successfully, you can use the following command in the terminal:

    ```bash
    ./gradlew test
    ```

## Contributing

If you would like to contribute, please follow these steps:

1. Fork this repository.
2. Create a new feature branch.
3. Commit your changes.
4. Create a pull request.

## License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

This app, developed by **Remzi Akgöz**, provides a simple and effective example for anyone interested in building modern Android applications using Jetpack Compose.
