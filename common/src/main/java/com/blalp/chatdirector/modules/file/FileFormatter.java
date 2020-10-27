package com.blalp.chatdirector.modules.file;

import java.util.HashMap;
import java.util.Map;

import com.blalp.chatdirector.internalModules.format.Formatter;

public class FileFormatter extends Formatter {

    @Override
    public Map<String, String> getContext(Object event) {
        HashMap<String,String> output = new HashMap<>();
        if(event instanceof String){
            output.put("PATH", (String)event);
        }
        return output;
    }
    
}
