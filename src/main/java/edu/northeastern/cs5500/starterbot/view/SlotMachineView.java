package edu.northeastern.cs5500.starterbot.view;

import static edu.northeastern.cs5500.starterbot.util.Constant.SLOTMACHINE_GAME_NAME;

import java.awt.Color;
import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;

/**
 * SlotMachineView is a utility class for creating Discord message builders related to the Slot
 * Machine game. It provides methods for generating game-related messages with appropriate embeds
 * and buttons.
 */
public class SlotMachineView {
    /**
     * Creates a Discord message builder for the initial Slot Machine game message.
     *
     * @param user the current user playing the game.
     * @param gameId the ObjectId of the game instance.
     * @return a MessageCreateBuilder with the game title, description, and action buttons.
     */
    private SlotMachineView() {}

    public static MessageCreateBuilder createSlotMachineMessageBuilder(User user, ObjectId gameId) {
        String id = gameId.toString();
        String userId = user.getId();
        EmbedBuilder embedBuilder =
                new EmbedBuilder()
                        .setTitle(SLOTMACHINE_GAME_NAME)
                        .setDescription(" Have Fun! " + user.getAsMention())
                        .setColor(Color.BLUE);
        Button play =
                Button.primary(SLOTMACHINE_GAME_NAME + ":PLAY" + ":" + id + ":" + userId, "PLAY");
        Button end =
                Button.primary(SLOTMACHINE_GAME_NAME + ":END" + ":" + id + ":" + userId, "END");
        return new MessageCreateBuilder().addEmbeds(embedBuilder.build()).addActionRow(play, end);
    }

    /**
     * Creates a Discord message builder for the Slot Machine game result message.
     *
     * @param user the user who played the game.
     * @param balance the user's balance after playing the game.
     * @return a MessageCreateBuilder with the game results as embed fields.
     */
    public static MessageCreateBuilder createSlotMachineResultMessageBuilder(
            User user, Double balance) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(SLOTMACHINE_GAME_NAME + " Result");
        String bal = String.valueOf(balance);
        Objects.requireNonNull(bal);
        embedBuilder.addField(user.getName(), bal, true);
        return new MessageCreateBuilder().addEmbeds(embedBuilder.build());
    }
}
