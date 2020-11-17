package com.blalp.chatdirector.model;

public interface IConfiguration extends ILoadable {
    public Class<?> getModuleClass(String moduleType);
    public Class<?> getItemClass(String itemType);
    
}
