package com.blalp.chatdirector.platform.bungee;

import com.blalp.chatdirector.configuration.TimedLoad;

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
