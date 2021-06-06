package com.blalp.chatdirector.legacyConfig.modules.file;

import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;

import lombok.Data;

@Data
public class FileInputItem_v0_2_0 implements ILegacyItem {
    String path;
    boolean create = false;
    int delay = 500;

    @Override
    public List<ILegacyItem> updateToNextLegacyItems() {
        return null;
    }

    @Override
    public Version nextUpdateVersion() {
        return null;
    }

    @Override
    public String name() {
        return "file-input";
    }

}
