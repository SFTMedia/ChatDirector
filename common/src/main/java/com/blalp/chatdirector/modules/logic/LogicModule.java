package com.blalp.chatdirector.modules.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Chain;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IteratorIterable;
import com.blalp.chatdirector.modules.IModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogicModule implements IModule {

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public List<String> getItemNames() {
        return Arrays.asList("if-contains", "if-equals", "if-regex-match", "split", "if-starts-with", "if-ends-with");
    }
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public IItem createItem(ObjectMapper om, Chain chain, String type, JsonNode config) {
        ConditionalItem output;
        switch (type) {
            case "if-contains":
                output = new IfContainsItem(ChatDirector.loadChain(om, config.get("yes-chain")), ChatDirector.loadChain(om, config.get("no-chain")), config.get("contains").asText());
                if (config.has("source")) {
                    output.source = config.get("source").asText();
                }
                if (config.has("invert")) {
                    output.invert = config.get("invert").asBoolean();
                }
                return output;
            case "if-equals":
                output = new IfEqualsItem(ChatDirector.loadChain(om, config.get("yes-chain")),ChatDirector.loadChain(om, config.get("no-chain")),config.get("equals").asText());
                if (config.has("source")) {
                    output.source = config.get("source").asText();
                }
                if (config.has("invert")) {
                    output.invert = config.get("invert").asBoolean();
                }
                if (config.has("ignore-case")) {
                    ((IfEqualsItem) output).ignoreCase = config.get("ignore-case").asBoolean();
                }
                return output;
            case "if-regex-match":
                output = new IfRegexMatchesItem(ChatDirector.loadChain(om, config.get("yes-chain")),ChatDirector.loadChain(om, config.get("no-chain")),config.get("regex").asText());
                if (config.has("source")) {
                    output.source = config.get("source").asText();
                }
                if (config.has("invert")) {
                    output.invert = config.get("invert").asBoolean();
                }
                return output;
            case "split":
                ArrayList<Chain> chains = new ArrayList<>();
                for (JsonNode jsonChain : new IteratorIterable<>(config.elements())) {
                    chains.add(ChatDirector.loadChain(om,jsonChain));
                }
                return new SplitItem(chains);
            case "if-starts-with":
                return new IfStartsWithItem(
                        ChatDirector.loadChain(om, config.get("yes-chain")),
                        ChatDirector.loadChain(om, config.get("no-chain")),
                        config.get("starts").asText(), config.get("source").asText());
            case "if-ends-with":
                return new IfEndsWithItem(
                        ChatDirector.loadChain(om, config.get("yes-chain")),
                        ChatDirector.loadChain(om, config.get("no-chain")),
                        config.get("ends").asText(), config.get("source").asText());
            default:
                return null;
        }
    }

    @Override
    public Context getContext(Object object) {
        return new Context();
    }
    
}
