package com.blalp.chatdirector.modules;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.ILoadable;
import com.blalp.chatdirector.model.IValid;

public interface IModule extends ILoadable, IValid {
    public List<String> getItemNames();
    public IItem createItem(ObjectMapper om, Chain chain,String type, JsonNode config);
    public Context getContext(Object object);
}