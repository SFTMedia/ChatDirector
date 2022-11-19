package com.blalp.chatdirector.bungee.modules.bungee;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;

import net.md_5.bungee.api.ProxyServer;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@NoArgsConstructor
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
