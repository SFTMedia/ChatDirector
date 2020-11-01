package com.blalp.chatdirector.modules.sponge;

import java.util.ArrayList;
import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.ChatDirectorSponge;
import com.blalp.chatdirector.configuration.Configuration;
import com.blalp.chatdirector.model.ILoadable;
import com.blalp.chatdirector.modules.common.PassItem;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.command.spec.CommandSpec.Builder;
import org.spongepowered.api.text.Text;

public class SpongeCommandItem extends PassItem implements CommandExecutor, ILoadable {
    String command;
    String permission;
	public boolean args=false;
    public static ArrayList<SpongeCommandItem> commands = new ArrayList<>();
    public SpongeCommandItem(String name,String permission){
        this.command=name;
        this.permission=permission;
        commands.add(this);
    }
    
    @Override
    public void load() {
        if(Configuration.debug) {
            System.out.println("Starting load of "+this);
        }
        Builder myCommandSpec = CommandSpec.builder()
            .permission(permission)
            .executor(this);
        if (args) {
            myCommandSpec.arguments(GenericArguments.remainingRawJoinedStrings(Text.of("args")));
        }
        if(!Sponge.getCommandManager().containsAlias(command)){
            Sponge.getCommandManager().register(ChatDirectorSponge.instance, myCommandSpec.build(), command);
        }else {
            System.err.println("Alias "+command+" in use");
        }
    }

    @Override
    public void unload() {
        if(Configuration.debug) {
            System.out.println("Starting unload of "+this);
        }
        if(Sponge.getCommandManager().get(command).isPresent()){
            Sponge.getCommandManager().removeMapping(Sponge.getCommandManager().get(command).get());
        } else {
            System.err.println("Mapping was not found");
        }
        commands=new ArrayList<>();
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
        String output = command;
        if(args.<String>getOne(Text.of("args")).isPresent()){
            output=args.<String>getOne(Text.of("args")).get();
            context.put("COMMAND_ARGS", args.<String>getOne(Text.of("args")).get());
        }
        context.put("COMMAND_NAME", command);
        context.put("COMMAND_PERMISSION", permission);
        startWork(output, true, context);
        return CommandResult.success();
    }
}
