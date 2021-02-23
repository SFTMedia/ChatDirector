package com.blalp.chatdirector.bukkit.modules.bukkit;

import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BukkitCommand {
    public static ArrayList<BukkitCommand> commands = new ArrayList<>();
    BukkitCommandItem item;

    public BukkitCommand(String name, BukkitCommandItem item) {
        System.err.println("WARNING: YOU MUST ADD " + name + " AS A COMMAND IN THE plugin.yml");
        this.item = item;
        commands.add(this);
    }

    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals(item.command) && sender.hasPermission(item.permission)) {
            Context context = BukkitModule.instance.getContext(sender);
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
        return false;
    }
}
