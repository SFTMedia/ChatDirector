package com.blalp.chatdirector.modules.cache;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class CacheSetItem extends CacheItem {
    String value;

	@Override
	public Context process(Context context) {
		CacheStore.setValue(ChatDirector.format(key, context), ChatDirector.format(value, context));
		return new Context();
	}
	@Override
	public boolean isValid() {
		return ValidationUtils.hasContent(value)&&super.isValid();
	}
}
