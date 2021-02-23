package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.bungee.ChatDirectorBungee;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.ILoadable;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class BungeeCommand extends Command implements ILoadable {
    public static ArrayList<BungeeCommand> commands = new ArrayList<>();
    public BungeeCommandItem item;

    public BungeeCommand(String name, BungeeCommandItem item) {
        super(name);
        this.item = item;
        commands.add(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Context context = BungeeModule.instance.getContext(sender);
        String string = item.command;
        for (int i = 0; i < args.length; i++) {
            string += " " + args[i];
            context.put("COMMAND_ARG_" + i, args[i]);
        }
        context.put("COMMAND_NAME", item.command);
        context.put("COMMAND_PERMISSION", item.permission);
        context.put("CURRENT", string);
        ChatDirector.run(item, context, true);
    }

    @Override
    public boolean load() {
        ProxyServer.getInstance().getPluginManager().registerCommand(ChatDirectorBungee.instance, this);
        return true;
    }

    @Override
    public boolean unload() {
        ProxyServer.getInstance().getPluginManager().unregisterCommand(this);
        return true;
    }

}
