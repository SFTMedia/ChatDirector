package com.blalp.chatdirector.modules.bukkit;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.ItemDaemon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BukkitCommandInputDaemon extends ItemDaemon {

    public static BukkitCommandInputDaemon instance;
    public BukkitCommandInputDaemon(){
        instance=this;
    }
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String,String> context=null;
        for(BukkitCommandInputItem item:items.toArray(new BukkitCommandInputItem[]{})) {
            if(item.commandName.equals(command.getName())&&sender.hasPermission(item.permission)){
                for (int i = 0; i < item.args.length; i++) {
                    if(!item.args[i].equals(args[i])){
                        continue;
                    }
                }
                if(context==null){
                    context = ChatDirector.formatter.getContext(sender);
                    context.putAll(ChatDirector.formatter.getContext(command));
                    String argsStr = "";
                    if(args.length>1){
                        argsStr=args[0];
                    }
                    for (int i = 1; i < args.length; i++) {
                        argsStr+=", "+args[i];
                    }
                    context.put("ARGS", argsStr);
                }
                item.startWork(ChatDirector.formatter.format(item.format, context), true, context);
            }
        }
        return false;
	}
    
}
