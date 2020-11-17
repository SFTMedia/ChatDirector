package com.blalp.chatdirector.modules;

import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.ILoadable;
import com.blalp.chatdirector.model.IValid;

public interface IModule extends ILoadable, IValid {
    public List<String> getItemNames();

    public Class<?> getItemClass(String type);

    public Context getContext(Object object);
}