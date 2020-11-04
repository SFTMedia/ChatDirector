package com.blalp.chatdirector.modules.luckperms;

import java.util.Map;
import java.util.UUID;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.logic.ConditionalItem;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeEqualityPredicate;

public class LuckPermsHasItem extends ConditionalItem {
    public LuckPermsHasItem(IItem nestedTrue, IItem nestedFalse,String permission) {
        super(nestedTrue, nestedFalse);
        this.permission=permission;
    }

    String permission;
    boolean value=true;

    @Override
    public boolean test(String string, Map<String, String> context) {
        return LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID"))).data().contains(Node.builder(permission).value(value).build(),NodeEqualityPredicate.ONLY_KEY).asBoolean();
    }
}
