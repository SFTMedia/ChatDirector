package com.blalp.chatdirector.bukkit.modules.bukkit;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ItemDaemon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BukkitCommandDaemon extends ItemDaemon {

    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        for (BukkitCommandItem item : this.getItems().toArray(new BukkitCommandItem[] {})) {
            if (command.getName().equals(item.command) && sender.hasPermission(item.permission)) {
                Context context = ChatDirector.getConfig().getModule(BukkitModule.class).getContext(sender);
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
        return false;
    }
}
