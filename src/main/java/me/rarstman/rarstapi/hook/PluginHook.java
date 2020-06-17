package me.rarstman.rarstapi.hook;

public abstract class PluginHook {

    private final String name;
    private boolean hooked;

    public PluginHook(final String name){
        this.name = name;
        this.hooked = false;
    }

    public String getName(){
        return this.name;
    }

    public boolean isHooked(){
        return this.hooked;
    }

    public PluginHook setHooked(final boolean hooked){
        this.hooked = hooked;
        return this;
    }

    public <A> A getHookInstance(final Class<A> returnType){
        return (A) this;
    }

    public abstract PluginHook initialize();

}
