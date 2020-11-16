package com.blalp.chatdirector.modules.cache;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

public class CacheSetItem extends CacheItem {
    String value;
	public CacheSetItem(String key, String value) {
		super(key);
        this.value=value;
	}

	@Override
	public Context process(Context context) {
		CacheStore.setValue(ChatDirector.format(key, context), ChatDirector.format(value, context));
		return new Context();
	}
	@Override
	public boolean isValid() {
		if(value==null){
			return false;
		}
		return super.isValid();
	}
}
