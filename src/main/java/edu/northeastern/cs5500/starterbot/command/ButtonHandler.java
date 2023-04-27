package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * ButtonHandler is an interface for handling button interactions in the Discord bot. Classes that
 * implement this interface should provide a method to handle button interactions and a method to
 * retrieve the name of the button handler.
 */
public interface ButtonHandler {
    /**
     * Retrieves the name of the button handler.
     *
     * @return A String representing the name of the button handler.
     */
    @Nonnull
    public String getName();
    /**
     * Handles a button interaction event received by the Discord bot.
     *
     * @param event A ButtonInteractionEvent object representing the button interaction event.
     */
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event);
}
