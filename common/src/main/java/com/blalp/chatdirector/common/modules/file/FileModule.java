package com.blalp.chatdirector.common.modules.file;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IModule;

public class FileModule implements IModule {

    @Override
    public boolean load() {
        if (FileInputDaemon.instance != null) {
            return FileInputDaemon.instance.load();
        }
        return true;
    }

    @Override
    public boolean unload() {
        if (FileInputDaemon.instance != null) {
            return FileInputDaemon.instance.unload();
        }
        return true;
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
