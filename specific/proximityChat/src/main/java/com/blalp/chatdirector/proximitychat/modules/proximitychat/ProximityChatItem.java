package com.blalp.chatdirector.proximitychat.modules.proximitychat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;

public class ProximityChatItem implements IItem {

    int radius = 32;

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Context process(Context context) {
        Context output = new Context();

        // Map IGNs to groups
        HashMap<String, Integer> group = new HashMap<>();

        HashMap<Integer, Set<String>> groupPlayers = new HashMap<>();

        // Map Groups to Groups to merge them
        HashMap<Integer, Integer> groupMapping = new HashMap<>();

        // Start at group 1, as group 0 is resolved for the lobby
        int maxGroup = 1;

        Context playerLocation = context.getContextAtPath("PLAYERS_LOCATION_");
        Context playerWorld = context.getContextAtPath("PLAYERS_WORLD_");

        HashMap<String, Location> locations = new HashMap<>();

        for (String player1 : playerLocation.keySet()) {
            locations.put(player1, new Location(playerLocation.get(player1)));
        }
        for (String player1 : playerLocation.keySet()) {
            if (playerLocation.containsKey(player1)) {
                for (String player2 : playerWorld.keySet()) {
                    if (!player1.equals(player2)) {
                        if (playerWorld.get(player1).equals(playerWorld.get(player2))) {
                            if (locations.get(player1).getFlatDistance(locations.get(player2)) < radius) {
                                if (group.containsKey(player2)) {
                                    if (group.containsKey(player1)) {
                                        // You're and their group need to merge
                                        // Prefer to move the group to a higher index
                                        if (group.get(player1) > group.get(player2)) {
                                            groupMapping.put(group.get(player2), group.get(player1));
                                        } else if (group.get(player1) < group.get(player2)) {
                                            groupMapping.put(group.get(player1), group.get(player2));
                                        } else {
                                            // You are already in their group
                                        }
                                    } else {
                                        // You add to their group
                                        group.put(player1, group.get(player2));
                                        groupPlayers.get(group.get(player2)).add(player1);
                                    }
                                } else {
                                    if (group.containsKey(player1)) {
                                        // You are in a group but they aren't add them to your group
                                        group.put(player2, maxGroup);
                                    } else {
                                        // Create a new group with you two in it.
                                        group.put(player1, maxGroup);
                                        group.put(player2, maxGroup);
                                        groupPlayers.put(maxGroup, new HashSet<>());
                                        groupPlayers.get(maxGroup).add(player1);
                                        groupPlayers.get(maxGroup).add(player2);
                                        maxGroup++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // Count the number of merges so our ending ids are sequential
        int mergeCount = 0;
        for (int i = 0; i < maxGroup; i++) {
            if (groupMapping.containsKey(i)) {
                // Merge with group
                groupPlayers.get(groupMapping.get(i)).addAll(groupPlayers.get(i));
                mergeCount++;
                continue;
            }
            // At this point the group is fully merged
            for (String player : groupPlayers.get(i)) {
                output.put("PROXIMITY_CHAT_GROUP_" + player, "" + (i - mergeCount));
            }
        }
        // Set all the players who don't have a group to 0
        for (String player : playerLocation.keySet()) {
            if (!group.containsKey(player)) {
                output.put("PROXIMITY_CHAT_GROUP_" + player, "0");
            }
        }
        return output;
    }

}
