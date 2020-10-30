package com.blalp.chatdirector.modules.multichat;

import java.util.LinkedHashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class MultiChatModule extends Module {

    @Override
    public void load() {
        ChatDirector.addFormatter(new MultiChatFormatter());
        if (MultiChatInputItemDaemon.instance != null) {
            MultiChatInputItemDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if (MultiChatInputItemDaemon.instance != null) {
            MultiChatInputItemDaemon.instance.unload();
        }
    }

    @Override
    public String[] getItemNames() {
        return new String[]{"multichat-input","multichat-context"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type) {
            case "multichat-input":
                LinkedHashMap<String,Object> configInput = ((LinkedHashMap<String,Object>)config);
                MultiChatInputItem itemInput = new MultiChatInputItem();
                if(configInput.containsKey("global")){
                    itemInput.global= (boolean) (configInput.get("global"));
                }
                if(configInput.containsKey("staff")){
                    itemInput.staff= (boolean) (configInput.get("staff"));
                }
                if(configInput.containsKey("broadcast")){
                    itemInput.broadcast= (boolean) (configInput.get("broadcast"));
                }
                if(MultiChatInputItemDaemon.instance==null){
                    MultiChatInputItemDaemon.instance=new MultiChatInputItemDaemon();
                }
                MultiChatInputItemDaemon.instance.addItem(itemInput);
                return itemInput;
            case "multichat-context":
                return new MultiChatContextItem();
                
        }
        return null;
    }
}
