package com.blalp.chatdirector.platform.sponge;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.configuration.TimedLoad;
import com.blalp.chatdirector.model.ILoadable;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

public class ReloadCommand implements CommandExecutor, ILoadable {
    @Override
    public boolean load() {
        ChatDirector.logDebug("Starting load of " + this);
        Sponge.getCommandManager().register(ChatDirectorSponge.instance,
                CommandSpec.builder().permission("chatdirectorlocal.commands.reload").executor(this).build(),
                "chatdirectorlocal");
        return true;
    }

    @Override
    public boolean unload() {
        ChatDirector.logDebug("Starting unload of " + this);
        Sponge.getCommandManager().removeMapping(Sponge.getCommandManager().get("chatdirectorlocal").get());
        return true;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        new Thread(new TimedLoad()).start();
        return CommandResult.success();
    }
}
