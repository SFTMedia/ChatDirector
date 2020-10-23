package com.blalp.chatdirector.modules.file;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class FileModule extends Module {

    @Override
    public void load() {
        FileInputDaemon.instance.load();
    }

    @Override
    public void unload() {
        FileInputDaemon.instance.unload();
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"FileInput","FileOutput"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "FileInput":
                FileInputItem item = new FileInputItem();
                FileInputDaemon.instance.addItem(item);
                return item;
            case "FileOutput":
                return new FileOutputItem();
        }
        return null;
    }
    
}
