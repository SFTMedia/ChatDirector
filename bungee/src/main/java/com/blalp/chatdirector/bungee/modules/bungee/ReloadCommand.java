package com.blalp.chatdirector.bungee.modules.bungee;

import com.blalp.chatdirector.core.configuration.TimedLoad;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    public ReloadCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("chatdirector.commands.reload")) {
            new Thread(new TimedLoad()).start();
        }
    }

}
