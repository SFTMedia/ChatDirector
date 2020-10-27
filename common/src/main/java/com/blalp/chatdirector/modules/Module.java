package com.blalp.chatdirector.modules;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.internalModules.format.IFormatter;
import com.blalp.chatdirector.model.Loadable;

public abstract class Module extends Loadable implements IModule {
    public IFormatter getFormatter(){
        return ChatDirector.formatter;
    }
}