package com.blalp.chatdirector.proximitychat.modules.proximitychat;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

public class ProximityChatModule implements IModule {

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("specific-proximity-chat-calculate", "specific-proximity-chat-discord-move",
                "specific-proximity-chat-convert-to-discord");
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "specific-proximity-chat":
            return ProximityChatItem.class;
        case "specific-proximity-chat-discord-move":
            return DiscordMovementItem.class;
        case "specific-proximity-chat-convert-to-discord":
            return ConvertUUIDToDiscordId.class;
        default:
            return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

}
