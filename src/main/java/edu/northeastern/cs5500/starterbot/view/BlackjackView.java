package edu.northeastern.cs5500.starterbot.view;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class BlackjackView {
    public static MessageCreateBuilder createBlackjackMessageBuilder(User user, Object gameId) {
        String id = gameId.toString();
        String userId = user.getId();
        EmbedBuilder embedBuilder =
                new EmbedBuilder()
                        .setTitle(BLACKJACK_GAME_NAME)
                        .setDescription("It is your turn: " + user.getAsMention())
                        .setColor(Color.BLUE);
        Button hit = Button.primary(BLACKJACK_GAME_NAME + ":HIT" + ":" + id + ":" + userId, "HIT");
        Button stand =
                Button.primary(BLACKJACK_GAME_NAME + ":STAND" + ":" + id + ":" + userId, "STAND");
        Button doubleDown =
                Button.primary(
                        BLACKJACK_GAME_NAME + ":DOUBLEDOWN" + ":" + id + ":" + userId,
                        "DOUBLE DOWN");
        Button surrender =
                Button.primary(
                        BLACKJACK_GAME_NAME + ":SURRENDER" + ":" + id + ":" + userId, "SURRENDER");
        Button showCard =
                Button.primary(
                        BLACKJACK_GAME_NAME + ":SHOWCARD" + ":" + id + ":" + userId, "SHOW CARD");
        return new MessageCreateBuilder()
                .addEmbeds(embedBuilder.build())
                .addActionRow(hit, stand, doubleDown, surrender, showCard);
    }
}
