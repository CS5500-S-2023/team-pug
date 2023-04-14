package edu.northeastern.cs5500.starterbot.view;

import static edu.northeastern.cs5500.starterbot.util.Constant.SLOTMACHINE_GAME_NAME;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;

public class SlotMachineView {
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

    public static MessageCreateBuilder createSlotMachineResultMessageBuilder(
            User user, Double balance) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle(SLOTMACHINE_GAME_NAME + " Result");
        embedBuilder.addField(user.getName(), String.valueOf(balance), true);
        return new MessageCreateBuilder().addEmbeds(embedBuilder.build());
    }
}
