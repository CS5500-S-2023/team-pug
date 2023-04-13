package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;

import edu.northeastern.cs5500.starterbot.controller.BlackjackController;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.bson.types.ObjectId;

public class BlackjackCommand implements ButtonHandler {
    @Inject BlackjackController blackjackController;

    @Inject
    public BlackjackCommand() {}

    @Override
    @Nonnull
    public String getName() {
        return BLACKJACK_GAME_NAME;
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        // accept actions for blackjack and pass to controller
        String action = event.getButton().getLabel();
        String targetUserId = event.getButton().getId().split(":")[3];
        ObjectId id = new ObjectId(event.getButton().getId().split(":")[2]);
        // when the game finished it will reply some message
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
