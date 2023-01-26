package Task;

import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subtasks;

    public Epic(Integer id, String name, String description, TaskStatus status) {
        super(id, name, description, status);
        this.taskType = TaskType.EPIC;
        this.subtasks = new HashMap<>();
    }

    public Epic(String name, String description, TaskStatus status) {
        this(null, name, description, status);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return this.subtasks;
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        updateStatus();
    }

    public void deleteSubtaskById(int id) {
        if (!subtasks.containsKey(id)) {
            throw new RuntimeException();
        } else {
            subtasks.remove(id);
            updateStatus();
        }
    }

    public void addSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId()) || subtask.getId() == null) {
            throw new RuntimeException();
        } else {
            subtasks.put(subtask.id, subtask);
            subtask.setEpic(this);
            updateStatus();
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            throw new RuntimeException();
        } else {
            subtasks.put(subtask.id, subtask);
            subtask.setEpic(this);
            updateStatus();
        }
    }

    private void updateStatus() {
        boolean isNew = subtasks.values().stream().filter(i -> i.status == TaskStatus.NEW).toArray()
                .length == subtasks.size();
        boolean isDone = subtasks.values().stream().filter(i -> i.status == TaskStatus.DONE).toArray()
                .length == subtasks.size();

        if (isNew) {
            this.status = TaskStatus.NEW;
        } else if (isDone) {
            this.status = TaskStatus.DONE;
        } else {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }

    public static Epic deserialize(String value) {
        Task task = Task.deserialize(value);
        return new Epic(task.id, task.name, task.description, task.status);
    }

    @Override
    public void setStatus(TaskStatus status) {
        updateStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public String toString() {
        return "Task.Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtasks=" + subtasks +
                '}';
    }
}
