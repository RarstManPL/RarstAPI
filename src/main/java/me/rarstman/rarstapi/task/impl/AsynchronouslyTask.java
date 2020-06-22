package me.rarstman.rarstapi.task.impl;

import me.rarstman.rarstapi.task.TaskProvider;

public abstract class AsynchronouslyTask extends TaskProvider {

    public AsynchronouslyTask() {}

    @Override
    public TaskProvider register() {
        this.runTaskAsynchronously(this.rarstAPIPlugin);
        this.logger.info("Registered task '" + this.getTaskId() + "' ('" + this.getClass().getCanonicalName() + "').");
        return this;
    }

    @Override
    public void run() {
        while(!this.isCancelled()) {
            this.checkAndRun();
        }
    }

}
