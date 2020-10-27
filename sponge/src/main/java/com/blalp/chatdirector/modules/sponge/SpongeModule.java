package com.blalp.chatdirector.modules.sponge;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class SpongeModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"sponge-output","sponge-input","sponge-playerlist"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "sponge-output":
                SpongeOutputItem item = new SpongeOutputItem();
                LinkedHashMap<String,String> configMap = ((LinkedHashMap<String,String>)config);
                if(configMap.containsKey("permission")) {
                    item.permission=configMap.get("permission");
                }
                if(configMap.containsKey("sender")) {
                    item.sender=configMap.get("sender");
                }
                return item;
            case "sponge-input":
                if(SpongeInputDaemon.instance==null){
                    new SpongeInputDaemon();
                    SpongeInputDaemon.instance.load();
                }
                Map<String,Object> configList = (Map<String,Object>)config;
                SpongeInputItem item2 = new SpongeInputItem();
                if(configList.containsKey("server-stopped")){
                    item2.serverStopped= (boolean) configList.get("server-stopped");
                }
                if(configList.containsKey("server-started")){
                    item2.serverStarted=(boolean) configList.get("server-started");
                }
                if(configList.containsKey("chat")){
                    item2.chat=(boolean) configList.get("chat");
                }
                if(configList.containsKey("check-canceled")){
                    item2.checkCanceled=(boolean) configList.get("check-canceled");
                }
                if(configList.containsKey("format")){
                    item2.format= (String) configList.get("format");
                }
                if(configList.containsKey("join")){
                    item2.join=(boolean) configList.get("join");
                }
                if(configList.containsKey("leave")){
                    item2.leave=(boolean) configList.get("leave");
                }
                SpongeInputDaemon.instance.addItem(item2);
                return item2;
            case "sponge-playerlist":
                SpongePlayerlistItem itemPlayerlist = new SpongePlayerlistItem();
                LinkedHashMap<String,Object> configMapPlayerlist = ((LinkedHashMap<String,Object>)config);
                if(configMapPlayerlist.containsKey("format")) {
                    itemPlayerlist.format= (String) configMapPlayerlist.get("format");
                }
                if(configMapPlayerlist.containsKey("format-no-players")) {
                    itemPlayerlist.formatNoPlayers= (String) configMapPlayerlist.get("format-no-players");
                }
                if(configMapPlayerlist.containsKey("format-player")) {
                    itemPlayerlist.formatPlayer= (String) configMapPlayerlist.get("format-player");
                }
                if(configMapPlayerlist.containsKey("ignore-case")) {
                    itemPlayerlist.ignoreCase= (boolean) (configMapPlayerlist.get("ignore-case"));
                }
                if(configMapPlayerlist.containsKey("trigger-word")) {
                    itemPlayerlist.triggerWord= (String) configMapPlayerlist.get("trigger-word");
                }
                return itemPlayerlist;
            default:
                return null;
        }
    }

    @Override
    public void load() {
        ChatDirector.addFormatter(new SpongeFormatter());
        if(SpongeInputDaemon.instance!=null){
            SpongeInputDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if(SpongeInputDaemon.instance!=null){
            SpongeInputDaemon.instance.unload();
        }
    }
    
}
