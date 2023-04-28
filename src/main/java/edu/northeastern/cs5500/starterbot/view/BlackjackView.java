package edu.northeastern.cs5500.starterbot.view;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;

import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import java.awt.Color;
import java.util.List;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;

/**
 * BlackjackView is a utility class for creating Discord message builders related to the Blackjack
 * game. It provides methods for generating game-related messages with appropriate embeds and
 * buttons.
 */
public class BlackjackView {
    /**
     * Creates a Discord message builder for the initial Blackjack game message.
     *
     * @param user the current user playing the game.
     * @param gameId the ObjectId of the game instance.
     * @return a MessageCreateBuilder with the game title, description, and action buttons.
     */
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
                Button.secondary(BLACKJACK_GAME_NAME + ":STAND" + ":" + id + ":" + userId, "STAND");
        Button doubleDown =
                Button.success(
                        BLACKJACK_GAME_NAME + ":DOUBLEDOWN" + ":" + id + ":" + userId,
                        "DOUBLE DOWN");

        Button showCard =
                Button.primary(
                        BLACKJACK_GAME_NAME + ":SHOWCARD" + ":" + id + ":" + userId, "SHOW CARD");
        Button surrender =
                Button.danger(
                        BLACKJACK_GAME_NAME + ":SURRENDER" + ":" + id + ":" + userId, "SURRENDER");
        return new MessageCreateBuilder()
                .addEmbeds(embedBuilder.build())
                .addActionRow(hit, stand, doubleDown, showCard, surrender);
    }

    /**
     * Creates a Discord message builder for the Blackjack game result message.
     *
     * @param results a list of Result objects containing user and bet information.
     * @return a MessageCreateBuilder with the game results as embed fields.
     */
    public static MessageCreateBuilder createBlackjackResultMessageBuilder(
            List<Result> results, @Nonnull ButtonInteractionEvent event) {
        EmbedBuilder embedBuilder =
                new EmbedBuilder().setTitle(BLACKJACK_GAME_NAME + " Result  " + ":sparkles:");
        for (Result result : results) {
            User user = event.getJDA().retrieveUserById(result.getDiscordId()).complete();
            embedBuilder.addField(user.getName(), result.getBet().toString(), true);
        }

        return new MessageCreateBuilder().addEmbeds(embedBuilder.build());
    }
}
