package com.blalp.chatdirector.proximitychat.modules.proximitychat;

import com.blalp.chatdirector.extra.modules.sql.SQLRetrieveDataItem;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

// Later this entire item will be replaced with a generic for loop, for now though we need this.
public class ConvertUUIDToDiscordId implements IItem {
    // Passthrough to the SQ: module
    String table, connection;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(table, connection);
    }

    @Override
    public Context process(Context context) {
        Context output = new Context();
        Context players = context.getContextAtPath("PROXIMITY_CHAT_GROUP_");
        for (String player : players.keySet()) {
            SQLRetrieveDataItem sql = new SQLRetrieveDataItem();
            sql.setConnection(connection);
            sql.setCache(true);
            sql.setTable(table);
            sql.setKey(player);
            sql.setName("Discord ID");
            Context sqlContext = sql.process(context);
            if (sqlContext.containsKey("SQL_RESULT")) {
                output.put("PROXIMITY_CHAT_GROUP_" + sqlContext.get("SQL_RESULT"), players.get(player));
            } else {
                // SQL failed.
                output.put("SQL ERROR!", "On player " + player);
                return output;
            }
        }
        return output;
    }
}
