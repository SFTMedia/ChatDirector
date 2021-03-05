package com.blalp.chatdirector.model;

import java.util.Map;

import com.blalp.chatdirector.configuration.Chain;

public interface IConfiguration extends ILoadable, IValid {
    public Class<?> getItemClass(String itemType);

    public Class<?> getItemClass(String itemType, Iterable<IModule> modules);

    public Map<String, Chain> getChains();

    public IModule getModule(Class<? extends IModule> class1);

    public IDaemon getOrCreateDaemon(Class<? extends IDaemon> class1);

    public boolean isDebug();
    
    public boolean isTesting();
}
