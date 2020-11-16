package com.blalp.chatdirector.model;

import java.util.Map.Entry;

import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IConfiguration extends ILoadable {
    public Chain loadChain(ObjectMapper om, JsonNode module);
    public IModule loadModule(ObjectMapper om, Entry<String, JsonNode> module);
    public IItem loadItem(ObjectMapper om, Chain chain, String key, JsonNode item);
    
}
