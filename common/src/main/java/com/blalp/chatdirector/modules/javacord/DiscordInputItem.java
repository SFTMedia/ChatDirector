package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.utils.ValidationUtils;

public class DiscordInputItem extends DiscordItem {
	public boolean reactionAdd=false;
	public boolean reactionRemove=false;
    public boolean message=false;
    String channelID,categoryID,messageID,format;

    public DiscordInputItem(String botName) {
        super(botName);
    }

    @Override
    public boolean isValid() {
        if(!super.isValid()||format==null||format.isBlank()){
            return false;
        }
        if(message){
            return ValidationUtils.anyOf(ValidationUtils.hasContent(channelID),ValidationUtils.hasContent(categoryID));
        } else if (reactionAdd||reactionRemove) {
            return ValidationUtils.anyOf(ValidationUtils.hasContent(channelID),ValidationUtils.hasContent(categoryID),ValidationUtils.hasContent(messageID));
        } else {
            return false;
        }
    }

}
