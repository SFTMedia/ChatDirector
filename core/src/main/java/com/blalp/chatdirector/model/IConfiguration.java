package com.blalp.chatdirector.model;

import java.util.List;
import java.util.Map;

import com.blalp.chatdirector.configuration.Chain;

public interface IConfiguration extends ILoadable {
    public Class<?> getModuleClass(String moduleType);

    public Class<?> getItemClass(String itemType);
    public List<IModule> getModules();
    public Map<String, Chain> getChains();

}
