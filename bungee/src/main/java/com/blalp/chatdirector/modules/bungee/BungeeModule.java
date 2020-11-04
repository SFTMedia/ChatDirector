package com.blalp.chatdirector.modules.bungee;

import java.util.LinkedHashMap;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.fancychat.FancyMessage;
import com.blalp.chatdirector.modules.Module;

public class BungeeModule extends Module {

    @Override
    public String[] getItemNames() {
        return new String[]{"bungee-command","bungee-playerlist","bungee-output","bungee-input","bungee-output-player","bungee-output-server","bungee-output-fancy"};
    }

    @Override
    public IItem createItem(String type, Object config) {
        switch (type){
            /*case "bungee-command":
                if(BukkitCommandInputDaemon.instance==null){
                    new BukkitCommandInputDaemon();
                    BukkitCommandInputDaemon.instance.load();
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
                if(configMapPlayerlist!=null){
                    if(configMapPlayerlist.containsKey("format")) {
                        itemPlayerlist.format= (String) configMapPlayerlist.get("format");
                    }
                    if(configMapPlayerlist.containsKey("format-no-players")) {
                        itemPlayerlist.formatNoPlayers= (String) configMapPlayerlist.get("format-no-players");
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
                }
                return itemPlayerlist;
            case "bungee-command":
                LinkedHashMap<String,String> configCommand = ((LinkedHashMap<String,String>)config);
                return new BungeeCommandItem(configCommand.get("command"),configCommand.get("permission"));
            case "bungee-output":
                return new BungeeOutputItem();
            case "bungee-output-fancy":
                return new BungeeOutputFancyItem(FancyMessage.parse(((LinkedHashMap<String,String>)config).get("fancy-format")), ((LinkedHashMap<String,String>)config).get("permission"));
            case "bungee-output-player":
                LinkedHashMap<String,String> configOutputPlayer = ((LinkedHashMap<String,String>)config);
                BungeePlayerItem outputPlayer = new BungeePlayerItem(configOutputPlayer.get("player"));
                if(configOutputPlayer.containsKey("permission")){
                    outputPlayer.permission=configOutputPlayer.get("permission");
                }
                return outputPlayer;
            case "bungee-output-server":
                LinkedHashMap<String,String> configOutputServer = ((LinkedHashMap<String,String>)config);
                BungeeOutputServerItem outputServer = new BungeeOutputServerItem(configOutputServer.get("server"));
                if(configOutputServer.containsKey("permission")){
                    outputServer.permission=configOutputServer.get("permission");
                }
                return outputServer;
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
                if(configInput.containsKey("command")){
                    itemInput.command= (boolean) configInput.get("command");
                }
                if(BungeeInputItemDaemon.instance==null){
                    BungeeInputItemDaemon.instance=new BungeeInputItemDaemon();
                }
                BungeeInputItemDaemon.instance.addItem(itemInput);
                return itemInput;
        }
        return null;
    }

    @Override
    public void load() {
        ChatDirector.addFormatter(new BungeeFormatter());
        if(BungeeInputItemDaemon.instance!=null){
            BungeeInputItemDaemon.instance.load();
        }
        for(BungeeCommand command:BungeeCommand.commands){
            command.load();
        }
    }

    @Override
    public void unload() {
        if(BungeeInputItemDaemon.instance!=null){
            BungeeInputItemDaemon.instance.unload();
        }
        for(BungeeCommand command:BungeeCommand.commands){
            command.unload();
        }
    }
}
