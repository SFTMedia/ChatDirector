package com.blalp.chatdirector;

import java.io.File;

import com.blalp.chatdirector.configuration.ConfigurationSponge;
import com.blalp.chatdirector.modules.sponge.SpongeInputDaemon;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(id="chatdirector",name = "Chat Director",version = "0.1.0",description = "Manages as much Chat as needed.")
public class ChatDirectorSponge {
    private ChatDirector chatDirector;

    public ChatDirectorSponge() {
        chatDirector = new ChatDirector(new ConfigurationSponge("config"+File.separatorChar+"chatdirector"+File.separatorChar+"config.yml"));
        new File("config"+File.separatorChar+"chatdirector").mkdirs();
        CommandSpec myCommandSpec = CommandSpec.builder()
            .description(Text.of("ChatDirector Local reload"))
            .permission("chatdirector.reload")
            .executor(new ReloadCommandSponge())
            .build();

        Sponge.getCommandManager().register(this, myCommandSpec, "chatdirectorlocal");
    }
    
    @Listener
    public void onServerStart(GameStartedServerEvent e){
        chatDirector.load();
        if(SpongeInputDaemon.instance!=null){
            SpongeInputDaemon.instance.onServerStart(e);
        }
    }
    
    @Listener
    public void onServerStop(GameStoppedServerEvent e){
        if(SpongeInputDaemon.instance!=null){
            SpongeInputDaemon.instance.onServerStop(e);
        }
        chatDirector.unload();
    }
    @Listener
    public void onReload(GameReloadEvent e){
        chatDirector.reload();
    }

    @Listener
    public void onChat(MessageChannelEvent.Chat e){
        if(SpongeInputDaemon.instance!=null){
            SpongeInputDaemon.instance.onChat(e);
        }
    }
    @Listener
    public void onLogin(ClientConnectionEvent.Login e){
        if(SpongeInputDaemon.instance!=null){
            SpongeInputDaemon.instance.onLogin(e);
        }
    }
    @Listener
    public void onLogout(ClientConnectionEvent.Disconnect e){
        if(SpongeInputDaemon.instance!=null){
            SpongeInputDaemon.instance.onLogout(e);
        }
    }

}
