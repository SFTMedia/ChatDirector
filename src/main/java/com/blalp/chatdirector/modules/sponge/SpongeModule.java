package com.blalp.chatdirector.modules.sponge;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

import org.apache.commons.lang.NotImplementedException;

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
                }
                SpongeInputItem item2 = new SpongeInputItem();
                ArrayList<String> configList = (ArrayList<String>)config;
                if(configList.contains("server-stopped")){
                    item2.serverStopped=true;
                }
                if(configList.contains("server-started")){
                    item2.serverStarted=true;
                }
                if(configList.contains("chat")){
                    item2.chat=true;
                }
                if(configList.contains("check-canceled")){
                    item2.checkCanceled=true;
                }
                if(configList.contains("format")){
                    try {
                        throw new NotImplementedException();
                    } catch (NotImplementedException e){
                        e.printStackTrace();
                    }
                }
                if(configList.contains("join")){
                    item2.join=true;
                }
                if(configList.contains("leave")){
                    item2.leave=true;
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
