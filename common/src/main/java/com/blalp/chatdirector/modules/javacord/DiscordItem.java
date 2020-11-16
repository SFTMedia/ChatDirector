package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;
public class DiscordItem extends PassItem {
	public String botName;
	
	public DiscordItem(String botName){
		this.botName=botName;
	}

	@Override
	public boolean isValid() {
		return ValidationUtils.hasContent(botName);
	}
}