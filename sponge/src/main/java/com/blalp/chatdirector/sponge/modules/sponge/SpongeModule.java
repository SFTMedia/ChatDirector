package com.blalp.chatdirector.sponge.modules.sponge;

import java.util.Arrays;
import java.util.List;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.model.IModule;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.event.user.TargetUserEvent;

public class SpongeModule implements IModule {

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("sponge-output", "sponge-input", "sponge-playerlist", "sponge-command");
    }

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
    public Context getContext(Object event) {
        Context context = new Context();
        context.put("SERVER_NUM_PLAYERS", String.valueOf(Sponge.getServer().getOnlinePlayers().size()));
        context.put("SERVER_MAX_PLAYERS", String.valueOf(Sponge.getServer().getMaxPlayers()));
        context.put("SERVER_MOTD", String.valueOf(Sponge.getServer().getMotd().toPlain()));
        if (event instanceof Event) {
            if (((Event) event).getCause().first(Player.class).isPresent()) {
                context.merge(getContext(((Event) event).getCause().first(Player.class).get()));
            }
        }
        if (event instanceof Player) {
            context.put("PLAYER_NAME", ((Player) event).getName());
            context.put("PLAYER_UUID", ((Player) event).getUniqueId().toString());
        }
        if(event instanceof Cancellable){
            context.put("CANCELED", ((Cancellable)event).isCancelled()?"1":"0");
        }
        if (event instanceof Chat) {
            context.put("CHAT_MESSAGE", ((Chat) event).getRawMessage().toPlain());
            context.put("CHAT_MESSAGE_FORMATTED", ((Chat) event).getMessage().toPlain());
            context.put("CHAT_MESSAGE_ORIGINAL", ((Chat) event).getOriginalMessage().toPlain());
            context.put("CHAT_FORMAT", ((Chat) event).getMessage().getFormat().toString());
        }
        if (event instanceof TargetUserEvent) {
            context.merge(getContext(((TargetUserEvent) event).getTargetUser()));
        }
        return context;
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
        case "sponge-command":
            return SpongeCommandItem.class;
        case "sponge-output":
            return SpongeOutputItem.class;
        case "sponge-input":
            return SpongeInputItem.class;
        case "sponge-playerlist":
            return SpongePlayerlistItem.class;
        default:
            return null;
        }
    }

}
