package com.blalp.chatdirector.modules.file;

import java.util.LinkedHashMap;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class FileModule extends Module {

    @Override
    public void load() {
        if(FileInputDaemon.instance!=null){
            FileInputDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if(FileInputDaemon.instance!=null){
            FileInputDaemon.instance.unload();
        }
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"file-input","file-output"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        LinkedHashMap<String,Object> configMap = (LinkedHashMap<String,Object>)config;
        switch (type) {
            case "file-input":
                if(FileInputDaemon.instance==null){
                    new FileInputDaemon();
                }
                FileInputItem item = new FileInputItem((String)configMap.get("path"));
                if(configMap.containsKey("delay")) {
                    item.delay=(int)configMap.get("delay");
                }
                FileInputDaemon.instance.addItem(item);
                return item;
            case "file-output":
                FileOutputItem item2 = new FileOutputItem((String)configMap.get("path"));
                if(configMap.containsKey("delay")){
                    item2.delay=(int)configMap.get("delay");
                }
                return item2;
        }
        return null;
    }
    
}
