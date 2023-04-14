package edu.northeastern.cs5500.starterbot.game;

import lombok.Data;

@Data
public class Config {
    private String name;
    private int minPlayers;
    private int maxPlayers;

    public Config(String name, Integer minPlayers, Integer maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public Config(String name) {
        this.name = name;
        minPlayers = 1;
        maxPlayers = 1;
    }
}
