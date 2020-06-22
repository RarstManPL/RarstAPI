package me.rarstman.rarstapi.task.impl;

import me.rarstman.rarstapi.task.TaskProvider;

public abstract class TimerTask extends TaskProvider {

    private final long delay;
    private final long period;

    public TimerTask(final long delay, final long period) {
        this.delay = delay;
        this.period = period;
    }

    @Override
    public TaskProvider register() {
        this.runTaskTimer(this.rarstAPIPlugin, this.delay, this.period);
        this.logger.info("Registered task '" + this.getTaskId() + "' ('" + this.getClass().getCanonicalName() + "').");
        return this;
    }

    @Override
    public void run() {
        this.checkAndRun();
    }
}
