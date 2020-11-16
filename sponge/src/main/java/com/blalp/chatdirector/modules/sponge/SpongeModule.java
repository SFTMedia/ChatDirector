package com.blalp.chatdirector.modules.sponge;

import java.util.Arrays;
import java.util.List;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.event.user.TargetUserEvent;

public class SpongeModule implements IModule {
    public static SpongeModule instance;
    public SpongeModule(){
        instance=this;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("sponge-output", "sponge-input", "sponge-playerlist", "sponge-command");
    }

    @Override
    public void load() {
        if (SpongeInputDaemon.instance != null) {
            SpongeInputDaemon.instance.load();
        }
        for (SpongeCommandItem command : SpongeCommandItem.commands) {
            command.load();
        }
    }

    @Override
    public void unload() {
        if (SpongeInputDaemon.instance != null) {
            SpongeInputDaemon.instance.unload();
        }
        for (SpongeCommandItem command : SpongeCommandItem.commands) {
            command.unload();
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        switch (type) {
            case "sponge-command":
                return om.convertValue(config, SpongeCommandItem.class);
            case "sponge-output":
                return om.convertValue(config, SpongeOutputItem.class);
            case "sponge-input":
                if (SpongeInputDaemon.instance == null) {
                    new SpongeInputDaemon();
                    SpongeInputDaemon.instance.load();
                }
                SpongeInputItem item2 = om.convertValue(config, SpongeInputItem.class);
                SpongeInputDaemon.instance.addItem(item2,chain);
                return item2;
            case "sponge-playerlist":
                return om.convertValue(config, SpongePlayerlistItem.class);
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object event) {
        Context context = new Context();
        context.put("SERVER_NUM_PLAYERS",String.valueOf(Sponge.getServer().getOnlinePlayers().size()));
        context.put("SERVER_MAX_PLAYERS",String.valueOf(Sponge.getServer().getMaxPlayers()));
        context.put("SERVER_MOTD",String.valueOf(Sponge.getServer().getMotd().toPlain()));
        if(event instanceof Event) {
            if(((Event)event).getCause().first(Player.class).isPresent()){
                context.merge(getContext(((Event)event).getCause().first(Player.class).get()));
            }
        }
        if(event instanceof Player) {
            context.put("PLAYER_NAME",((Player)event).getName());
            context.put("PLAYER_UUID",((Player)event).getUniqueId().toString());
        }
        if(event instanceof Chat) {
            context.put("CHAT_MESSAGE",((Chat)event).getRawMessage().toPlain());
            context.put("CHAT_MESSAGE_FORMATTED",((Chat)event).getMessage().toPlain());
            context.put("CHAT_MESSAGE_ORIGINAL",((Chat)event).getOriginalMessage().toPlain());
            context.put("CHAT_FORMAT",((Chat)event).getMessage().getFormat().toString());
        }
        if(event instanceof TargetUserEvent){
            context.merge(getContext(((TargetUserEvent)event).getTargetUser()));
        }
        return context;
    }
    
}
