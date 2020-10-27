package com.blalp.chatdirector.modules.bungee;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
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
        for (BungeeInputItem item : (BungeeInputItem[]) instance.items.toArray()) {
            if(item.disconnect){
                item.startWork(ChatDirector.formatter.format(item.disconnectFormat, context), true, context);
            }
        }
	}
	@EventHandler
	public void onEvent(ServerConnectedEvent e) {
        Map<String,String> context = ChatDirector.formatter.getContext(e);
		// this is needed as ServerConnectEvent is also called for the first time.
        for (BungeeInputItem item : (BungeeInputItem[]) instance.items.toArray()) {
            if (existing_players.contains(e.getPlayer().getUniqueId())) {
                if(item.switchServers){
                    item.startWork(ChatDirector.formatter.format(item.formatSwitch, context), true, context);
                }
            } else {
                if(item.joinServer){
                    item.startWork(ChatDirector.formatter.format(item.formatJoin, context), true, context);
                }
            }
        }
	}
    @EventHandler
    public void onChat(ChatEvent e){
        Map<String,String> context = ChatDirector.formatter.getContext(e);
        for (BungeeInputItem item : (BungeeInputItem[]) instance.items.toArray()) {
            if(item.chat){
                item.startWork(ChatDirector.formatter.format(item.formatChat, context), true, context);
            }
        }
    }   
    
}
