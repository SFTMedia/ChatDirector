package com.blalp.chatdirector.extra.modules.sql;

import java.util.logging.Level;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class SQLCacheRemoveItem extends SQLItem {
	@Override
	public Context process(Context context) {
		SQLCacheStore sqlCacheStore = (SQLCacheStore) ChatDirector.getConfig().getOrCreateDaemon(SQLCacheStore.class);
		if (sqlCacheStore.containsKey(connection, ChatDirector.format(table, context),
				ChatDirector.format(name, context), ChatDirector.format(key, context))) {
			sqlCacheStore.removeValue(connection, ChatDirector.format(table, context),
					ChatDirector.format(name, context), ChatDirector.format(key, context));
			if (ChatDirector.getConfig().isDebug()){
				ChatDirector.getLogger().log(Level.WARNING,
						"Cache Hit " + connection + " " + ChatDirector.format(table, context) + " "
								+ ChatDirector.format(name, context) + " " + ChatDirector.format(key, context)
								+ ", removing...");
			}
		} else {
			if (ChatDirector.getConfig().isDebug()){
				ChatDirector.getLogger().log(Level.WARNING,
						"Cache Miss " + connection + " " + ChatDirector.format(table, context) + " "
								+ ChatDirector.format(name, context) + " " + ChatDirector.format(key, context));
			}
		}
		return new Context();
	}
}
