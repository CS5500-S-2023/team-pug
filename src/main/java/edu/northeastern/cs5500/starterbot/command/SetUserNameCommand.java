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

@Singleton
@Slf4j
public class SetUserNameCommand implements SlashCommandHandler {
    @Inject PlayerController playerController;

    @Inject
    public SetUserNameCommand() {}

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

    @Override
    @Nonnull
    public String getName() {
        return "set_name";
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /set_name");
        String newName = Objects.requireNonNull(event.getOption("name")).getAsString();
        String discordId = event.getUser().getId();
        playerController.setPlayerName(discordId, newName);
        event.reply("You have change the name to:" + newName).queue();
    }
}
