package com.blalp.chatdirector.model;

import java.util.Map;

import com.blalp.chatdirector.configuration.Chain;

public interface IConfiguration extends ILoadable {
    public Class<?> getItemClass(String itemType);

    public Class<?> getItemClass(String itemType, Iterable<IModule> modules);

    public Map<String, Chain> getChains();

}
