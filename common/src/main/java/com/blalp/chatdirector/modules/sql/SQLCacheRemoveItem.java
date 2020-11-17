package com.blalp.chatdirector.modules.sql;
import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper=false)
public class SQLCacheRemoveItem extends SQLItem {
    @Override
    public Context process(Context context) {
        if(SQLCacheStore.containsKey(connection, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context))) {
            SQLCacheStore.removeValue(connection, ChatDirector.format(table,context), ChatDirector.format(name,context), ChatDirector.format(key,context));
            ChatDirector.logDebug("Cache Hit "+connection+" "+ChatDirector.format(table,context)+" "+ChatDirector.format(name,context)+" "+ChatDirector.format(key,context)+", removing...");
        } else {
            ChatDirector.logDebug("Cache Miss "+connection+" "+ChatDirector.format(table,context)+" "+ChatDirector.format(name,context)+" "+ChatDirector.format(key,context));
        }
        return new Context();
    }
}
