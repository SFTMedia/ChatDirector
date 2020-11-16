package com.blalp.chatdirector.modules.luckperms;

import java.util.UUID;

import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.logic.ConditionalItem;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeEqualityPredicate;

public class LuckPermsHasItem extends ConditionalItem {
    public LuckPermsHasItem(Chain nestedTrue, Chain nestedFalse,String permission) {
        super(nestedTrue, nestedFalse);
        this.permission=permission;
    }

    String permission;

    @Override
    public boolean test(Context context) {
        return LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID"))).data().contains(Node.builder(permission).build(),NodeEqualityPredicate.ONLY_KEY).asBoolean();
    }
}
