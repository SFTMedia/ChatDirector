package com.blalp.chatdirector.modules.bungee;

import java.util.LinkedHashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;

public class BungeeModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"bungee-command","bungee-playerlist","bungee-output","bungee-input"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            /*case "bungee-command":
                if(BukkitCommandInputDaemon.instance==null){
                    new BukkitCommandInputDaemon();
                }
                LinkedHashMap<String,Object> configMap = ((LinkedHashMap<String,Object>)config);
                BukkitCommandInputItem item2 = new BukkitCommandInputItem((String)configMap.get("command"), (String)configMap.get("permission"));
                if (configMap.containsKey("args")){
                    if(configMap.get("args") instanceof ArrayList<?>){
                        item2.args=((ArrayList<?>)configMap.get("args")).toArray(item2.args);
                    } else {
                        try {
                            throw new NullPointerException("args needs to be a list.");
                        } catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                }
                if(configMap.containsKey("format")){
                    item2.format=(String)configMap.get("format");
                }
                return item2;*/
            case "bungee-playerlist":
                BungeePlayerlistItem itemPlayerlist = new BungeePlayerlistItem();
                LinkedHashMap<String,Object> configMapPlayerlist = ((LinkedHashMap<String,Object>)config);
                if(configMapPlayerlist.containsKey("format")) {
                    itemPlayerlist.format= (String) configMapPlayerlist.get("format");
                }
                if(configMapPlayerlist.containsKey("format-no-players")) {
                    itemPlayerlist.formatNoPlayers= (String) configMapPlayerlist.get("format-no-players");
                }
                if(configMapPlayerlist.containsKey("ignore-case")) {
                    itemPlayerlist.ignoreCase= (boolean) configMapPlayerlist.get("ignore-case");
                }
                if(configMapPlayerlist.containsKey("trigger-word")) {
                    itemPlayerlist.triggerWord= (String) configMapPlayerlist.get("trigger-word");
                }
                if(configMapPlayerlist.containsKey("format-player")) {
                    itemPlayerlist.formatPlayer= (String) configMapPlayerlist.get("format-player");
                }
                if(configMapPlayerlist.containsKey("format-server")) {
                    itemPlayerlist.formatServer= (String) configMapPlayerlist.get("format-server");
                }
                if(configMapPlayerlist.containsKey("split-by-server")) {
                    itemPlayerlist.splitByServer= (boolean) configMapPlayerlist.get("split-by-server");
                }
                return itemPlayerlist;
            case "bungee-output":
                return new BungeeOutputItem();
            case "bungee-input":
                LinkedHashMap<String,Object> configInput = ((LinkedHashMap<String,Object>)config);
                BungeeInputItem itemInput = new BungeeInputItem();
                if(configInput.containsKey("chat")){
                    itemInput.chat= (boolean) (configInput.get("chat"));
                }
                if(configInput.containsKey("format-chat")){
                    itemInput.formatChat= (String) configInput.get("format-chat");
                }
                if(configInput.containsKey("disconnect")){
                    itemInput.disconnect= (boolean) (configInput.get("disconnect"));
                }
                if(configInput.containsKey("format-disconnect")){
                    itemInput.disconnectFormat= (String) configInput.get("format-disconnect");
                }
                if(configInput.containsKey("switch-servers")){
                    itemInput.switchServers= (boolean) (configInput.get("switch-servers"));
                }
                if(configInput.containsKey("format-switch-servers")){
                    itemInput.formatSwitch= (String) configInput.get("format-switch-servers");
                }
                if(configInput.containsKey("join")){
                    itemInput.joinServer= (boolean) (configInput.get("join"));
                }
                if(configInput.containsKey("format-join")){
                    itemInput.formatSwitch= (String) configInput.get("format-join");
                }
                return itemInput;
        }
        return null;
    }

    @Override
    public void load() {
        ChatDirector.addFormatter(new BungeeFormatter());
    }

    @Override
    public void unload() {
    }
}
