package com.blalp.chatdirector.modules.sponge;

import java.util.ArrayList;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.ILoadable;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.platform.sponge.ChatDirectorSponge;
import com.blalp.chatdirector.utils.ValidationUtils;

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
        ChatDirector.logDebug("Starting load of "+this);
        Builder myCommandSpec = CommandSpec.builder().permission(permission).executor(this);
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
        ChatDirector.logDebug("Starting unload of "+this);
        if(Sponge.getCommandManager().get(command).isPresent()){
            Sponge.getCommandManager().removeMapping(Sponge.getCommandManager().get(command).get());
        } else {
            System.err.println("Mapping was not found");
        }
        commands=new ArrayList<>();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Context context = SpongeModule.instance.getContext(src);
        context.merge(SpongeModule.instance.getContext(args));
        String output = command;
        if(args.<String>getOne(Text.of("args")).isPresent()){
            output=args.<String>getOne(Text.of("args")).get();
            context.put("COMMAND_ARGS", args.<String>getOne(Text.of("args")).get());
        }
        context.put("COMMAND_NAME", command);
        context.put("COMMAND_PERMISSION", permission);
        context.put("CURRENT", output);
        ChatDirector.run(this, context, true);
        return CommandResult.success();
    }
    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(command,permission);
    }
}
