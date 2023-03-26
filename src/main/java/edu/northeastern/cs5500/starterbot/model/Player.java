package edu.northeastern.cs5500.starterbot.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Player implements Model {
    ObjectId id;
    String discordUserId;
    String userName;
    BigDecimal balance;
    String lastLoginTime;

    public void setLastLoginTime() {
        lastLoginTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
    }

    public void initPlayer(String discordUserId) {
        this.discordUserId = discordUserId;
        userName = "Anonymous";
        balance = BigDecimal.ZERO;
        setLastLoginTime();
    }
}
