# 6-java-core

This is part of the Java Developer course from Yandex. Module - standard Java library and its features.

The repository contains my solution that has been verified from the reviewer to the task described below.

Technical specification
===================

In this sprint, you will add another useful option to the task tracker. The current implementation stores the state of the manager in RAM, because of this, after restarting the application, all the data we need is lost. This problem can be solved by a manager class that will automatically save all tasks and their status to a special file after each operation.

You have to create a second implementation of the manager. It will have the same system of classes and interfaces as the current one. The new and old managers will differ only in the implementation details of the methods: one stores information in RAM, the other in a file.

The second implementation of the manager
---------------------------

So, create the `FileBackedTasksManager' class. In it, you will prescribe the logic of auto-saving to a file. This class, like 'InMemoryTasksManager', should implement the interface of the `TasksManager` manager.

![image](https://pictures.s3.yandex.net:443/resources/S5_21_1644488175.png)

Now you need to write an implementation for the new class. If you want to just copy the code from `InMemoryTasksManager` and add it in the right places with the save to file function, stop! Try to avoid duplicating the code, this is a sign of bad style.

In this case, there is a more elegant solution: you can inherit `FileBackedTasksManager` from `InMemoryTasksManager` and get the desired manager logic from the parent class. It remains only to add calls to the autosave method in some places.

![image](https://pictures.s3.yandex.net:443/resources/S5_22_1644488199.png)

Autosave method
--------------------

Let the new manager get an autosave file in his constructor and save it in the field. Create a `save` method without parameters — it will save the current state of the manager to the specified file.

Now it is enough to redefine each modifying operation in such a way that the version inherited from the ancestor is executed first, and then the `save` method. For example:

Copy the code

@Override
public void addSubtask(Subtask subtask) {
super.addSubtask(subtask);
save();
}


Then you need to think through the logic of the `save` method. What should it save? All tasks, subtasks, epics and the history of viewing any tasks. For convenience, we recommend choosing the CSV text format (English _Comma-Separated Values_, "comma-separated values"). Then the file with the saved data will look like this:

Copy the code JSH

id,type,name,status,description,epic
1,TASK,Task1,NEW,Description task1,
2,EPIC,Epic2,DONE,Description epic2,
3,SUBTASK,Sub Task2,DONE,Description sub task3,2

2,3

First, all the task fields are listed comma-separated. Below is a list of tasks, each of them is written from a new line. Then there is an empty line that separates the tasks from the browsing history. And the final line is the task IDs from the browsing history.

The file from our example can be read as follows: a task, an epic and a subtask have been added to the tracker. The epic and subtask have been reviewed and completed. The task remained in the new state and was not viewed.

Checking the work of a new manager
--------------------------------

Exceptions of the type `IOException` need to be caught inside the `save` method and throw your own unchecked exception `ManagerSaveException'. Thanks to this, you can not change the signature of the methods of the manager interface.

We assume that our manager works in ideal conditions. Invalid operations are not performed on it, and all its actions with the environment (for example, saving a file) are completed successfully.

In addition to the save method, create a static method `static FileBackedTasksManager` `LoadFromFile(File file)`, which will restore manager data from the file when the program starts. Don't forget to make sure that the new task manager works the same as the previous one. And check the work of saving and restoring the manager from the file (serialization).

To do this, create the `static void main(String[] args) method` in the `FileBackedTasksManager` class and implement a small script:

1. Create several different tasks, epics and subtasks.
2. Request some of them to fill the browsing history.
3. Create a new `FileBackedTasksManager' manager from the same file.
4. Check that the browsing history has been restored correctly and all the tasks, epics, subtasks that were in the old one are in the new manager.

Result
----

You should have several new classes, as well as a new manager with a save state option. Make sure it works correctly and submit your code for review.
