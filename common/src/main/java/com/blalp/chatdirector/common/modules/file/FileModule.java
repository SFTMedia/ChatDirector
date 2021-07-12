package com.blalp.chatdirector.common.modules.file;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class FileModule implements IModule {

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
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
    public Class<? extends IItem> getItemClass(String type) {
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
