package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.model.Player;
import java.text.MessageFormat;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * PlayerInfoCommand class handles the "/info" slash command interaction in the Discord bot. It
 * retrieves the account information of the user who invoked the command.
 */
@Singleton
@Slf4j
public class PlayerInfoCommand implements SlashCommandHandler {
    @Inject PlayerController playerController;

    /** Default constructor for PlayerInfoCommand. */
    @Inject
    PlayerInfoCommand() {}

    /**
     * Returns the command data for the "info" command.
     *
     * @return A non-null CommandData object representing the command data for the "info" command.
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Ask the bot to get the account info");
    }

    /**
     * Returns the name of the command.
     *
     * @return A non-null string representing the name of the command.
     */
    @Override
    @Nonnull
    public String getName() {
        return "info";
    }

    /**
     * Handles the slash command interaction event for the "info" command.
     *
     * @param event A non-null SlashCommandInteractionEvent object representing the slash command
     *     interaction.
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /info");
        String discordUserId = event.getUser().getId();
        Player player = playerController.getPlayer(discordUserId);
        String template = "username: {0}\n balance:{1}\n last login:{2}";
        String output =
                MessageFormat.format(
                        template,
                        new Object[] {
                            player.getUserName(),
                            player.getBalance().toString(),
                            player.getLastLoginTime()
                        });
        Objects.requireNonNull(output);
        event.reply(output).setEphemeral(true).queue();
    }
}
