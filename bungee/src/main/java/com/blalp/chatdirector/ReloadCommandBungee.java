package com.blalp.chatdirector;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommandBungee extends Command {

    public ReloadCommandBungee(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("chatdirector.reload")){
            sender.sendMessage("Starting Bungee ChatDirector reload");
            ChatDirector.instance.reload();
            sender.sendMessage("Finished Bungee ChatDirector reload");
        }
    }
    
}
