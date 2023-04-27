package edu.northeastern.cs5500.starterbot.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * A class representing a player in the casino game. Each player has a unique ID, a Discord user ID,
 * a username, a balance, and a last login time.
 *
 * <p>The player's ID is generated automatically by the database and is not set by the user. The
 * player's balance is initially set to 1000.00. The player's last login time is automatically set
 * to the current date and time when the player is created.
 *
 * <p>This class implements the {@code Model} interface, which provides a common interface for
 * database operations.
 */
@Data
public class Player implements Model {
    ObjectId id;
    String discordUserId;
    String userName;
    Double balance;
    String lastLoginTime;
    /**
     * Constructs a new instance of the {@code Player} class with the specified Discord user ID. The
     * player's username is initially set to "Anonymous". The player's balance is initially set to
     * 1000.00. The player's last login time is automatically set to the current date and time.
     *
     * @param discordUserId the Discord user ID of the player
     */
    public Player(String discordUserId) {
        this.discordUserId = discordUserId;
        userName = "Anonymous";
        balance = 1000.00;
        setLastLoginTime();
    }
    /**
     * Sets the last login time of this player to the current date and time. The last login time is
     * stored as a string in the format "yyyy.MM.dd.HH.mm.ss".
     */
    public void setLastLoginTime() {
        lastLoginTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }
}
