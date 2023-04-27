package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * SlashCommandHandler interface defines methods that must be implemented by classes handling slash
 * command interactions in the Discord bot.
 */
public interface SlashCommandHandler {
    /**
     * Returns the name of the command.
     *
     * @return A non-null string representing the name of the command.
     */
    @Nonnull
    public String getName();

    /**
     * Returns the command data for the command.
     *
     * @return A non-null CommandData object representing the command data for the command.
     */
    @Nonnull
    public CommandData getCommandData();
    /**
     * Handles the slash command interaction event for the command.
     *
     * @param event A non-null SlashCommandInteractionEvent object representing the slash command
     *     interaction.
     */
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event);
}
