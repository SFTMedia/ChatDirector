package com.blalp.chatdirector.modules.bungee;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeInputItemDaemon extends ItemDaemon implements Listener {
    public static BungeeInputItemDaemon instance;
    public BungeeInputItemDaemon(){
        instance=this;
    }

    private Set<UUID> existing_players = new HashSet<>();
    
	@EventHandler
	public void onEvent(PlayerDisconnectEvent e) {
        Map<String,String> context = ChatDirector.formatter.getContext(e);
		existing_players.remove(e.getPlayer().getUniqueId());
        for (BungeeInputItem item : instance.items.toArray(new BungeeInputItem[]{})) {
            if(item.disconnect){
                item.startWork(ChatDirector.format(item.disconnectFormat, context), true, context);
            }
        }
	}
	@EventHandler
	public void onEvent(ServerSwitchEvent e) {
        Map<String,String> context = ChatDirector.formatter.getContext(e);
		// this is needed as ServerConnectEvent is also called for the first time.
        for (BungeeInputItem item : instance.items.toArray(new BungeeInputItem[]{})) {
            if (existing_players.contains(e.getPlayer().getUniqueId())) {
                if(item.switchServers){
                    item.startWork(ChatDirector.format(item.formatSwitch, context), true, context);
                }
            } else {
                existing_players.add(e.getPlayer().getUniqueId());
            }
        }
    }
	@EventHandler
	public void onEvent(ServerConnectedEvent e) {
        Map<String,String> context = ChatDirector.formatter.getContext(e);
		// this is needed as ServerConnectEvent is also called for the first time.
        for (BungeeInputItem item : instance.items.toArray(new BungeeInputItem[]{})) {
            if (!existing_players.contains(e.getPlayer().getUniqueId())) {
                if(item.joinServer){
                    item.startWork(ChatDirector.format(item.formatJoin, context), true, context);
                }
            }
        }
	}
    @EventHandler
    public void onChat(ChatEvent e){
        Map<String,String> context = ChatDirector.formatter.getContext(e);
        for (BungeeInputItem item : instance.items.toArray(new BungeeInputItem[]{})) {
            if((item.chat&&!e.getMessage().startsWith("/"))||item.command&&e.getMessage().startsWith("/")){
                if(item.overrideChat){
                    e.setMessage(item.work(ChatDirector.format(item.formatChat, context), context));
                } else {
                    item.startWork(ChatDirector.format(item.formatChat, context), true, context);
                }
            }
        }
    }
    @EventHandler
    public void onMessage(PluginMessageEvent e){
        // TODO
    }
    /*
    @Override
    public void load() {
        ChatDirectorBungee.instance.getProxy().getPluginManager().registerListener(ChatDirectorBungee.instance, this);
    }
    @Override
    public void unload() {
        ChatDirectorBungee.instance.getProxy().getPluginManager().unregisterListener(this);
    }
    */
}
