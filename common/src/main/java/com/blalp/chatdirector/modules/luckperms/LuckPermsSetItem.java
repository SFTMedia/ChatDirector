package com.blalp.chatdirector.modules.luckperms;

import java.util.UUID;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class LuckPermsSetItem extends PassItem {
    String permission;
    boolean value=true;
    public LuckPermsSetItem(String permission) {
        this.permission=permission;
    }
    @Override
    public Context process(Context context) {
        // DO NOT RESOLVE CONTEXTS ON THE PERMISSION NODE
        if(context.containsKey("PLAYER_UUID")) {
            User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID")));
            user.data().add(Node.builder(permission).value(value).build());
            LuckPermsProvider.get().getUserManager().saveUser(user);
        } else {
            System.err.println("PLAYER_UUID not set");
        }
        return new Context();
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(permission);
    }
}
