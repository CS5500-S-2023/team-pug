package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.model.Player;
import java.text.MessageFormat;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Singleton
@Slf4j
public class PlayerInfoCommand implements SlashCommandHandler {
    @Inject PlayerController playerController;

    @Inject
    PlayerInfoCommand() {}

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Ask the bot to get the account info");
    }

    @Override
    @Nonnull
    public String getName() {
        return "info";
    }

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
        event.reply(output).setEphemeral(true).queue();
    }
}
