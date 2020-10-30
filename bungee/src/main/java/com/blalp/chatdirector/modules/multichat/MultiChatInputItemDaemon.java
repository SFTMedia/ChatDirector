package com.blalp.chatdirector.modules.multichat;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.olivermartin.multichat.bungee.events.PostBroadcastEvent;
import xyz.olivermartin.multichat.bungee.events.PostGlobalChatEvent;
import xyz.olivermartin.multichat.bungee.events.PostStaffChatEvent;

public class MultiChatInputItemDaemon extends ItemDaemon implements Listener {
    public static MultiChatInputItemDaemon instance;
    public MultiChatInputItemDaemon(){
        instance=this;
    }
    
	@EventHandler
	public void onEvent(PostBroadcastEvent e) {
        Map<String,String> context = ChatDirector.formatter.getContext(e);
        for (MultiChatInputItem item : instance.items.toArray(new MultiChatInputItem[]{})) {
            if(item.broadcast){
                item.startWork(ChatDirector.formatter.format(e.getMessage(), context), true, context);
            }
        }
	}
	@EventHandler
	public void onEvent(PostGlobalChatEvent e) {
        Map<String,String> context = ChatDirector.formatter.getContext(e);
		// this is needed as ServerConnectEvent is also called for the first time.
        for (MultiChatInputItem item : instance.items.toArray(new MultiChatInputItem[]{})) {
            if(item.global){
                item.startWork(ChatDirector.formatter.format(e.getMessage(), context), true, context);
            }
        }
	}
    @EventHandler
    public void onChat(PostStaffChatEvent e){
        Map<String,String> context = ChatDirector.formatter.getContext(e);
        for (MultiChatInputItem item : instance.items.toArray(new MultiChatInputItem[]{})) {
            if(item.staff){
                item.startWork(ChatDirector.formatter.format(e.getMessage(), context), true, context);
            }
        }
    }
    
}
