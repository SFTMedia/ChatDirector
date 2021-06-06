package com.blalp.chatdirector.core.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Map.Entry;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.ILegacyModule;
import com.blalp.chatdirector.core.model.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(using = LegacyConfigurationDeserializer.class)
public class LegacyConfiguration {
    boolean debug;
    String version;
    @JsonIgnore
    ServiceLoader<ILegacyModule> modules;
    Map<String, LegacyChain> chains = new HashMap<String, LegacyChain>();
    // This is for storage of generic keys that modules may need.
    // The first key is the module name
    Map<String, Map<String, String>> moduleData = new HashMap<>();

    public LegacyConfiguration() {
        modules = ServiceLoader.load(ILegacyModule.class, this.getClass().getClassLoader());
    }

    public LegacyConfiguration updateTo(Version version) {
        LegacyConfiguration output = new LegacyConfiguration();
        if (ChatDirector.getInstance().getLegacyConfig().isDebug()){
            ChatDirector.getLogger().info("Legacy Modules:");
            for (ILegacyModule iLegacyModule : modules) {
                ChatDirector.getLogger().info("\t"+iLegacyModule);
            }
        }
        output.setDebug(debug);
        output.moduleData.putAll(moduleData);
        output.version=version.toString();
        for (Entry<String, LegacyChain> chain : chains.entrySet()) {
            if(chain.getValue()!=null){
                output.chains.put(chain.getKey(), chain.getValue().updateTo(version));
            } else {
                ChatDirector.getLogger().warning("Chain "+chain.getValue()+" was null at configuration update");
            }
        }
        return output;
    }
    public Class<?> getLegacyItemClass(String itemType, Version version) {
        for (ILegacyModule module : modules) {
            if (module.getItemNames(version).contains(itemType)) {
                return module.getItemClass(itemType,version);
            }
        }
        return null;
    }
}
