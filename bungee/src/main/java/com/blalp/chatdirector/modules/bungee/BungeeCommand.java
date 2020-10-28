package com.blalp.chatdirector.modules.bungee;

import java.util.ArrayList;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.ChatDirectorBungee;
import com.blalp.chatdirector.model.ILoadable;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class BungeeCommand extends Command implements ILoadable {
    public static ArrayList<BungeeCommand> commands = new ArrayList<>();
    BungeeCommandItem item;
    public BungeeCommand(String name,BungeeCommandItem item) {
        super(name);
        this.item=item;
        commands.add(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Map<String,String> context = ChatDirector.formatter.getContext(sender);
        String string = item.command;
        for (int i = 0; i < args.length; i++) {
            string += " "+args[i];
            context.put("COMMAND_ARG_"+i, args[i]);
        }
        context.put("COMMAND_NAME", item.command);
        context.put("COMMAND_PERMISSION", item.permission);
        item.startWork(string, true, context);
    }

    @Override
    public void load() {
        ProxyServer.getInstance().getPluginManager().registerCommand(ChatDirectorBungee.instance, this);
    }

    @Override
    public void unload() {
        ProxyServer.getInstance().getPluginManager().unregisterCommand(this);
    }

    @Override
    public void reload() {
        unload();
        load();
    }
    
}
