package com.blalp.chatdirector.modules.luckperms;

import java.util.Map;
import java.util.UUID;

import com.blalp.chatdirector.modules.common.PassItem;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class LuckPermsUnsetItem extends PassItem {
    String permission;
    public LuckPermsUnsetItem(String permission) {
        this.permission=permission;
    }
    @Override
    public String process(String string, Map<String, String> context) {
        // DO NOT RESOLVE CONTEXTS ON THE PERMISSION NODE
        if(context.containsKey("PLAYER_UUID")) {
            User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID")));
            user.data().remove(Node.builder(permission).value(true).build());
            LuckPermsProvider.get().getUserManager().saveUser(user);
        } else {
            System.err.println("PLAYER_UUID not set");
        }
        return super.process(string, context);
    }
}
