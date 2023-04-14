package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;
import static edu.northeastern.cs5500.starterbot.util.Constant.SLOTMACHINE_GAME_NAME;

import edu.northeastern.cs5500.starterbot.controller.BlackjackController;
import edu.northeastern.cs5500.starterbot.controller.SlotMachineController;
import java.awt.Color;
import java.io.File;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class GameCommand implements SlashCommandHandler, ButtonHandler {
    @Inject BlackjackController blackjackController;
    @Inject SlotMachineController slotMachineController;

    @Inject
    public GameCommand() {}

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Choose the game to play")
                .addOptions(
                        new OptionData(OptionType.STRING, "game-name", "The game to play")
                                .addChoice(BLACKJACK_GAME_NAME, BLACKJACK_GAME_NAME)
                                .addChoice(SLOTMACHINE_GAME_NAME, SLOTMACHINE_GAME_NAME));
    }

    @Override
    @Nonnull
    public String getName() {
        return "game";
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("event: /game");

        // TODO make the slot machine game private to player

        String gameName = event.getOption("game-name").getAsString();
        Objects.requireNonNull(gameName);

        if (gameName.equals(SLOTMACHINE_GAME_NAME)) {
            // Send a Direct Message to the user for the Slot Machine game
            event.reply(createStartGameMessageBuilder(event).build()).setEphemeral(true).queue();
        } else {
            // For other games, reply in the public channel
            event.reply(createStartGameMessageBuilder(event).build()).queue();
        }

    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        User user = event.getUser();
        ObjectId id = new ObjectId(event.getButton().getId().split(":")[2]);
        String gameName = event.getButton().getId().split(":")[3];
        if (gameName.equals(BLACKJACK_GAME_NAME)) {
            if (event.getButton().getLabel().equals("JOIN")) {
                Objects.requireNonNull(user);
                blackjackController.joinGame(id, user, event);
            } else {
                blackjackController.startGame(id, event);
            }
        } else if (gameName.equals(SLOTMACHINE_GAME_NAME)) {
            slotMachineController.startGame(id, event);
        }
    }

    private MessageCreateBuilder createStartGameMessageBuilder(
            @Nonnull SlashCommandInteractionEvent event) {
        String gameName = event.getOption("game-name").getAsString();
        Objects.requireNonNull(gameName);
        User gameStarter = event.getUser();
        ObjectId gameId = null;
        File file = null;
        EmbedBuilder embedBuilder =
                new EmbedBuilder()
                        .setTitle(gameName)
                        .setDescription(gameStarter.getAsMention() + "start a new game")
                        .setColor(Color.BLUE);
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.addEmbeds(embedBuilder.build());
        if (gameName.equals(BLACKJACK_GAME_NAME)) {
            gameId = blackjackController.newGame(2, 2, gameStarter);
            file = new File("/Users/peace/Downloads/Blackjack21.jpg");
            embedBuilder.setImage("attachment://blackjack.jpg");
            Button join =
                    Button.primary(
                            this.getName() + ":join" + ":" + gameId + ":" + gameName, "JOIN");
            Button start =
                    Button.danger(
                            this.getName() + ":start" + ":" + gameId + ":" + gameName, "START");

            messageCreateBuilder
                    .addActionRow(join, start)
                    .addFiles(FileUpload.fromData(file, "blackjack.jpg"));
        } else if (gameName.equals(SLOTMACHINE_GAME_NAME)) {
            gameId = slotMachineController.newGame(gameStarter);
            Button start =
                    Button.danger(
                            this.getName() + ":start" + ":" + gameId + ":" + gameName, "START");

            messageCreateBuilder.addActionRow(start);
        }

        return messageCreateBuilder;
    }
}