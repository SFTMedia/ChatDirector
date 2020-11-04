package com.blalp.chatdirector.modules.sponge;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.event.network.ClientConnectionEvent.Disconnect;
import org.spongepowered.api.event.network.ClientConnectionEvent.Login;

public class SpongeInputDaemon extends ItemDaemon {
    public static SpongeInputDaemon instance;
    public String format = "**%PLAYER_NAME% joined the server**";
    public SpongeInputDaemon(){
        instance=this;
    }
	public void onServerStop(GameStoppedServerEvent e) {
        // Loaded the main world. Server started!
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (item.serverStopped) {
                item.startWork("**Server Stopped**",true,ChatDirector.formatter.getContext(e));
            }
        }
	}
	public void onServerStart(GameStartedServerEvent event) {
        // Loaded the main world. Server started!
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (item.serverStarted) {
                item.startWork("**Server Started**",true,ChatDirector.formatter.getContext(event));
            }
        }
	}
	public void onChat(Chat event) {
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (event.isCancelled() && item.checkCanceled) {
                continue;
            }
            if (item.chat) {
                Map<String,String> context = ChatDirector.formatter.getContext(event);
                item.startWork(ChatDirector.format(item.format,context),false,context);
                if(item.cancelChat){
                    event.setCancelled(true);
                }
            }
        }
    }
	public void onLogin(Login event) {
        Map<String,String> context = ChatDirector.formatter.getContext(event);
        String message = ChatDirector.format(format,context);
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (event.isCancelled() && item.checkCanceled) {
                continue;
            }
            if (item.join) {
                item.startWork(message,true,ChatDirector.formatter.getContext(event));
            }
        }
	}
	public void onLogout(Disconnect event) {
        String message = "**" + event.getTargetEntity().getName() + " left the server**";
        for (SpongeInputItem item : items.toArray(new SpongeInputItem[]{})) {
            if (item.leave) {
                item.startWork(message,true,ChatDirector.formatter.getContext(event));
            }
        }
	}
    
}
