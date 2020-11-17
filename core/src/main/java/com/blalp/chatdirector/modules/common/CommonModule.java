package com.blalp.chatdirector.modules.common;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IModule;

public class CommonModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("pass", "stop", "halt", "break", "echo", "reload");
    }

    @Override
    public Class<?> getItemClass(String type) {
        switch (type) {
            case "stop":
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
    public void load() {
    }

    @Override
    public void unload() {
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
