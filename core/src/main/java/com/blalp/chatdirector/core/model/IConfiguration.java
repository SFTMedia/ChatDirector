package com.blalp.chatdirector.core.model;

import java.util.Map;

import com.blalp.chatdirector.core.configuration.Chain;

public interface IConfiguration extends ILoadable, IValid {
    public Class<?> getItemClass(String itemType);

    public Class<?> getItemClass(String itemType, Iterable<IModule> modules);

    public Map<String, Chain> getChains();

    public IModule getModule(Class<? extends IModule> class1);

    public ILoadable getOrCreateDaemon(Class<? extends ILoadable> class1);

    public boolean isDebug();

    public boolean isTesting();
}
