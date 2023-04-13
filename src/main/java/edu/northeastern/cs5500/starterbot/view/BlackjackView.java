package edu.northeastern.cs5500.starterbot.view;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;

import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import java.awt.Color;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;

public class BlackjackView {

    public static MessageCreateBuilder createBlackjackMessageBuilder(User user, ObjectId gameId) {
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

    public static MessageCreateBuilder createBlackjackResultMessageBuilder(List<Result> results) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(BLACKJACK_GAME_NAME + " Result");
        for (Result result : results) {
            embedBuilder.addField(result.getUser().getName(), result.getBet().toString(), true);
        }

        return new MessageCreateBuilder().addEmbeds(embedBuilder.build());
    }
}
