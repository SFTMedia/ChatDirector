package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.ArrayList;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BungeeCommand extends Command {
    public static ArrayList<BungeeCommand> commands = new ArrayList<>();
    public BungeeCommandItem item;

    public BungeeCommand(String name, BungeeCommandItem item) {
        super(name, item.permission);
        this.item = item;
        commands.add(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Context context = ChatDirector.getConfig().getModule(BungeeModule.class).getContext(sender);
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
}
