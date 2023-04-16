package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;
import static edu.northeastern.cs5500.starterbot.util.Constant.SLOTMACHINE_GAME_NAME;

import edu.northeastern.cs5500.starterbot.controller.BlackjackController;
import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.controller.SlotMachineController;
import edu.northeastern.cs5500.starterbot.model.Player;
import java.awt.Color;
import java.io.File;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class GameCommand implements SlashCommandHandler, ButtonHandler, ModalHandler {
    @Inject BlackjackController blackjackController;
    @Inject SlotMachineController slotMachineController;
    @Inject PlayerController playerController;

    @Inject
    public GameCommand() {}

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "Choose the game to play")
                .addOptions(
                        new OptionData(OptionType.STRING, "game-name", "The game to play")
                                .addChoice(BLACKJACK_GAME_NAME, BLACKJACK_GAME_NAME)
                                .addChoice(SLOTMACHINE_GAME_NAME, SLOTMACHINE_GAME_NAME))
                .addOption(
                        OptionType.INTEGER,
                        "min-players",
                        "Minimum number of players (for Blackjack)",
                        false)
                .addOption(
                        OptionType.INTEGER,
                        "max-players",
                        "Maximum number of players (for Blackjack)",
                        false);
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
            event.reply(createStartGameMessageBuilder(event).build()).setEphemeral(true).queue();
        } else {
            event.reply(createStartGameMessageBuilder(event).build()).queue();
        }
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        User user = event.getUser();
        ObjectId id = new ObjectId(event.getButton().getId().split(":")[2]);
        String gameName = event.getButton().getId().split(":")[3];
        String label = event.getButton().getLabel();
        TextInput bet =
                TextInput.create("sub", "Your Bet", TextInputStyle.SHORT)
                        .setMinLength(1)
                        .setRequired(true)
                        .build();

        Modal modal =
                Modal.create(
                                this.getName()
                                        + ":"
                                        + user.getId()
                                        + ":"
                                        + id
                                        + ":"
                                        + gameName
                                        + ":"
                                        + label,
                                "Bet")
                        .addActionRows(ActionRow.of(bet))
                        .build();
        event.replyModal(modal).queue();
    }

    @Override
    public void onModalInteraction(@Nonnull ModalInteractionEvent event) {
        String userId = event.getModalId().split(":")[1];
        ObjectId gameId = new ObjectId(event.getModalId().split(":")[2]);
        String gameName = event.getModalId().split(":")[3];
        String label = event.getModalId().split(":")[4];
        if (userId.equals(event.getUser().getId())) {
            Double bet = Double.valueOf(event.getValue("sub").getAsString());
            Player player = playerController.getPlayer(event.getUser().getId());
            if (player.getBalance() >= bet && bet > 0) {
                if (gameName.equals(BLACKJACK_GAME_NAME)) {
                    if (label.equals("JOIN")) {
                        Objects.requireNonNull(event.getUser());
                        blackjackController.joinGame(gameId, event.getUser(), bet, event);
                    } else {
                        blackjackController.startGame(gameId, bet, event);
                    }

                } else if (gameName.equals(SLOTMACHINE_GAME_NAME)) {
                    slotMachineController.startGame(gameId, bet, event);
                }

                if (event.isAcknowledged()) {
                    event.getHook().sendMessage("You bet" + bet + "$").setEphemeral(true).queue();
                } else {
                    event.reply("You bet" + bet + "$").setEphemeral(true).queue();
                }
            } else if (player.getBalance() < bet) {
                event.reply("bet is larger than balance").setEphemeral(true).queue();
            } else if (bet < 0) {
                event.reply("bet cannot be negative").setEphemeral(true).queue();
            }
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

            int minNumberOfPlayers = event.getOption("min-players").getAsInt();
            int maxNumberOfPlayers = event.getOption("max-players").getAsInt();
            gameId =
                    blackjackController.newGame(
                            minNumberOfPlayers, maxNumberOfPlayers, gameStarter);

            embedBuilder.setImage("attachment://blackjack.jpg");
            Button join =
                    Button.primary(
                            this.getName() + ":join" + ":" + gameId + ":" + gameName, "JOIN");
            Button start =
                    Button.danger(
                            this.getName() + ":start" + ":" + gameId + ":" + gameName, "START");

            messageCreateBuilder.addActionRow(join, start);

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
