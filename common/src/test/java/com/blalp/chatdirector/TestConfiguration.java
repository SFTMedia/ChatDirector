package com.blalp.chatdirector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.blalp.chatdirector.configuration.Chain;
import com.blalp.chatdirector.configuration.ConfigurationCommon;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.model.IModule;
import com.blalp.chatdirector.modules.cache.CacheGetItem;
import com.blalp.chatdirector.modules.cache.CacheIfItem;
import com.blalp.chatdirector.modules.cache.CacheSetItem;
import com.blalp.chatdirector.modules.common.BreakItem;
import com.blalp.chatdirector.modules.common.HaltItem;

import org.junit.jupiter.api.Test;

public class TestConfiguration {
    String rawData = ""+
"\n"+
"    modules:\n"+
"      - cache\n"+
"      - console\n"+
"      - context\n"+
"      - file\n"+
"      - logic\n"+
"      - replacement\n"+
"    debug: false\n"+
"    chains:\n"+
"        - cache-parse-test:\n"+
"            - cache-get:\n"+
"                key: \"SomeUniqueKey\"\n"+
"            - cache-set:\n"+
"                key: \"EXAMPLE_KEY\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-if:\n"+
"                yes-chain:\n"+
"                   - stop: null\n"+
"                no-chain:\n"+
"                   - break: null\n"+
"                key: \"KEY\"\n"+
"        - cache-get-set-test:\n"+
"            - cache-set:\n"+
"                key: \"SomeUniqueKey\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-get:\n"+
"                key: \"SomeUniqueKey\"\n"+
"        - cache-get-set-test-2:\n"+
"            - cache-set:\n"+
"                key: \"SomeUniqueKey\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-get:\n"+
"                key: \"RandomKey\"\n"+
"        - cache-if-test:\n"+
"            - cache-set:\n"+
"                key: \"SomeUniqueKey\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-if:\n"+
"                yes-chain:\n"+
"                   - echo: \"SomeUniqueKey was found!\"\n"+
"                no-chain:\n"+
"                   - echo: \"SomeUniqueKey was not found!\"\n"+
"                key: \"SomeUniqueKey\"\n"+
"            - cache-if:\n"+
"                yes-chain:\n"+
"                   - echo: \"Random was found!\"\n"+
"                no-chain:\n"+
"                   - echo: \"Random was not found!\"\n"+
"                key: \"Random\"\n"+
"        - context-parse-test:\n"+
"            - get-context:\n"+
"                context: \"CONTEXT_NAME\"\n"+
"            - set-context:\n"+
"                context: \"TARGET_CONTEXT\"\n"+
"                value: \"CONTEXT_VALUE\"\n"+
"            - set-context:\n"+
"                context: \"TARGET_CONTEXT\"\n"+
"            - remove-context:\n"+
"                context: \"SERVER_NAME\"\n"+
"            - resolve-context: null\n"+
"            - resolve-context\n"+
"        - context-get-set-test:\n"+
"            - set-context:\n"+
"                context: \"TARGET_CONTEXT\"\n"+
"                value: \"CONTEXT_VALUE\"\n"+
"            - get-context:\n"+
"                context: \"TARGET_CONTEXT\"\n"+
"            - remove-context:\n"+
"                context: \"SERVER_NAME\"\n"+
"            - resolve-context: null\n"+
"        - context-parse-test:\n"+
"            - get-context:\n"+
"                context: \"CONTEXT_NAME\"\n"+
"            - set-context:\n"+
"                context: \"TARGET_CONTEXT\"\n"+
"                value: \"CONTEXT_VALUE\"\n"+
"            - remove-context:\n"+
"                context: \"SERVER_NAME\"\n"+
"            - resolve-context: null\n"+
"        - file-parse-test:\n"+
"            - file-input:\n"+
"                path: target/PATH_TO_FIFO\n"+
"                delay: 500\n"+
"                create: true\n"+
"            - file-input:\n"+
"                path: target/PATH_TO_FIFO\n"+
"            - file-output:\n"+
"                path: target/PATH_TO_FIFO\n"+
"                delay: 500\n"+
"            - file-output:\n"+
"                path: target/PATH_TO_FIFO\n"+
"                create: false\n"+
"            - file-input:\n"+
"                path: target/PATH_TO_FIFO_2\n"+
"                create: true\n"+
"            - file-output:\n"+
"                path: target/PATH_TO_FIFO_2\n"+
"                create: false\n"+
"        - logic-test:\n"+
"            - if-contains:\n"+
"                yes-chain:\n"+
"                    - pass: null\n"+
"                    - stop: null\n"+
"                no-chain:\n"+
"                    - stop: null\n"+
"                contains: \"String\"\n"+
"                source: \"%CURRENT%\"\n"+
"                invert: false\n"+
"            - if-contains:\n"+
"                contains: \"String\"\n"+
"                yes-chain:\n"+
"                    - pass: null\n"+
"                    - stop: null\n"+
"            - if-contains:\n"+
"                contains: \"String\"\n"+
"                no-chain:\n"+
"                    - stop: null\n"+
"            - if-equals:\n"+
"                yes-chain:\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                no-chain:\n"+
"                    - stop: null\n"+
"                equals: \"String\"\n"+
"                source: \"%CURRENT%\"\n"+
"                invert: false\n"+
"                ignore-case: false\n"+
"            - if-equals:\n"+
"                equals: \"String\"\n"+
"                no-chain:\n"+
"                    - stop: null\n"+
"            - if-regex-match:\n"+
"                yes-chain:\n"+
"                    - echo: \"hello!\"\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                no-chain:\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                match: \"[sS]tring\"\n"+
"                source: \"%CURRENT%\"\n"+
"                invert: false\n"+
"            - if-regex-match:\n"+
"                match: \"[sS]tring\"\n"+
"                yes-chain:\n"+
"                    - halt\n"+
"            - split:\n"+
"                - stream-1:\n"+
"                    - echo: \"hello!\"\n"+
"                    - break: null\n"+
"                - stream-2:\n"+
"                    - halt: null\n"+
"            - split\n"+
"            - if-starts-with:\n"+
"                yes-chain:\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                no-chain:\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                starts: \"String\"\n"+
"                source: \"%CURRENT%\"\n"+
"            - if-starts-with:\n"+
"                starts: \"String\"\n"+
"                yes-chain:\n"+
"                    - halt\n"+
"            - if-ends-with:\n"+
"                yes-chain:\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                no-chain:\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                ends: \"String\"\n"+
"                source: \"%CURRENT%\"\n"+
"            - if-ends-with:\n"+
"                ends: \"String\"\n"+
"                yes-chain:\n"+
"                    - halt\n"+
"        - replacement-test:\n"+
"            - regex:\n"+
"                - \"Regex to find\": \"regex to replace\"\n"+
"                - \"Regex to find2\": \"regex to replace2\"\n"+
"            - remove-colors: null\n"+
"            - remove-colors\n"+
"            - resolve-colors: null\n"+
"            - resolve-colors\n"+
"            - to-upper: null\n"+
"            - to-upper\n"+
"            - to-lower: null\n"+
"            - to-lower\n"+
"            - to-word: null\n"+
"            - to-word\n"+
"            - sub-string:\n"+
"                start: 2\n"+
"                end: 4\n"+
"            - sub-string:\n"+
"                end: 4\n"+
"            - sub-string\n"+
"        - console-test:\n"+
"            - console-output: null\n"+
"            - console-output-error: null\n"+
"            - console-output\n"+
"            - console-output-error\n";
    static ChatDirector chatDirector;
    private void init() {
        if(chatDirector!=null){
            return;
        }
        chatDirector = new ChatDirector(rawData);
        ChatDirector.config.addConfiguration(new ConfigurationCommon());
        chatDirector.load();

    }
    @Test
    public void valid(){
        init();
        for(IModule module: chatDirector.modules) {
            assertTrue(module.isValid());
        }
        for(Chain chain: chatDirector.chains.values()) {
            assertTrue(chain.isValid());
        }
    }
    @Test
    public void cache(){
        init();
        // Checking Chain metric
        assertEquals(chatDirector.getChains().size(),10);
        assertTrue(chatDirector.getChains().containsKey("cache-parse-test"));
        assertNotNull(chatDirector.getChains().get("cache-parse-test"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("cache-parse-test").items);
        assertTrue(chatDirector.getChains().get("cache-parse-test").items.size()==3);
        assertTrue(chatDirector.getChains().get("cache-parse-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("cache-parse-test").items.get(0);
        assertTrue(item instanceof CacheGetItem);
        IItem compare = new CacheGetItem();
        ((CacheGetItem)compare).setKey("SomeUniqueKey");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-parse-test").items.get(1);
        assertTrue(item instanceof CacheSetItem);
        compare = new CacheSetItem();
        ((CacheSetItem)compare).setKey("EXAMPLE_KEY");
        ((CacheSetItem)compare).setValue("EXAMPLE_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("cache-parse-test").items.get(2);
        assertTrue(item instanceof CacheIfItem);
        compare = new CacheIfItem();
        ((CacheIfItem)compare).setKey("KEY");
        Chain chain = new Chain();
        IItem chainItem = new HaltItem();
        chain.addItem(chainItem);
        ((CacheIfItem)compare).setYesChain(chain);
        chain = new Chain();
        chainItem=new BreakItem();
        chain.addItem(chainItem);
        ((CacheIfItem)compare).setNoChain(chain);
        assertEquals(compare, item);        
    }
    @Test
    public void testReload(){
        
    }
}
