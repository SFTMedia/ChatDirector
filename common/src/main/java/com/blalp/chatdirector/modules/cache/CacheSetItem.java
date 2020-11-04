package com.blalp.chatdirector.modules.cache;

import java.util.Map;

import com.blalp.chatdirector.ChatDirector;

public class CacheSetItem extends CacheItem {
    String value;
	public CacheSetItem(String key, String value) {
		super(key);
        this.value=value;
	}

	@Override
	public String process(String string, Map<String, String> context) {
        context.put("CURRENT", string);
		CacheStore.setValue(ChatDirector.format(key, context), ChatDirector.format(value, context));
		return string;
	}
}
