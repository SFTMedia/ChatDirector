package com.blalp.chatdirector.common.modules.logic;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IModule;

public class LogicModule implements IModule {

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean unload() {
        return true;
    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("if-contains", "if-equals", "if-regex-match", "split", "if-starts-with", "if-ends-with");
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }

    @Override
    public Class<? extends IItem> getItemClass(String type) {
        switch (type) {
            case "if-contains":
                return IfContainsItem.class;
            case "if-equals":
                return IfEqualsItem.class;
            case "if-regex-match":
                return IfRegexMatchesItem.class;
            case "split":
                return SplitItem.class;
            case "if-starts-with":
                return IfStartsWithItem.class;
            case "if-ends-with":
                return IfEndsWithItem.class;
            default:
                return null;
        }
    }

}
