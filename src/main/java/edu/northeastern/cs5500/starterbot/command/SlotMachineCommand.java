package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.SLOTMACHINE_GAME_NAME;

import edu.northeastern.cs5500.starterbot.controller.SlotMachineController;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * SlotMachineCommand class is responsible for handling button interactions related to the Slot
 * Machine game in the Discord bot.
 */
@Singleton
@Slf4j
public class SlotMachineCommand implements ButtonHandler {

    private SlotMachineController slotMachineController;

    /**
     * Constructs a SlotMachineCommand instance with the specified SlotMachineController.
     *
     * @param slotMachineController A SlotMachineController object responsible for managing the Slot
     *     Machine game.
     */
    @Inject
    public SlotMachineCommand(SlotMachineController slotMachineController) {
        this.slotMachineController = slotMachineController;
    }

    /**
     * Returns the name of the Slot Machine game.
     *
     * @return A non-null string representing the name of the Slot Machine game.
     */
    @NotNull
    @Override
    public String getName() {
        return SLOTMACHINE_GAME_NAME;
    }

    /**
     * Handles button interactions related to the Slot Machine game.
     *
     * @param event A non-null ButtonInteractionEvent object representing the button interaction.
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        log.info(SLOTMACHINE_GAME_NAME);
        // accept actions for blackjack and pass to controller
        String action = event.getButton().getLabel();
        String eventId = event.getButton().getId();
        Objects.requireNonNull(eventId);
        String targetUserId = eventId.split(":")[3];
        ObjectId id = new ObjectId(eventId.split(":")[2]);
        // when the game finished it will reply some message
        if (!slotMachineController.containsGameId(id)) {
            event.reply("The game has already finished...").setEphemeral(true).queue();
            return;
        }
        if (event.getUser().getId().equals(targetUserId)) {
            slotMachineController.handlePlayerAction(id, action, event);
        } else {
            if (event.isAcknowledged()) {
                event.getHook().sendMessage("This is not your game...").setEphemeral(true).queue();
            } else {
                event.reply("This is not your game...").setEphemeral(true).queue();
            }
        }
    }
}
