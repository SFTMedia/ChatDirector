package com.blalp.chatdirector.core.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = LegacyConfigurationDeserializer.class)
public class LegacyConfiguration {
    boolean debug;
    Map<String, LegacyChain> chains = new HashMap<String, LegacyChain>();
    // This is for storage of generic keys that modules may need.
    // The first key is the module name
    Map<String, Map<String, String>> moduleData = new HashMap<>();

    public LegacyConfiguration updateTo(Version version) {
        LegacyConfiguration output = new LegacyConfiguration();
        output.setDebug(debug);
        output.moduleData.putAll(moduleData);
        for (Entry<String, LegacyChain> chain : chains.entrySet()) {
            output.chains.put(chain.getKey(), chain.getValue().updateTo(version));
        }
        return output;
    }
}
