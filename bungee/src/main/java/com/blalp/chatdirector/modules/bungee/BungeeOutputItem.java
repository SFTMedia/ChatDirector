package com.blalp.chatdirector.modules.bungee;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;

import net.md_5.bungee.api.ProxyServer;

public class BungeeOutputItem implements IItem {

    @SuppressWarnings("deprecation")
    @Override
    public Context process(Context context) {
        ProxyServer.getInstance().broadcast(ChatDirector.format(context));
        return new Context();
    }

    @Override
	public boolean isValid() {
		return true;
	}
    
}
