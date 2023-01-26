package Task;

import java.util.Objects;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(int id, String name, String description, TaskStatus status) {
        this(id, name, description, status, null);
    }

    public Subtask(Epic epic, String name, String description, TaskStatus status) {
        this(null, name, description, status, epic);
    }

    private Subtask(Integer id, String name, String description, TaskStatus status, Epic epic) {
        super(id, name, description, status);
        this.epic = epic;
        this.taskType = TaskType.SUBTASK;
    }

    public Epic getEpic() {
        return this.epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String serialize() {
        return super.serialize() + "," + epic.getId();
    }

    public static Subtask deserialize(String value) {
        Task task = Task.deserialize(value);
        return new Subtask(task.id, task.name, task.description, task.status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(epic.name, subtask.epic.name)
                && Objects.equals(epic.description, subtask.epic.description)
                && Objects.equals(epic.id, subtask.epic.id)
                && Objects.equals(epic.status, subtask.epic.status);
    }

    @Override
    public String toString() {
        return "Task.Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", Epic{" +
                "id=" + epic.id +
                ", name='" + epic.name + '\'' +
                ", description='" + epic.description + '\'' +
                ", status=" + epic.status +
                '}' +
                '}';
    }
}
