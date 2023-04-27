package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * SetUserNameCommand class handles the "/set_name" slash command interaction in the Discord bot. It
 * allows the user to set a new name for the bot to address them with.
 */
@Singleton
@Slf4j
public class SetUserNameCommand implements SlashCommandHandler {
    @Inject PlayerController playerController;
    /** Default constructor for SetUserNameCommand. */
    @Inject
    public SetUserNameCommand() {}

    /**
     * Returns the command data for the "set_name" command.
     *
     * @return A non-null CommandData object representing the command data for the "set_name"
     *     command.
     */
    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Tell the bot what name to address you with")
                .addOptions(
                        new OptionData(
                                        OptionType.STRING,
                                        "name",
                                        "The bot will use this name to talk to you going forward")
                                .setRequired(true));
    }
    /**
     * Returns the name of the command.
     *
     * @return A non-null string representing the name of the command.
     */
    @Override
    @Nonnull
    public String getName() {
        return "set_name";
    }
    /**
     * Handles the slash command interaction event for the "set_name" command.
     *
     * @param event A non-null SlashCommandInteractionEvent object representing the slash command
     *     interaction.
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /set_name");
        String newName = Objects.requireNonNull(event.getOption("name")).getAsString();
        String discordId = event.getUser().getId();
        playerController.setPlayerName(discordId, newName);
        event.reply("You have change the name to:" + newName).queue();
    }
}
