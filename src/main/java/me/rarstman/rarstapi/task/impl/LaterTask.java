package me.rarstman.rarstapi.task.impl;

import me.rarstman.rarstapi.task.TaskProvider;

public abstract class LaterTask extends TaskProvider {

    private final long delay;

    public LaterTask(final long delay) {
        this.delay = delay;
    }

    @Override
    public TaskProvider register() {
        this.runTaskLater(this.rarstAPIPlugin, this.delay);
        this.logger.info("Registered task '" + this.getTaskId() + "' ('" + this.getClass().getCanonicalName() + "').");
        return this;
    }

    @Override
    public void run() {
        this.checkAndRun();
    }

}
