package com.blalp.chatdirector.legacyConfig.modules.file;

import java.io.BufferedWriter;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FileOutputItem_v0_2_0 implements ILegacyItem {
    String path;
    boolean create = false;
    int delay = 500;
    @JsonIgnore
    BufferedWriter writer;
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
        return "file-output";
    }
    
}
