package com.blalp.chatdirector.extra.modules.luckperms;

import java.util.UUID;

import com.blalp.chatdirector.common.modules.logic.ConditionalItem;
import com.blalp.chatdirector.core.model.Context;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeEqualityPredicate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = LuckPermsHasDeserializer.class)
@ToString(callSuper = true)
public class LuckPermsHasItem extends ConditionalItem {
    String permission;

    @Override
    public boolean test(Context context) {
        return LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(context.get("PLAYER_UUID"))).data()
                .contains(Node.builder(permission).build(), NodeEqualityPredicate.ONLY_KEY).asBoolean();
    }
}
