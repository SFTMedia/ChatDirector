package com.blalp.chatdirector.modules.luckperms;

import java.util.Map;
import java.util.UUID;

import com.blalp.chatdirector.modules.common.PassItem;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;

public class LuckPermsSetItem extends PassItem {
    String permission;
    public LuckPermsSetItem(String permission) {
        this.permission=permission;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        // DO NOT RESOLVE CONTEXTS ON THE PERMISSION NODE
        if(context.containsKey("PLAYER_UUID")) {
            LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID"))).data().add(Node.builder(permission).build());
        } else {
            System.err.println("PLAYER_UUID not set");
        }
        return super.process(string, context);
    }
}
