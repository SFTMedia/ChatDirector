package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.ArrayList;
import java.util.List;

import com.blalp.chatdirector.bungee.ChatDirectorBungee;
import com.blalp.chatdirector.model.IDaemon;
import com.blalp.chatdirector.model.IItem;

import net.md_5.bungee.api.ProxyServer;

public class BungeeCommandDaemon implements IDaemon {
    private List<BungeeCommand> commands = new ArrayList<>();
    

    @Override
    public boolean load() {
        for (BungeeCommand command : commands) {
            ProxyServer.getInstance().getPluginManager().registerCommand(ChatDirectorBungee.instance, command);
        }
        return true;
    }

    @Override
    public boolean unload() {
        for (BungeeCommand command : commands) {
            ProxyServer.getInstance().getPluginManager().unregisterCommand(command);
        }
        commands=new ArrayList<>();
        return true;
    }
    @Override
    public void addItem(IItem item) {
        commands.add(new BungeeCommand(((BungeeCommandItem)item).command, (BungeeCommandItem) item));
    }
}
