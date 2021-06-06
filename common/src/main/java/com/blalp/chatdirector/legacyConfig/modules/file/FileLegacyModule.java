package com.blalp.chatdirector.legacyConfig.modules.file;

import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.core.model.ILegacyItem;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;

public class FileLegacyModule implements ILegacyModule {

    @Override
    public List<String> getItemNames(Version version) {
        return Arrays.asList("file-input", "file-output");
    }

    @Override
    public Class<? extends ILegacyItem> getItemClass(String type, Version version) {
        switch (type) {
        case "file-input":
            return FileInputItem_v0_2_0.class;
        case "file-output":
            return FileOutputItem_v0_2_0.class;
        default:
            return null;
        }
    }

}
