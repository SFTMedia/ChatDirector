package com.blalp.chatdirector.core.model;

import java.util.List;

public interface IModule extends ILoadable, IValid {
    public List<String> getItemNames();

    public Class<? extends IItem> getItemClass(String type);

    public Context getContext(Object object);
}