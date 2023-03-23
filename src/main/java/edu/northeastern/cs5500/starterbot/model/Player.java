package edu.northeastern.cs5500.starterbot.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.bson.types.ObjectId;

@Data
public class Player implements Model {
    ObjectId id;
    String discordUserId;
    String userName;
    BigDecimal balance;

    @Setter(AccessLevel.NONE)
    String lastLoginTime;

    public void setLastLoginTime(Date date) {
        lastLoginTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(date);
    }

    public void initPlayer(String discordUserId) {
        this.discordUserId = discordUserId;
        userName = "Anonymous";
        balance = BigDecimal.ZERO;
        setLastLoginTime(new Date());
    }
}
