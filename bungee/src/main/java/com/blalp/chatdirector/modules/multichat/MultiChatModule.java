package com.blalp.chatdirector.modules.multichat;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.IModule;

import xyz.olivermartin.multichat.bungee.events.PostBroadcastEvent;
import xyz.olivermartin.multichat.bungee.events.PostGlobalChatEvent;
import xyz.olivermartin.multichat.bungee.events.PostStaffChatEvent;

public class MultiChatModule implements IModule {

    public static MultiChatModule instance;

    public MultiChatModule() {
        instance=this;
    }

	@Override
    public void load() {
        if (MultiChatInputItemDaemon.instance != null) {
            MultiChatInputItemDaemon.instance.load();
        }
    }

    @Override
    public void unload() {
        if (MultiChatInputItemDaemon.instance != null) {
            MultiChatInputItemDaemon.instance.unload();
        }
    }

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public List<String> getItemNames() {
		return Arrays.asList("multichat-input","multichat-context");
	}
	@Override
	public Context getContext(Object event) {
        Context context = new Context();
        if(event instanceof PostBroadcastEvent){
            context.put("BROADCAST_MESSAGE", ((PostBroadcastEvent)event).getMessage());
        }
        if(event instanceof PostGlobalChatEvent) {
            context.put("CHAT_MESSAGE_FORMAT", ((PostGlobalChatEvent)event).getFormat());
            context.put("CHAT_MESSAGE", ((PostGlobalChatEvent)event).getMessage());
            context.put("MULTICHAT_MODE", ((PostGlobalChatEvent)event).getSenderChatMode());
            context.merge(getContext(((PostGlobalChatEvent)event).getSender()));
            context.put("PLAYER_NAME", ((PostGlobalChatEvent)event).getSenderName());
            context.put("PLAYER_PREFIX", ((PostGlobalChatEvent)event).getSenderPrefix());
            context.put("SERVER_NAME", ((PostGlobalChatEvent)event).getSenderServer());
            context.put("PLAYER_SUFFIX", ((PostGlobalChatEvent)event).getSenderSuffix());
            context.put("MULTICHAT_MODE_SHORT", ((PostGlobalChatEvent)event).getSenderShortChatMode());
        } 
        if(event instanceof PostStaffChatEvent) {
            context.put("CHAT_MESSAGE", ((PostStaffChatEvent)event).getMessage());
            context.merge(getContext(((PostStaffChatEvent)event).getSender()));
            context.put("PLAYER_NAME", ((PostStaffChatEvent)event).getSenderName());
            context.put("PLAYER_PREFIX", ((PostStaffChatEvent)event).getSenderPrefix());
            context.put("SERVER_NAME", ((PostStaffChatEvent)event).getSenderServer());
            context.put("PLAYER_SUFFIX", ((PostStaffChatEvent)event).getSenderSuffix());
        }
        return context;
	}

    @Override
    public Class<?> getItemClass(String type) {
        switch (type) {
            case "multichat-input":
                return MultiChatInputItem.class;
            case "multichat-context":
                return  MultiChatContextItem.class;
            default:
                return null;
        }
    }
}