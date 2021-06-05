package com.blalp.chatdirector.proximitychat.modules.proximitychat;

import lombok.Data;

@Data
public class Location {
    double x, y, z;

    public Location(String location) {
        x = Double.parseDouble(location.split(",")[0]);
        y = Double.parseDouble(location.split(",")[1]);
        z = Double.parseDouble(location.split(",")[2]);
    }

    public double getFlatDistance(Location other) {
        return Math.sqrt(Math.pow((other.getX() - x), 2) + Math.pow((other.getY() - y), 2));
    }
}
