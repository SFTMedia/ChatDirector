package com.blalp.chatdirector.modules.sql;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.logic.ConditionalItem;
import com.blalp.chatdirector.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = SQLCacheIfDeserializer.class)
public class SQLCacheIfItem extends ConditionalItem {
    public String table, name, key, connection;
    public boolean cache;

    @Override
    public boolean test(Context context) {
        return SQLCacheStore.containsKey(connection, ChatDirector.format(table, context),
                ChatDirector.format(name, context), ChatDirector.format(key, context));
    }

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(table, name, key, connection) && super.isValid();
    }

}
