package com.blalp.chatdirector.model;

import java.util.List;

public interface IModule extends ILoadable, IValid {
    public List<String> getItemNames();

    public Class<?> getItemClass(String type);

    public Context getContext(Object object);
}