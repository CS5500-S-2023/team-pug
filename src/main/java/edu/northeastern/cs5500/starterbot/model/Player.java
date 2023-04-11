package edu.northeastern.cs5500.starterbot.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Player implements Model {
    ObjectId id;
    String discordUserId;
    String userName;
    Double balance;
    String lastLoginTime;

    public Player(String discordUserId) {
        this.discordUserId = discordUserId;
        userName = "Anonymous";
        balance = 1000.00;
        setLastLoginTime();
    }

    public void setLastLoginTime() {
        lastLoginTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
    }
}
