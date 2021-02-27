package com.blalp.chatdirector.bungee.modules.bungee;

import java.util.logging.Level;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.minecraft.model.FancyMessage;
import com.blalp.chatdirector.minecraft.utils.FancyMessageEnum;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
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
     * https://www.spigotmc.org/threads/need-help-with-clickable-chat-message.
     * 180366/ https://www.spigotmc.org/wiki/the-chat-component-api/
     * https://hub.spigotmc.org/javadocs/spigot/index.html
     * https://www.spigotmc.org/wiki/bungeecord-plugin-development/
     * https://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/net/md_5/bungee/api
     * /chat/ClickEvent.Action.html
     * https://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/index-all.html
     * http://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/overview-summary.
     * html
     */
    FancyMessage fancyMessage;
    String permission;
    boolean sendToCurrentServer = true;
    String player = null;

    @SuppressWarnings("deprecation")
    public static BaseComponent fromFancyMessage(FancyMessage fancyMessage) {
        BaseComponent output = new TextComponent();
        for (BaseComponent component : TextComponent.fromLegacyText(fancyMessage.getText())) {
            if (ChatDirector.isDebug()) {
                ChatDirector.getLogger().log(Level.WARNING,
                        "appending >" + component.toLegacyText() + "< to >" + output.toLegacyText() + "<");
            }
            output.addExtra(component);
            if (ChatDirector.isDebug()) {
                ChatDirector.getLogger().log(Level.WARNING, "output is now >" + output.toLegacyText() + "<");
            }
        }
        if (fancyMessage.isBold()) {
            output.setBold(true);
        }
        if (fancyMessage.getColor() != null) {
            output.setColor(ChatColor.valueOf(fancyMessage.getColor()));
        }
        if (fancyMessage.isItalics()) {
            output.setItalic(true);
        }
        if (fancyMessage.isObfuscated()) {
            output.setObfuscated(true);
        }
        if (fancyMessage.isStrikethrough()) {
            output.setStrikethrough(true);
        }
        if (fancyMessage.getClick() != FancyMessageEnum.NONE) {
            output.setClickEvent(
                    new ClickEvent(ClickEvent.Action.valueOf(fancyMessage.getClick().toString()), fancyMessage.getClickData()));
        }
        if (fancyMessage.getHover() == FancyMessageEnum.SHOW_TEXT) {
            output.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(fancyMessage.getHover().toString()),
                    new Text(fancyMessage.getHoverData())));
        }
        for (FancyMessage message : fancyMessage.getNext()) {
            output.addExtra(fromFancyMessage(message));
        }
        return output;
    }

    @Override
    public Context process(Context context) {
        Context playerContext = new Context(context);
        // Resolve all of the contexts that you can before resolving player related ones
        FancyMessage fancyBase = fancyMessage.duplicate().withContext(context);
        for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers()) {
            if (proxiedPlayer == null
                    || (ChatDirector.format(player, context).length() > 16
                            && proxiedPlayer.getUniqueId().toString().equals(ChatDirector.format(player, context)))
                    || (ChatDirector.format(player, context).length() < 16
                            && proxiedPlayer.getName().equals(ChatDirector.format(player, context)))) {
                if (permission != null && !proxiedPlayer.hasPermission(permission)) {
                    continue;
                }
                if (!sendToCurrentServer && context.containsKey("SERVER_NAME")) {
                    if (proxiedPlayer.getServer() != null && proxiedPlayer.getServer().getInfo() != null
                            && proxiedPlayer.getServer().getInfo().getName().equals(context.get("SERVER_NAME"))) {
                        if (ChatDirector.isDebug()) {
                            ChatDirector.getLogger().log(Level.WARNING,
                                    "Server name matches player (" + proxiedPlayer.getName() + ") server >"
                                            + proxiedPlayer.getServer().getInfo().getName() + "< to context server >"
                                            + context.get("SERVER_NAME") + "< not sending... ");
                        }
                        continue;
                    }
                }
                playerContext.merge(BungeeModule.instance.getContext(proxiedPlayer));
                BaseComponent message = fromFancyMessage(fancyBase.duplicate().withContext(playerContext));
                // Since we want to do context resolution per player we need to duplicate
                proxiedPlayer.sendMessage(message);
                if (ChatDirector.isDebug()) {
                    ChatDirector.getLogger().log(Level.WARNING,
                            "Sent >" + message.toLegacyText() + "< to " + proxiedPlayer.getName());
                }
                playerContext = new Context(context);
            }
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.isNotNull(fancyMessage);
    }
}
