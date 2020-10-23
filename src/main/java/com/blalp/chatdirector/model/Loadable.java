package com.blalp.chatdirector.model;

public abstract class Loadable implements ILoadable {
    public abstract void load();

    public abstract void unload();

    public void reload() {
        unload();
        load();
    }
}