package com.blalp.chatdirector.modules.bungee;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Item;
import com.blalp.chatdirector.model.fancychat.FancyMessage;
import com.blalp.chatdirector.model.fancychat.FancyMessageEnum;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeOutputFancyItem extends Item {
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
    FancyMessage fancyMessage;
    String permission;
    public BungeeOutputFancyItem(FancyMessage fancyMessage,String permission){
        this.fancyMessage=fancyMessage;
        this.permission=permission;
    }
    public static BaseComponent fromFancyMessage(FancyMessage fancyMessage){
        BaseComponent output = new TextComponent(fancyMessage.text);
        if(fancyMessage.bold){
            output.setBold(true);
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
    public String process(String string, Map<String, String> context) {
        Map<String,String> playerContext = context;
        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
            if(player.hasPermission(permission)){
                playerContext.putAll(ChatDirector.getContext(player));
                player.sendMessage(fromFancyMessage(fancyMessage.duplicate().withContext(playerContext))); // Since we want to do context resolution per player we need to duplicate
                playerContext=context;
            }
        }
        return string;
    }
}
