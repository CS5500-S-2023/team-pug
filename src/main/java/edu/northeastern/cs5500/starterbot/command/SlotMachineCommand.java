package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.SLOTMACHINE_GAME_NAME;

import edu.northeastern.cs5500.starterbot.controller.SlotMachineController;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

@Singleton
@Slf4j
public class SlotMachineCommand implements ButtonHandler {

    @Inject SlotMachineController slotMachineController;

    @Inject
    public SlotMachineCommand() {}

    @NotNull
    @Override
    public String getName() {
        return SLOTMACHINE_GAME_NAME;
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        // accept actions for blackjack and pass to controller
        String action = event.getButton().getLabel();
        String targetUserId = event.getButton().getId().split(":")[3];
        ObjectId id = new ObjectId(event.getButton().getId().split(":")[2]);
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
