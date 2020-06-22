package me.rarstman.rarstapi.task;

import java.util.Arrays;

public class TaskManager {

    public static void registerTask(final TaskProvider task){
        task.register();
    }

    public static void registerTasks(final TaskProvider... tasks) {
        Arrays.stream(tasks)
                .forEach(TaskManager::registerTask);
    }

}
