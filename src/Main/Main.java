package Main;

import Manager.*;
import Task.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        addTasks(manager);

        //Test #1
        manager.getTaskById(0);
        manager.getTaskById(1);
        manager.getEpicById(2);
        manager.getEpicById(3);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(6);

        List<Integer> result = manager.getHistory().stream().map(x -> x.getId()).collect(Collectors.toList());

        System.out.println(result.equals(List.of(6, 5, 4, 3, 2, 1, 0)));


        //Test #2
        manager.getSubtaskById(6);
        manager.getSubtaskById(5);
        manager.getSubtaskById(4);
        manager.getEpicById(3);
        manager.getEpicById(2);
        manager.getTaskById(1);
        manager.getTaskById(0);

        result = manager.getHistory().stream().map(x -> x.getId()).collect(Collectors.toList());

        System.out.println(result.equals(List.of(0, 1, 2, 3, 4, 5, 6)));


        //Test #3
        manager.getEpicById(3);
        manager.getTaskById(0);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
        manager.getTaskById(1);
        manager.getEpicById(2);
        manager.getSubtaskById(6);

        result = manager.getHistory().stream().map(x -> x.getId()).collect(Collectors.toList());

        System.out.println(result.equals(List.of(6, 2, 1, 5, 4, 0, 3)));


        //Test #4
        manager.getTaskById(0);

        result = manager.getHistory().stream().map(x -> x.getId()).collect(Collectors.toList());

        System.out.println(result.equals(List.of(0, 6, 2, 1, 5, 4, 3)));


        //Test #5
        manager.deleteTaskById(1);

        result = manager.getHistory().stream().map(x -> x.getId()).collect(Collectors.toList());

        System.out.println(result.equals(List.of(0, 6, 2, 5, 4, 3)));


        //Test #5
        manager.deleteEpicById(2);

        result = manager.getHistory().stream().map(x -> x.getId()).collect(Collectors.toList());

        System.out.println(result.equals(List.of(0, 3)));
    }

    public static void addTasks(TaskManager manager) {
        Task task1 = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);

        Epic epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание 2", TaskStatus.NEW);

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpic(epic1);
        manager.createEpic(epic2);

        manager.createSubtask(new Subtask(epic1, "Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW));
        manager.createSubtask(new Subtask(epic1, "Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW));
        manager.createSubtask(new Subtask(epic1, "Подзадача 3", "Описание подзадачи 3", TaskStatus.NEW));
    }
}