package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Player;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.util.Collection;
import javax.inject.Inject;

/**
 * PlayerController is a class for managing player-related operations, such as getting a player,
 * setting the player's name, and updating their balance.
 */
public class PlayerController {
    GenericRepository<Player> playerRepository;
    /**
     * Constructor for the PlayerController.
     *
     * @param playerRepository A GenericRepository object that handles the storage of Player
     *     objects.
     */
    @Inject
    public PlayerController(GenericRepository<Player> playerRepository) {
        this.playerRepository = playerRepository;
    }
    /**
     * Retrieves a player with the given Discord user ID. If the player is not found, a new player
     * will be created and added to the repository.
     *
     * @param discordUserId A String containing the Discord user ID.
     * @return A Player object representing the player with the given Discord user ID.
     */
    public Player getPlayer(String discordUserId) {
        Collection<Player> playerCollection = playerRepository.getAll();
        for (Player player : playerCollection) {
            if (player.getDiscordUserId().equals(discordUserId)) {
                return player;
            }
        }
        Player newPlayer = new Player(discordUserId);
        playerRepository.add(newPlayer);
        return newPlayer;
    }
    /**
     * Sets the name of the player with the given Discord user ID.
     *
     * @param discordUserId A String containing the Discord user ID.
     * @param newName A String containing the new name of the player.
     */
    public void setPlayerName(String discordUserId, String newName) {
        Player player = getPlayer(discordUserId);
        player.setUserName(newName);
        player.setLastLoginTime();
        playerRepository.update(player);
    }
    /**
     * Updates the balance of the player with the given Discord user ID by the specified amount.
     *
     * @param discordUserId A String containing the Discord user ID.
     * @param amount A Double representing the amount to update the player's balance.
     */
    public void updateBalance(String discordUserId, Double amount) {
        Player player = getPlayer(discordUserId);
        Double balance = player.getBalance();
        balance += amount;
        player.setBalance(balance);
    }
}
