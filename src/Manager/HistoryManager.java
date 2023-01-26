package Manager;

import Task.Task;

import java.util.*;
import java.util.stream.Collectors;

public interface HistoryManager {
    void add(Task task);

    void remove(int id);

    List<Task> getHistory();

    static String serialize(HistoryManager historyManager) {
        List<Task> history = historyManager.getHistory();
        Collections.reverse(history);
        return history.stream()
                .map(x -> x.getId().toString())
                .collect(Collectors.joining(","));
    }

    static List<Integer> deserialize(String value) {
        return Arrays.asList(value.split(",")).stream()
                .map(x -> Integer.parseInt(x))
                .collect(Collectors.toList());
    }
}
