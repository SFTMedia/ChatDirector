package com.blalp.chatdirector.modules.bungee;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.Module;
import com.blalp.chatdirector.modules.bukkit.BukkitCommandInputDaemon;
import com.blalp.chatdirector.modules.bukkit.BukkitCommandInputItem;

import org.apache.commons.lang.NullArgumentException;

public class BungeeModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"bungee-to","bungee-from","bungee-command","bungee-playerlist"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            case "bungee-to":
                return new ToBungeeItem((String)((LinkedHashMap<String,Object>)config).get("channel"));
            case "bungee-from":
                if(FromBungeeDaemon.instance==null){
                    FromBungeeDaemon.instance=new FromBungeeDaemon();
                }
                FromBungeeItem item = new FromBungeeItem((String)((LinkedHashMap<String,Object>)config).get("channel"));
                FromBungeeDaemon.instance.addItem(item);
                return item;
            case "bungee-command":
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
                            throw new NullArgumentException("args needs to be a list.");
                        } catch (NullArgumentException e){
                            e.printStackTrace();
                        }
                    }
                }
                if(configMap.containsKey("format")){
                    item2.format=(String)configMap.get("format");
                }
                return item2;
            case "bungee-playerlist":
                BungeePlayerlistItem itemPlayerlist = new BungeePlayerlistItem();
                LinkedHashMap<String,String> configMapPlayerlist = ((LinkedHashMap<String,String>)config);
                if(configMapPlayerlist.containsKey("format")) {
                    itemPlayerlist.format=configMapPlayerlist.get("format");
                }
                if(configMapPlayerlist.containsKey("format-no-players")) {
                    itemPlayerlist.formatNoPlayers=configMapPlayerlist.get("format-no-players");
                }
                if(configMapPlayerlist.containsKey("ignore-case")) {
                    itemPlayerlist.ignoreCase=Boolean.parseBoolean(configMapPlayerlist.get("ignore-case"));
                }
                if(configMapPlayerlist.containsKey("trigger-word")) {
                    itemPlayerlist.triggerWord=configMapPlayerlist.get("trigger-word");
                }
                if(configMapPlayerlist.containsKey("format-player")) {
                    itemPlayerlist.formatPlayer=configMapPlayerlist.get("format-player");
                }
                if(configMapPlayerlist.containsKey("format-server")) {
                    itemPlayerlist.formatServer=configMapPlayerlist.get("format-server");
                }
                if(configMapPlayerlist.containsKey("split-by-server")) {
                    itemPlayerlist.splitByServer=Boolean.parseBoolean(configMapPlayerlist.get("split-by-server"));
                }
                return itemPlayerlist;
        }
        return null;
    }

    @Override
    public void load() {
        ChatDirector.addFormatter(new BungeeFormatter());
        if(FromBungeeDaemon.instance!=null){
            FromBungeeDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if(FromBungeeDaemon.instance!=null){
            FromBungeeDaemon.instance.unload();
        }
    }
    
}
