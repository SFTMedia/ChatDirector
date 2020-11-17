package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.fancychat.FancyMessage;
import com.blalp.chatdirector.model.fancychat.FancyMessageEnum;
import com.blalp.chatdirector.utils.ValidationUtils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
public class BungeeOutputFancyItem implements IItem {
    /*
            - bungee-output-fancy:
                permission: null
                fancy-format:
                    - click:
                        click-file: "PATH"
                        click-change-page: page_num
                        click-run-command: "/test"
                        click-suggest-command: "/suggestion"
                        click-url: "https://sftmc.org"
                        text: "text to show" # This can also be a list of fancy formats
                    - color:
                        color: "RED"
                        bold: false
                        italics: true
                        strikethrough: true
                        obfuscated: false
                        text: "TEST" # This can also be a list of fancy formats
                    - hover:
                        //show-achievement: "name" NOPE Cant do easily
                        //show-entity: "name" NOPE Cant do easily
                        //show-item: "name" NOPE Cant do easily
                        show-text: "text"
                        text: "TEST" # This can also be a list of fancy formats
                        
        */
/*
        https://www.spigotmc.org/threads/need-help-with-clickable-chat-message.180366/
        https://www.spigotmc.org/wiki/the-chat-component-api/
        https://hub.spigotmc.org/javadocs/spigot/index.html
        https://www.spigotmc.org/wiki/bungeecord-plugin-development/
        https://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/net/md_5/bungee/api/chat/ClickEvent.Action.html
        https://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/index-all.html
        http://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/overview-summary.html
*/
    FancyMessage fancyFormat;
    String permission;
    boolean sendToCurrentServer = true;
	public String player=null;
    public BungeeOutputFancyItem(FancyMessage fancyMessage,String permission){
        this.fancyFormat=fancyMessage;
        this.permission=permission;
    }
    @SuppressWarnings("deprecation")
    public static BaseComponent fromFancyMessage(FancyMessage fancyMessage){
        BaseComponent output = new TextComponent();
        for (BaseComponent component : TextComponent.fromLegacyText(fancyMessage.text)){
            ChatDirector.logDebug("appending >"+component.toLegacyText()+"< to >"+output.toLegacyText()+"<");
            output.addExtra(component);
            ChatDirector.logDebug("output is now >"+output.toLegacyText()+"<");
        }
        if(fancyMessage.bold){
            output.setBold(true);
        }
        if(fancyMessage.color!=null){
            output.setColor(ChatColor.valueOf(fancyMessage.color));
        }
        if(fancyMessage.italics){
            output.setItalic(true);
        }
        if(fancyMessage.obfuscated){
            output.setObfuscated(true);
        }
        if(fancyMessage.strikethrough){
            output.setStrikethrough(true);
        }
        if(fancyMessage.click!=FancyMessageEnum.NONE){
            output.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(fancyMessage.click.toString()), fancyMessage.clickData));
        }
        if(fancyMessage.hover==FancyMessageEnum.SHOW_TEXT){
            output.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(fancyMessage.hover.toString()), new Text(fancyMessage.hoverData)));
        }
        for(FancyMessage message : fancyMessage.next){
            output.addExtra(fromFancyMessage(message));
        }
        return output;
    }
    
    @Override
    public Context process(Context context) {
        Context playerContext = new Context(context);
        FancyMessage fancyBase = fancyFormat.duplicate().withContext(context); // Resolve all of the contexts that you can before resolving player related ones
        for(ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers()){
            if(proxiedPlayer==null||(ChatDirector.format(player, context).length()>16&&proxiedPlayer.getUniqueId().toString().equals(ChatDirector.format(player, context)))||(ChatDirector.format(player, context).length()<16&&proxiedPlayer.getName().equals(ChatDirector.format(player, context)))) {
                if(permission!=null&&!proxiedPlayer.hasPermission(permission)) {
                    continue;
                }
                if(!sendToCurrentServer&&context.containsKey("SERVER_NAME")){
                    if(proxiedPlayer.getServer()!=null&&proxiedPlayer.getServer().getInfo()!=null&&proxiedPlayer.getServer().getInfo().getName().equals(context.get("SERVER_NAME"))){
                        ChatDirector.logDebug("Server name matches player ("+proxiedPlayer.getName()+") server >"+proxiedPlayer.getServer().getInfo().getName()+"< to context server >"+context.get("SERVER_NAME")+"< not sending... ");
                        continue;
                    }
                }
                playerContext.merge(BungeeModule.instance.getContext(proxiedPlayer));
                BaseComponent message = fromFancyMessage(fancyBase.duplicate().withContext(playerContext));
                proxiedPlayer.sendMessage(message); // Since we want to do context resolution per player we need to duplicate
                ChatDirector.logDebug("Sent >"+message.toLegacyText()+"< to "+proxiedPlayer.getName());
                playerContext=new Context(context);
            }
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(fancyFormat);
    }
}
