package com.blalp.chatdirector.modules.bukkit;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.model.format.Formatter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitFormatter extends Formatter {
    @Override
    public Map<String, String> getContext(Object event) {
        HashMap<String,String> context = new HashMap<>();
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
