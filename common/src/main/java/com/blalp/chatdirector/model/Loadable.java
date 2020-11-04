package com.blalp.chatdirector.model;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.Configuration;

public abstract class Loadable implements ILoadable {
    public abstract void load();

    public abstract void unload();

    public void reload() {
        if(Configuration.debug){
            System.out.println("Reloading "+getClass().getCanonicalName()+" "+this);
        }
        unload();
        if(Configuration.debug){
            System.out.println("Unloaded "+getClass().getCanonicalName()+" "+this);
        }
        load();
        if(Configuration.debug){
            System.out.println("Reloaded "+getClass().getCanonicalName()+" "+this);
        }
    }
}