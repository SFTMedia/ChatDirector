package com.blalp.chatdirector.modules.file;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IModule;

public class FileModule implements IModule {

    @Override
    public void load() {
        if (FileInputDaemon.instance != null) {
            FileInputDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if (FileInputDaemon.instance != null) {
            FileInputDaemon.instance.unload();
        }
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("file-input", "file-output");
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<?> getItemClass(String type) {
        switch (type) {
            case "file-input":
                return FileInputItem.class;
            case "file-output":
                return FileOutputItem.class;
            default:
                return null;
        }
    }
}
