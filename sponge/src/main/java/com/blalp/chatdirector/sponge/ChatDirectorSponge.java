package com.blalp.chatdirector.sponge;

import java.io.File;

import com.blalp.chatdirector.core.configuration.TimedLoad;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.sponge.modules.sponge.ReloadCommand;
import com.blalp.chatdirector.sponge.modules.sponge.SpongeInputDaemon;
import com.google.inject.Inject;

import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "chatdirector", name = "Chat Director", version = "0.2.17", description = "Manages as much Chat as needed.")
public class ChatDirectorSponge {
    public static ChatDirectorSponge instance;
    private ChatDirector chatDirector;

    public ChatDirectorSponge() {
        instance = this;
    }

    @Inject
    @ConfigDir(sharedRoot = false)
    public File configDir;

    @Listener
    public void onServerStart(GameStartedServerEvent e) {
        new ReloadCommand().load();
        try {
            chatDirector = new ChatDirector(configDir.getAbsolutePath() + File.separatorChar + "config.yml");
            configDir.mkdirs();
            chatDirector.load();
            ((SpongeInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(SpongeInputDaemon.class)).onServerStart(e);
            if (!ChatDirector.hasChains()) {
                throw new Exception("NO CHAINS!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("YIKES! Some error. Registering /chatdirector for you so you can reload.");
        }
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent e) {
        ((SpongeInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(SpongeInputDaemon.class)).onServerStop(e);
        chatDirector.unload();
    }

    @Listener
    public void onReload(GameReloadEvent e) {
        new Thread(new TimedLoad()).start();
    }

    @Listener
    public void onChat(MessageChannelEvent.Chat e) {
        ((SpongeInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(SpongeInputDaemon.class)).onChat(e);
    }

    @Listener
    public void onLogin(ClientConnectionEvent.Login e) {
        ((SpongeInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(SpongeInputDaemon.class)).onLogin(e);
    }

    @Listener
    public void onLogout(ClientConnectionEvent.Disconnect e) {
        ((SpongeInputDaemon) ChatDirector.getConfig().getOrCreateDaemon(SpongeInputDaemon.class)).onLogout(e);
    }
}
