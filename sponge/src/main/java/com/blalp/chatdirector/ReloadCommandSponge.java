package com.blalp.chatdirector;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class ReloadCommandSponge implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Starting Sponge ChatDirector reload"));
        ChatDirector.instance.reload();
        src.sendMessage(Text.of("Finished Sponge ChatDirector reload"));
        return CommandResult.success();
    }
    
}
