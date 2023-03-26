package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Player;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.util.Collection;
import javax.inject.Inject;

public class PlayerController {
    GenericRepository<Player> playerRepository;

    @Inject
    public PlayerController(GenericRepository<Player> playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayer(String discordUserId) {
        Collection<Player> playerCollection = playerRepository.getAll();
        for (Player player : playerCollection) {
            if (player.getDiscordUserId().equals(discordUserId)) {
                return player;
            }
        }
        Player newPlayer = new Player();
        newPlayer.initPlayer(discordUserId);
        playerRepository.add(newPlayer);
        return newPlayer;
    }

    public void setPlayerName(String discordUserId, String newName) {
        Player player = getPlayer(discordUserId);
        player.setUserName(newName);
        player.setLastLoginTime();
        playerRepository.update(player);
    }
}
