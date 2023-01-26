package Task;

import java.util.HashMap;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Integer id;
    protected TaskStatus status;
    protected TaskType taskType;

    public Task(String name, String description, TaskStatus status) {
        this(null, name, description, status);
    }

    public Task(Integer id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = TaskType.TASK;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String serialize() {
        return String.format("%s,%s,%s,%s,%s", this.id, this.getTaskType(), this.name, this.status, this.description);
    }

    public static Task deserialize(String value) {
        String[] args = value.split(",");
        int id = Integer.parseInt(args[0]);
        String name = args[2];
        TaskStatus status = TaskStatus.valueOf(args[3]);
        String description = args[4];

        return new Task(id, name, description, status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return this.id == task.id
                && Objects.equals(this.name, task.name)
                && Objects.equals(this.description, task.description)
                && Objects.equals(this.status, task.status);
    }

    @Override
    public String toString() {
        return "Task.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}