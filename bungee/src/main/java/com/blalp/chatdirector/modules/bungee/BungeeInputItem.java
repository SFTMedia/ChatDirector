package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.modules.common.PassItem;

import net.md_5.bungee.api.plugin.Listener;

public class BungeeInputItem extends PassItem implements Listener {

	public String disconnectFormat="&0[&4<-&0] &e%PLAYER_NAME% has left the network from %SERVER_NAME%, Have a good day!";
    public String formatSwitch="&0[&e<->&0] &e%PLAYER_NAME% has switched to %SERVER_NAME%";
    public String formatChat="[%SERVER_NAME%] %PLAYER_NAME%: %CHAT_MESSAGE%";
    public String formatJoin="&0[&2->&0] &e%PLAYER_NAME% joined the network on %SERVER_NAME%!";
    public boolean disconnect,switchServers,chat,joinServer;
	public boolean command=false;
}
