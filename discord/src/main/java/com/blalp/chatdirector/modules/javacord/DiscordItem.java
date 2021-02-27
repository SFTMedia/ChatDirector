package com.blalp.chatdirector.modules.javacord;

import com.blalp.chatdirector.modules.common.PassItem;
import com.blalp.chatdirector.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscordItem extends PassItem {
	String bot;

	@Override
	public boolean isValid() {
		return ValidationUtils.hasContent(bot);
	}
}