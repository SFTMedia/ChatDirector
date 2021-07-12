package com.blalp.chatdirector.bungee.modules.multichat;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ItemDaemon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.olivermartin.multichat.bungee.events.PostBroadcastEvent;
import xyz.olivermartin.multichat.bungee.events.PostGlobalChatEvent;
import xyz.olivermartin.multichat.bungee.events.PostStaffChatEvent;

@Data
@EqualsAndHashCode(callSuper = false)
public class MultiChatInputDaemon extends ItemDaemon implements Listener {

    @EventHandler
    public void onEvent(PostBroadcastEvent e) {
        Context context = ChatDirector.getConfig().getModule(MultiChatModule.class).getContext(e);
        for (MultiChatInputItem item : getItems().toArray(new MultiChatInputItem[] {})) {
            if (item.broadcast) {
                context.put("CURRENT", e.getMessage());
                ChatDirector.run(item, context, true);
            }
        }
    }

    @EventHandler
    public void onEvent(PostGlobalChatEvent e) {
        Context context = ChatDirector.getConfig().getModule(MultiChatModule.class).getContext(e);
        for (MultiChatInputItem item : getItems().toArray(new MultiChatInputItem[] {})) {
            if (item.global) {
                context.put("CURRENT", e.getMessage());
                ChatDirector.run(item, context, true);
            } else {
                System.out.println(" no global for " + item);
            }
        }
    }

    @EventHandler
    public void onEvent(PostStaffChatEvent e) {
        Context context = ChatDirector.getConfig().getModule(MultiChatModule.class).getContext(e);
        for (MultiChatInputItem item : getItems().toArray(new MultiChatInputItem[] {})) {
            if (item.staff) {
                context.put("CURRENT", e.getMessage());
                ChatDirector.run(item, context, true);
            }
        }
    }
}
