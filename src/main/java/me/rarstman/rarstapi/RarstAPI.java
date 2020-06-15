package me.rarstman.rarstapi;

import org.bukkit.plugin.java.JavaPlugin;

public class RarstAPI extends JavaPlugin {

    private JavaPlugin javaPlugin;

    @Override
    public void onLoad(){
        this.javaPlugin = this;
    }

    public void setJavaPlugin(final JavaPlugin javaPlugin){
        this.javaPlugin = javaPlugin;
    }

}
