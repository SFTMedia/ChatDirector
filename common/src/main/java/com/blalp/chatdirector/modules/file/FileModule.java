package com.blalp.chatdirector.modules.file;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "file-input":
                if (FileInputDaemon.instance == null) {
                    new FileInputDaemon();
                }
                FileInputItem item = om.convertValue(config, FileInputItem.class);
                FileInputDaemon.instance.addItem(item,chain);
                return item;
            case "file-output":
                return om.convertValue(config, FileOutputItem.class);
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
}
