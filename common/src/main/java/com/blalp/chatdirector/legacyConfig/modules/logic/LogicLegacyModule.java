package com.blalp.chatdirector.legacyConfig.modules.logic;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class LogicLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("if-contains", "if-equals", "if-regex-match", "split", "if-starts-with", "if-ends-with");

    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "if-contains":
            return IfContainsItem_v0_2_0.class;
        case "if-equals":
            return IfEqualsItem_v0_2_0.class;
        case "if-regex-match":
            return IfRegexMatchesItem_v0_2_0.class;
        case "split":
            if (version.compareTo(new SplitItem_v0_2_0().nextUpdateVersion()) > 0) {
                return SplitItem_v0_2_5.class;
            } else {
                return SplitItem_v0_2_0.class;
            }
        case "if-starts-with":
            return IfStartsWithItem_v0_2_0.class;
        case "if-ends-with":
            return IfEndsWithItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
