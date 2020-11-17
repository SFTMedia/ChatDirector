package com.blalp.chatdirector.modules.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.blalp.chatdirector.model.IItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitModule implements IModule {

    public static BukkitModule instance;

    public BukkitModule() {
        instance = this;
    }

    @Override
    public void load() {
        if (BukkitInputDaemon.instance != null) {
            BukkitInputDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if (BukkitInputDaemon.instance != null) {
            BukkitInputDaemon.instance.unload();
        }
        BukkitCommand.commands = new ArrayList<>();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("bukkit-input", "bukkit-output", "bukkit-playerlist", "bukkit-command");
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "bukkit-input":
                if (BukkitInputDaemon.instance == null) {
                    new BukkitInputDaemon();
                    BukkitInputDaemon.instance.load();
                }
                BukkitInputItem item = om.convertValue(config, BukkitInputItem.class);
                BukkitInputDaemon.instance.addItem(item,chain);
                return item;
            case "bukkit-output":
                return om.convertValue(config, BukkitOutputItem.class);
            case "bukkit-playerlist":
                return om.convertValue(config, BukkitPlayerlistItem.class);
            case "bukkit-command":
                return om.convertValue(config, BukkitCommandItem.class);
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object event) {
        Context context = new Context();
        context.put("SERVER_NUM_PLAYERS",String.valueOf(Bukkit.getOnlinePlayers().size()));
        context.put("SERVER_MAX_PLAYERS",String.valueOf(Bukkit.getMaxPlayers()));
        context.put("SERVER_NAME",String.valueOf(Bukkit.getServer().getName()));
        context.put("SERVER_MOTD",String.valueOf(Bukkit.getMotd()));
        if(event instanceof PlayerEvent) {
            context.put("PLAYER_NAME", ((PlayerEvent)event).getPlayer().getName());
            context.put("PLAYER_UUID", ((PlayerEvent)event).getPlayer().getUniqueId().toString());
        }
        if(event instanceof AsyncPlayerChatEvent){
            context.put("CHAT_MESSAGE",((AsyncPlayerChatEvent)event).getMessage());
            context.put("CHAT_FORMAT",((AsyncPlayerChatEvent)event).getFormat());
        }
        if(event instanceof PlayerQuitEvent){
            context.put("PLAYER_QUIT_MESSAGE",((PlayerQuitEvent)event).getQuitMessage());
        }
        if(event instanceof CommandSender) {
            context.put("PLAYER_NAME",((CommandSender)event).getName());
        }
        if(event instanceof Player) {
            context.put("PLAYER_NAME",((Player)event).getName());
            context.put("PLAYER_UUID",((Player)event).getUniqueId().toString());
        }
        if(event instanceof Command) {
            context.put("COMMAND_NAME",((Command)event).getName());
        }
        if(event instanceof ConsoleCommandSender) {
            context.put("PLAYER_NAME","*CONSOLE*");
        }
        return context;
    }
}