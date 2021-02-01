package com.blalp.chatdirector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.blalp.chatdirector.configuration.ConfigurationCommon;
import com.blalp.chatdirector.model.Context;
import com.blalp.chatdirector.model.IItem;
import com.blalp.chatdirector.modules.context.ContextGetItem;
import com.blalp.chatdirector.modules.context.ContextRemoveItem;
import com.blalp.chatdirector.modules.context.ContextResolveItem;
import com.blalp.chatdirector.modules.context.ContextSetItem;

import org.junit.jupiter.api.Test;

public class TestIntegration {
    static String rawData = ""+
"# Example of just common items\n"+
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
"                key: \"SomeUniqueKey\" # Where get the value from\n"+
"            - cache-set:\n"+
"                key: \"EXAMPLE_KEY\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-if:\n"+
"                yes-chain: # List of items to run if there is a cached value\n"+
"                   - stop: null\n"+
"                no-chain: # List of items to run if there is no cached value\n"+
"                   - break: null\n"+
"                key: \"KEY\" # The key to check for\n"+
"        - cache-get-set-test:\n"+
"            - cache-set:\n"+
"                key: \"SomeUniqueKey\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-get:\n"+
"                key: \"SomeUniqueKey\" # Where get the value from\n"+
"        - cache-get-set-test-2:\n"+
"            - cache-set:\n"+
"                key: \"SomeUniqueKey\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-get:\n"+
"                key: \"RandomKey\" # Where get the value from\n"+
"        - cache-if-test:\n"+
"            - cache-set:\n"+
"                key: \"SomeUniqueKey\"\n"+
"                value: \"EXAMPLE_VALUE\"\n"+
"            - cache-if:\n"+
"                yes-chain: # List of items to run if there is a cached value\n"+
"                   - echo: \"SomeUniqueKey was found!\"\n"+
"                no-chain: # List of items to run if there is no cached value\n"+
"                   - echo: \"SomeUniqueKey was not found!\"\n"+
"                key: \"SomeUniqueKey\" # The key to check for\n"+
"            - cache-if:\n"+
"                yes-chain: # List of items to run if there is a cached value\n"+
"                   - echo: \"Random was found!\"\n"+
"                no-chain: # List of items to run if there is no cached value\n"+
"                   - echo: \"Random was not found!\"\n"+
"                key: \"Random\" # The key to check for\n"+
"        - context-test:\n"+
"            - get-context:\n"+
"                context: \"SERVER_%SERVER_NAME%_Players\" # Normally such a nested contexts would not be possible\n"+
"            - set-context:\n"+
"                context: \"TARGET_CONTEXT\"\n"+
"                value: \"Hello %PLAYER_NAME% you have %BALANCE% money!\" # Optional. defaults to CURRENT\n"+
"            - remove-context:\n"+
"                context: \"SERVER_NAME\"\n"+
"            - resolve-context: null # This resolves the input string as a formattable message. Normally this shouldn't be needed and could allow for formatting injection.\n"+
"        - file-test:\n"+
"            - file-input:\n"+
"                path: target/PATH_TO_FIFO\n"+
"                delay: 500 # optional, defaults to 500\n"+
"                create: true\n"+
"            - file-output:\n"+
"                path: target/PATH_TO_FIFO\n"+
"                delay: 500 # optional, defaults to 500\n"+
"            - file-input:\n"+
"                path: target/PATH_TO_FIFO_2\n"+
"                create: true\n"+
"            - file-output:\n"+
"                path: target/PATH_TO_FIFO_2\n"+
"        - logic-test:\n"+
"            - if-contains:\n"+
"                yes-chain: # this can be any list of items or optional\n"+
"                    - pass: null\n"+
"                    - stop: null\n"+
"                no-chain: # Or this can be nothing or optional\n"+
"                    - stop: null\n"+
"                contains: \"String\"\n"+
"                source: \"%CURRENT%\" # Optional. source.contains(contains)\n"+
"                invert: false # Optional inverts the decision\n"+
"            - if-contains:\n"+
"                contains: \"String\"\n"+
"                yes-chain: # this can be any list of items or optional\n"+
"                    - pass: null\n"+
"                    - stop: null\n"+
"            - if-equals:\n"+
"                yes-chain:\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                no-chain:\n"+
"                    - stop: null\n"+
"                equals: \"String\"\n"+
"                source: \"%CURRENT%\" # Optional. source.equals(contains)\n"+
"                invert: false # Optional inverts the decision\n"+
"                ignore-case: false # optional\n"+
"            - if-equals:\n"+
"                equals: \"String\"\n"+
"                no-chain:\n"+
"                    - stop: null\n"+
"            - if-regex-match:\n"+
"                yes-chain: # Optional\n"+
"                    - echo: \"hello!\"\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                no-chain: # Optional\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                match: \"[sS]tring\"\n"+
"                source: \"%CURRENT%\" # Optional. source.match(contains)\n"+
"                invert: false # Optional inverts the decision\n"+
"            - if-regex-match:\n"+
"                match: \"[sS]tring\"\n"+
"            - split:\n"+
"                - stream-1:\n"+
"                    - echo: \"hello!\"\n"+
"                    - break: null\n"+
"                - stream-2:\n"+
"                    - halt: null\n"+
"            - split\n"+
"            - if-starts-with:\n"+
"                yes-chain: # Optional\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                no-chain: # Optional\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                starts: \"String\"\n"+
"                source: \"%CURRENT%\" # Optional. source.startsWith(contains)\n"+
"            - if-starts-with:\n"+
"                starts: \"String\"\n"+
"            - if-ends-with:\n"+
"                yes-chain: # Optional\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                no-chain: # Optional\n"+
"                    - cache-get:\n"+
"                       key: \"Key val\"\n"+
"                    - echo: \"hello!\"\n"+
"                ends: \"String\"\n"+
"                source: \"%CURRENT%\" # Optional. source.endsWith(contains)\n"+
"            - if-ends-with:\n"+
"                ends: \"String\"\n"+
"        - replacement-test:\n"+
"            - regex:\n"+
"                - \"Regex to find\": \"regex to replace\"\n"+
"                - \"Regex to find2\": \"regex to replace2\"\n"+
"            - remove-colors: null\n"+
"            - remove-colors\n"+
"            - resolve-colors: null # converts & into §\n"+
"            - resolve-colors # converts & into §\n"+
"            - to-upper: null # to upper case\n"+
"            - to-upper # to upper case\n"+
"            - to-lower: null # to lower case\n"+
"            - to-lower # to lower case\n"+
"            - to-word: null # Capitalize each word\n"+
"            - to-word # Capitalize each word\n"+
"            - sub-string:\n"+
"                start: 2 # number of characters to end at (optional)\n"+
"                end: 4 # number of characters to end at (optional)\n"+
"            - sub-string:\n"+
"                end: 4 # number of characters to end at (optional)\n"+
"            - sub-string # While this makes no sense, it should parse\n"+
"        - console-test:\n"+
"            - console-output: null\n"+
"            - console-output-error: null\n"+
"            - console-output\n"+
"            - console-output-error\n";
    static ChatDirector chatDirector;
    private void init(){
        chatDirector = new ChatDirector(rawData);
        ChatDirector.config.addConfiguration(new ConfigurationCommon());
        chatDirector.load();
    }

    @Test
    public void cache(){
        init();
        // Integration test
        assertEquals(new Context(), chatDirector.getChains().get("cache-parse-test").run(new Context()));
        Context context = new Context();
        context.put("CURRENT", "EXAMPLE_VALUE");
        assertEquals(context, chatDirector.getChains().get("cache-get-set-test").run(new Context()));
        context.put("LAST","Even if there is something here");
        assertEquals(context, chatDirector.getChains().get("cache-get-set-test").run(new Context("Even if there is something here")));
        context = new Context();
        assertEquals(context, chatDirector.getChains().get("cache-get-set-test-2").run(new Context()));
        context.put("CURRENT","Even if there is something here");
        assertEquals(context, chatDirector.getChains().get("cache-get-set-test-2").run(new Context("Even if there is something here")));
        context = new Context();
        context.put("CURRENT", "Random was not found!");
        context.put("LAST", "SomeUniqueKey was found!");
        assertEquals(context, chatDirector.getChains().get("cache-if-test").run(new Context()));
        assertEquals(context, chatDirector.getChains().get("cache-if-test").run(new Context("Even if there was something here.")));
    }
    @Test
    public void context(){
        init();
        // Checking Chain metric
        assertTrue(chatDirector.getChains().containsKey("context-parse-test"));
        assertNotNull(chatDirector.getChains().get("context-parse-test"));
        // Checking Per Chain metric
        assertNotNull(chatDirector.getChains().get("context-parse-test").items);
        assertTrue(chatDirector.getChains().get("context-parse-test").items.size()==6);
        assertTrue(chatDirector.getChains().get("context-parse-test").isValid());
        // Checking Each item in chain
        IItem item = chatDirector.getChains().get("context-parse-test").items.get(0);
        assertTrue(item instanceof ContextGetItem);
        IItem compare = new ContextGetItem();
        ((ContextGetItem)compare).setContext("CONTEXT_NAME");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(1);
        assertTrue(item instanceof ContextSetItem);
        compare = new ContextSetItem();
        ((ContextSetItem)compare).setContext("TARGET_CONTEXT");
        ((ContextSetItem)compare).setValue("CONTEXT_VALUE");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(2);
        assertTrue(item instanceof ContextSetItem);
        compare = new ContextSetItem();
        ((ContextSetItem)compare).setContext("TARGET_CONTEXT");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(3);
        assertTrue(item instanceof ContextRemoveItem);
        compare = new ContextRemoveItem();
        ((ContextRemoveItem)compare).setContext("SERVER_NAME");
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(4);
        assertTrue(item instanceof ContextResolveItem);
        compare = new ContextResolveItem();
        assertEquals(compare, item);
        item = chatDirector.getChains().get("context-parse-test").items.get(5);
        assertTrue(item instanceof ContextResolveItem);
        assertEquals(compare, item);
        
        // Behavior testing
        

    }
    @Test
    public void file(){
        init();
        // TODO: implement me
        
    }
    @Test
    public void logic(){
        init();
        // TODO: implement me
        
    }
    @Test
    public void replacement(){
        init();
        // TODO: implement me
        
    }
    @Test
    public void console(){
        init();
        // TODO: implement me
        
    }
}
