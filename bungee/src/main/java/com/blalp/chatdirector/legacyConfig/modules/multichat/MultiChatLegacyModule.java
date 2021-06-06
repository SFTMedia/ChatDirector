package com.blalp.chatdirector.legacyConfig.modules.multichat;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class MultiChatLegacyModule implements ILegacyModule {
    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("multichat-input", "multichat-context");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "multichat-input":
            return MultiChatInputItem_v0_2_0.class;
        case "multichat-context":
            return MultiChatContextItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
