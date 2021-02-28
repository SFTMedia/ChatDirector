package com.blalp.chatdirector.common.modules.file;

import java.util.ArrayList;
import java.util.List;

import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.utils.ItemDaemon;

public class FileInputDaemon extends ItemDaemon {
    List<PerFileInputDaemon> perFileInputDaemons = new ArrayList<>();

    @Override
    public void addItem(IItem item) {
        perFileInputDaemons.add(new PerFileInputDaemon(item));
        super.addItem(item);
    }
    @Override
    public boolean load() {
        boolean result = true;
        for(PerFileInputDaemon daemon:perFileInputDaemons){
            new Thread(daemon).start();
            result=result && daemon.load();
        }
        result=result&&super.load();
        return result;
    }
    @Override
    public boolean unload() {
        boolean result = true;
        for(PerFileInputDaemon daemon:perFileInputDaemons){
            result=result && daemon.unload();
        }
        result=result&&super.unload();
        return result;
    }

}