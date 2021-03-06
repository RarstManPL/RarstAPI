package me.rarstman.rarstapi.task;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.logger.Logger;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class TaskProvider extends BukkitRunnable {

    protected final RarstAPIPlugin rarstAPIPlugin;
    protected final Logger logger;
    private boolean once = false;
    private boolean disabled = false;

    public TaskProvider() {
        this.rarstAPIPlugin = RarstAPIPlugin.getAPI();
        this.logger = this.rarstAPIPlugin.getAPILogger();
    }

    public void disableTask() {
        this.cancel();
        this.onDisable();
        this.disabled = true;
        this.logger.info("Disabled task '" + this.getTaskId() + "'.");
    }

    protected void checkAndRun() {
        if(this.disabled) {
            return;
        }

        if(this.rarstAPIPlugin.isDisabling()) {
            this.disableTask();
            return;
        }
        this.onExecute();

        if(this.once) {
            this.disableTask();
        }
    }

    public TaskProvider once() {
        this.once = true;
        return this;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public abstract void onDisable();
    public abstract void onExecute();
    public abstract TaskProvider register();

}
