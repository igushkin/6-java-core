package Manager;

import Task.*;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    protected HistoryManager historyManager;

    private static int id;

    static {
        id = 0;
    }

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(this.tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(this.epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(this.epics.values().stream()
                .flatMap(i -> i.getSubtasks().values().stream())
                .collect(Collectors.toList())
        );
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        addToViewHistory(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        addToViewHistory(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = findSubtask(id);
        addToViewHistory(subtask);
        return subtask;
    }

    private Subtask findSubtask(int id) {
        Subtask subtask = getAllSubtasks().stream()
                .filter(i -> i.getId() == id)
                .findAny()
                .orElse(null);
        return subtask;
    }

    private void addToViewHistory(Task task) {
        historyManager.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        setIdIfNull(task);
        if (tasks.containsKey(task.getId())) {
            throw new RuntimeException();
        } else {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        setIdIfNull(epic);
        if (epics.containsKey(epic.getId())) {
            throw new RuntimeException();
        } else {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void createSubtask(Subtask subtask) {
        setIdIfNull(subtask);
        if (findSubtask(subtask.getId()) != null || subtask.getEpic() == null) {
            throw new RuntimeException();
        } else {
            subtask.getEpic().addSubtask(subtask);
        }
    }

    private void setIdIfNull(Task task) {
        if (task.getId() == null) {
            task.setId(getNewId());
        }
    }

    @Override
    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            throw new RuntimeException();
        } else {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (!epics.containsKey(newEpic.getId())) {
            throw new RuntimeException();
        } else {
            Epic epic = epics.get(newEpic.getId());
            epic.setName(newEpic.getName());
            epic.setDescription(newEpic.getDescription());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask.getEpic() == null) {
            throw new RuntimeException();
        } else {
            subtask.getEpic().updateSubtask(subtask);
        }
    }

    @Override
    public void deleteAllTasks() {
        ArrayList<Integer> ids = new ArrayList(tasks.keySet());
        for (int id : ids) {
            deleteTaskById(id);
        }
    }

    @Override
    public void deleteAllEpics() {
        deleteAllSubtasks();
        ArrayList<Integer> ids = new ArrayList(epics.keySet());
        for (int id : ids) {
            deleteEpicById(id);
        }
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            ArrayList<Integer> ids = new ArrayList(epic.getSubtasks().keySet());
            for (int id : ids) {
                deleteSubtaskById(id);
            }
        }
    }

    @Override
    public void deleteTaskById(int id) {
        if (!tasks.containsKey(id)) {
            throw new RuntimeException();
        } else {
            tasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        if (!epics.containsKey(id)) {
            throw new RuntimeException();
        } else {
            ArrayList<Integer> subtaskIds = new ArrayList<>(getSubtasksByEpicId(id).keySet());
            for (int subtaskId : subtaskIds) {
                deleteSubtaskById(subtaskId);
            }
            epics.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask taskToRemove = findSubtask(id);
        if (taskToRemove == null) {
            throw new RuntimeException();
        } else {
            Epic epic = taskToRemove.getEpic();
            epic.deleteSubtaskById(id);
            historyManager.remove(id);
        }
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasksByEpicId(int id) {
        return epics.get(id).getSubtasks();
    }

    private static int getNewId() {
        return id++;
    }
}
