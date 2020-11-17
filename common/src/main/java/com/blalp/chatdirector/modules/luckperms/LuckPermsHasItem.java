package com.blalp.chatdirector.modules.luckperms;

import java.util.UUID;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.logic.ConditionalItem;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeEqualityPredicate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@JsonDeserialize(using = LuckPermsHasDeserializer.class)
public class LuckPermsHasItem extends ConditionalItem {
    String permission;

    @Override
    public boolean test(Context context) {
        return LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID"))).data().contains(Node.builder(permission).build(),NodeEqualityPredicate.ONLY_KEY).asBoolean();
    }
}
