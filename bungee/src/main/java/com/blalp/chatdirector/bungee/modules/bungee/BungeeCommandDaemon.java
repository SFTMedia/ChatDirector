package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.HashMap;
import java.util.Map.Entry;

import com.blalp.chatdirector.bungee.ChatDirectorBungee;
import com.blalp.chatdirector.core.model.IDaemon;
import com.blalp.chatdirector.core.model.IItem;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.md_5.bungee.api.ProxyServer;

@Data
@EqualsAndHashCode(callSuper = false)
public class BungeeCommandDaemon implements IDaemon {
    private HashMap<BungeeCommandItem, BungeeCommand> commands = new HashMap<>();

    @Override
    public boolean load() {
        for (Entry<BungeeCommandItem, BungeeCommand> command : commands.entrySet()) {
            if (command.getValue() == null) {
                command.setValue(new BungeeCommand(command.getKey().getCommand(), command.getKey()));
            }
            // AKA if this is running on a real server
            if (ProxyServer.getInstance() != null) {
                ProxyServer.getInstance().getPluginManager().registerCommand(ChatDirectorBungee.instance,
                        command.getValue());
            }
        }
        return true;
    }

    @Override
    public boolean unload() {
        for (BungeeCommand command : commands.values()) {
            ProxyServer.getInstance().getPluginManager().unregisterCommand(command);
        }
        commands = new HashMap<>();
        return true;
    }

    @Override
    public void addItem(IItem item) {
        commands.put((BungeeCommandItem) item, null);
    }
}
