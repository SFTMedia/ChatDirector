package com.blalp.chatdirector.modules.bungee;
import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeOutputServerItem implements IItem {
    public String server;
    public String permission;
    public BungeeOutputServerItem(String server){
        this.server=server;
    }
    @SuppressWarnings("deprecation")
    @Override
    public Context process(Context context) {
        for(ProxiedPlayer player:ProxyServer.getInstance().getPlayers()) {
            if(player.getServer()!=null&&player.getServer().getInfo()!=null&&player.getServer().getInfo().getName().equals(ChatDirector.format(server, context))){
                if(permission==null||(permission!=null&player.hasPermission(ChatDirector.format(permission, context)))){
                    player.sendMessage(ChatDirector.format(context));
                }
            }
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(server);
    }
    
}
