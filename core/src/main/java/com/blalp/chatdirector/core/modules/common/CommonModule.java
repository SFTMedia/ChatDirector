package com.blalp.chatdirector.core.modules.common;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class CommonModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("stop", "halt", "break", "echo", "reload");
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "halt":
            return HaltItem.class;
        case "break":
            return BreakItem.class;
        case "echo":
            return EchoItem.class;
        case "reload":
            return ReloadItem.class;
        default:
            return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
