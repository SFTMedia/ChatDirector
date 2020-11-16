package com.blalp.chatdirector.modules.sponge;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.ItemDaemon;

import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.event.network.ClientConnectionEvent.Disconnect;
import org.spongepowered.api.event.network.ClientConnectionEvent.Login;
import org.spongepowered.api.text.Text;

public class SpongeInputDaemon extends ItemDaemon {
    public static SpongeInputDaemon instance;
    public SpongeInputDaemon(){
        instance=this;
    }
	public void onServerStop(GameStoppedServerEvent event) {
        // Loaded the main world. Server started!
        Context context = SpongeModule.instance.getContext(event);
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (item.serverStopped) {
                context.put("CURRENT", ChatDirector.format(item.formatStopped, context));
                ChatDirector.run(item, context, true);
            }
        }
	}
	public void onServerStart(GameStartedServerEvent event) {
        // Loaded the main world. Server started!
        Context context = SpongeModule.instance.getContext(event);
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (item.serverStarted) {
                context.put("CURRENT", ChatDirector.format(item.formatStarted, context));
                ChatDirector.run(item, context, true);
            }
        }
	}
	public void onChat(Chat event) {
        Context context = SpongeModule.instance.getContext(event);
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (event.isCancelled() && item.checkCanceled) {
                continue;
            }
            if (item.chat) {
                context.put("CURRENT", ChatDirector.format(item.formatChat,context));
                if (item.overrideChat){
                    event.setMessage(Text.of(ChatDirector.run(item,context,false)));
                } else {
                    event.setMessage(Text.of(ChatDirector.run(item,context,true)));
                }
                if(item.cancelChat){
                    event.setCancelled(true);
                }
            }
        }
    }
	public void onLogin(Login event) {
        Context context = SpongeModule.instance.getContext(event);
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (event.isCancelled() && item.checkCanceled) {
                continue;
            }
            if (item.join) {
                context.put("CURRENT",ChatDirector.format(item.formatLogin,context));
                ChatDirector.run(item, context, true);
            }
        }
	}
	public void onLogout(Disconnect event) {
        Context context = SpongeModule.instance.getContext(event);
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (item.leave) {
                context.put("CURRENT",ChatDirector.format(item.formatLogout,context));
                ChatDirector.run(item, context, true);
            }
        }
	}
    
}
