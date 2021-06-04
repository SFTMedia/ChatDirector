package com.blalp.chatdirector.proximitychat.modules.proximitychat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.blalp.chatdirector.ChatDirector;
import com.blalp.chatdirector.model.Context;

import org.junit.jupiter.api.Test;

public class TestProximity {

    private static ChatDirector chatDirector;

    @Test
    public void init() {
        /*
         * if (chatDirector != null) { return; } chatDirector = new ChatDirector( new
         * File(this.getClass().getClassLoader().getResource(
         * "modules/luckperms/config.yml").getFile())); assertTrue(chatDirector.load());
         */
    }

    @Test
    public void valid() {
        init();
        assertTrue(chatDirector.isValid());
    }

    // Not complete, doesn't need to be, its just for generating fake names
    private static char[] letters = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k' };

    private Context generateContext(int playerNum, int nameLength, int numWorlds, int minX, int minY, int minZ,
            int maxX, int maxY, int maxZ) {
        Context output = new Context();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < playerNum; i++) {
            String name = "";
            for (int j = 0; j < nameLength; j++) {
                name += letters[rand.nextInt(letters.length)];
            }
            double x = rand.nextInt(maxX - minX) + minX;
            double y = rand.nextInt(maxY - minZ) + minY;
            double z = rand.nextInt(maxZ - minZ) + minY;
            output.put("PLAYERS_LOCATION_" + name, x + " " + y + " " + z);
            // Just use numeric worlds
            output.put("PLAYERS_WORLD_" + name, "" + rand.nextInt(numWorlds));
        }
        return output;
    }

    // @Test
    // Running this on every test scope would be slow, but if you need to test, turn
    // it on.
    public void performanceTest() {
        ProximityChatItem item = new ProximityChatItem();
        int[] players = new int[] { 10, 25, 50, 100, 200, 500, 1000, 2000, 5000, 10000 };
        /*
         * 0 Milliseconds for 10 players 0 Milliseconds for 25 players 0 Milliseconds
         * for 50 players 0 Milliseconds for 100 players 0 Milliseconds for 200 players
         * 11 Milliseconds for 500 players 68 Milliseconds for 1000 players 170
         * Milliseconds for 2000 players 1129 Milliseconds for 5000 players 5083
         * Milliseconds for 10000 players 28669 Milliseconds for 20000 players
         */
        for (int j : players) {
            Context context = generateContext(j, 16, 5, -10000, -10000, -10000, 10000, 10000, 10000);
            int trials = 10;
            List<Long> times = new ArrayList<>();
            for (int i = 0; i < trials; i++) {
                long start = System.currentTimeMillis();
                item.process(context);
                long end = System.currentTimeMillis();
                times.add(end - start);
            }
            int average = 0;
            for (Long long1 : times) {
                // System.out.println("Trial "+long1);
                average += long1 / trials;
            }
            System.out.println(average + " Milliseconds for " + j + " players");
        }
    }

    // @Test
    public void accuracyTest() {
        ProximityChatItem item = new ProximityChatItem();
        Context candidateContext = generateContext(10, 16, 2, -100, 0, -100, 100, 100, 100);
        System.out.println(candidateContext);
        System.out.println(item.process(candidateContext));
        candidateContext = generateContext(5, 16, 2, -100, 0, -100, 100, 100, 100);
        System.out.println(candidateContext);
        System.out.println(item.process(candidateContext));
        candidateContext = generateContext(20, 16, 2, -100, 0, -100, 100, 100, 100);
        System.out.println(candidateContext);
        System.out.println(item.process(candidateContext));
    }
}
