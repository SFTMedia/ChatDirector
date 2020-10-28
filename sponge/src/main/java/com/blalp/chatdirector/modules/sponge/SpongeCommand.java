package com.blalp.chatdirector.modules.sponge;

import java.util.ArrayList;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.ChatDirectorSponge;
import com.blalp.chatdirector.model.ILoadable;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

public class SpongeCommand implements CommandExecutor, ILoadable {
    static ArrayList<SpongeCommand> commands = new ArrayList<>();
    SpongeCommandItem item;

    public SpongeCommand(String name,SpongeCommandItem item) {
        this.item=item;
        commands.add(this);
    }

    @Override
    public void load() {
        CommandSpec myCommandSpec = CommandSpec.builder()
            .permission(item.permission)
            .executor(this)
            .build();
        if(!Sponge.getCommandManager().containsAlias(item.command)){
            Sponge.getCommandManager().register(ChatDirectorSponge.instance, myCommandSpec, item.command);
        }
    }

    @Override
    public void unload() {
    }

    @Override
    public void reload() {
        unload();
        load();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Map<String, String> context = ChatDirector.formatter.getContext(src);
        context.putAll(ChatDirector.formatter.getContext(args));
        context.put("COMMAND_ARGS", args.toString());
        context.put("COMMAND_NAME", item.command);
        context.put("COMMAND_PERMISSION", item.permission);
        item.startWork(item.command, true, context);
        return CommandResult.success();
    }
    
}
