package com.blalp.chatdirector.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.blalp.chatdirector.model.BaseConfiguration;
import com.blalp.chatdirector.model.Loadable;
import com.blalp.chatdirector.modules.IModule;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Configuration extends Loadable {

    String fileName;

    public Configuration(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load() {
        Constructor constructor = new Constructor(BaseConfiguration.class);
        TypeDescription typeDescription = new TypeDescription(BaseConfiguration.class);
        typeDescription.putMapPropertyType("modules", String.class, IModule.class);
        constructor.addTypeDescription(typeDescription);
        Yaml yaml = new Yaml(constructor);
        try {
            BaseConfiguration configuration = (BaseConfiguration) yaml.load(new FileReader(fileName));
            System.out.println(configuration.debug);
        } catch (FileNotFoundException e) {
            System.err.println("CONFIG NOT FOUND!");
            e.printStackTrace();
        }
    }

    @Override
    public void unload() {

    }
    
}