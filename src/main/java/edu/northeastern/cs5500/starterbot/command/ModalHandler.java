package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * ModalHandler interface represents an object that handles modal interactions in the Discord bot.
 * Implementing classes should define the behavior when a modal interaction occurs.
 */
public interface ModalHandler {
    /**
     * Returns the name of the modal handler.
     *
     * @return A non-null string representing the name of the modal handler.
     */
    @Nonnull
    public String getName();
    /**
     * Handles a modal interaction event.
     *
     * @param event A non-null ModalInteractionEvent object representing the modal interaction.
     */
    public void onModalInteraction(@Nonnull ModalInteractionEvent event);
}
