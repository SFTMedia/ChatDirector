package com.blalp.chatdirector.bukkit.modules.bukkit;

import java.util.UUID;
import java.util.logging.Level;

import com.blalp.chatdirector.bukkit.ChatDirectorBukkit;
import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Data;

@Data
public class BukkitExecuteItem implements IItem {

    String command;
    // UUID of player to execute as
    String sender;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(command);
    }

    @Override
    public Context process(Context context) {
        CommandSender sender;
        if (this.sender == null) {
            sender = Bukkit.getConsoleSender();
        } else {
            sender = Bukkit.getPlayer(UUID.fromString(this.sender));
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                if (!Bukkit.getServer().dispatchCommand(sender, command)) {
                    ChatDirector.getLogger().log(Level.WARNING, "Invalid player UUID '" + sender + "'");
                }
            }
        }.runTask(ChatDirectorBukkit.getInstance());
        return new Context();
    }

}
