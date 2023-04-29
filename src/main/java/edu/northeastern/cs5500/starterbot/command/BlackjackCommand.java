package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;

import edu.northeastern.cs5500.starterbot.controller.BlackjackController;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.bson.types.ObjectId;

/**
 * BlackjackCommand class handles button interactions for the Blackjack game. It implements the
 * ButtonHandler interface.
 */
public class BlackjackCommand implements ButtonHandler {
    @Inject BlackjackController blackjackController;

    /** Constructs a new instance of BlackjackCommand. */
    @Inject
    public BlackjackCommand() {}

    /**
     * Returns the name of the game.
     *
     * @return A String representing the name of the game.
     */
    @Override
    @Nonnull
    public String getName() {
        return BLACKJACK_GAME_NAME;
    }

    /**
     * Handles button interactions for the Blackjack game.
     *
     * @param event A ButtonInteractionEvent representing the button interaction event.
     */
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        // accept actions for blackjack and pass to controller
        String action = event.getButton().getLabel();
        String eventId = event.getButton().getId();
        Objects.requireNonNull(eventId);
        String targetUserId = eventId.split(":")[3];
        ObjectId id = new ObjectId(eventId.split(":")[2]);
        if (!blackjackController.containsGameId(id)) {
            event.reply("The game has already finished...").setEphemeral(true).queue();
            return;
        }
        if (event.getUser().getId().equals(targetUserId)) {
            blackjackController.handlePlayerAction(id, action, event);
        } else {
            if (event.isAcknowledged()) {
                event.getHook()
                        .sendMessage("Please Wait, It is not your turn...")
                        .setEphemeral(true)
                        .queue();
            } else {
                event.reply("Please Wait, It is not your turn...").setEphemeral(true).queue();
            }
        }
    }
}
