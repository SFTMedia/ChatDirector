package com.blalp.chatdirector.internalModules.format;

import java.util.Map;

public interface IFormatter {
    public String format(String input, Map<String,String> context);
	public Map<String, String> getContext(Object event);
}
