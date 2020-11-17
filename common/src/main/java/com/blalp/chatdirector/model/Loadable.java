package com.blalp.chatdirector.model;

import com.blalp.chatdirector.ChatDirector;

public abstract class Loadable implements ILoadable {
    public abstract void load();

    public abstract void unload();

    public void reload() {
        ChatDirector.logDebug("Reloading " + getClass().getCanonicalName() + " " + this);
        unload();
        ChatDirector.logDebug("Unloaded " + getClass().getCanonicalName() + " " + this);
        load();
        ChatDirector.logDebug("Reloaded " + getClass().getCanonicalName() + " " + this);
    }
}