package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import net.md_5.bungee.api.plugin.Listener;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper=false)
public class BungeeInputItem extends PassItem implements Listener {

	public String formatDisconnect="&0[&4<-&0] &e%PLAYER_NAME% has left the network from %SERVER_NAME%";
    public String formatSwitch="&0[&e<->&0] &e%PLAYER_NAME% has switched to %SERVER_NAME%";
    public String formatChat="[%SERVER_NAME%] %PLAYER_NAME%: %CHAT_MESSAGE%";
    public String formatJoin="&0[&2->&0] &e%PLAYER_NAME% joined the network on %SERVER_NAME%!";
    public boolean disconnect,switchServers,chat,join;
	public boolean command=false;
	public boolean overrideChat=false;

    public BungeeInputItem() {

        if (BungeeInputItemDaemon.instance == null) {
            BungeeInputItemDaemon.instance = new BungeeInputItemDaemon();
        }
        BungeeInputItemDaemon.instance.addItem(this);
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.anyOf(disconnect,switchServers,chat,join,command)&&ValidationUtils.hasContent(formatDisconnect,formatSwitch,formatChat,formatJoin);
    }
}
