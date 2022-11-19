package com.blalp.chatdirector.common.modules.cache;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CacheSetItem extends CacheItem {
	String value;

	@Override
	public Context process(Context context) {
		CacheStore cacheStore = (CacheStore) ChatDirector.getConfig().getOrCreateDaemon(CacheStore.class);
		cacheStore.setValue(ChatDirector.format(this.key, context), ChatDirector.format(value, context));
		return new Context();
	}

	@Override
	public boolean isValid() {
		return ValidationUtils.hasContent(value) && super.isValid();
	}
}
