package com.blalp.chatdirector.modules.logic;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.modules.IModule;

public class LogicModule implements IModule {

    @Override
    public void load() {

    }

    @Override
    public void unload() {

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
    public Class<?> getItemClass(String type) {
        switch (type) {
            case "if-contains":
                return IfContainsItem.class;
            case "if-equals":
                return IfEqualsItem.class;
            case "if-regex-match":
                return IfEqualsItem.class;
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
