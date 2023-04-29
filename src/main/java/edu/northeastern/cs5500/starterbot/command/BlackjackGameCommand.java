package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.controller.BlackjackController;
import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.model.Player;
import edu.northeastern.cs5500.starterbot.util.Constant;
import java.awt.Color;
import java.io.InputStream;
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
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

@Singleton
@Slf4j
public class BlackjackGameCommand implements SlashCommandHandler, ButtonHandler, ModalHandler {
    @Inject BlackjackController blackjackController;
    @Inject PlayerController playerController;

    @Inject
    public BlackjackGameCommand() {}

    @NotNull
    @Override
    public String getName() {

        return Constant.BLACKJACK_GAME_NAME.toLowerCase();
    }

    @NotNull
    @Override
    public CommandData getCommandData() {
        String name = getName();
        Objects.requireNonNull(name);
        return Commands.slash(name, "start a blackjack game!")
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
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String userId = event.getModalId().split(":")[1];
        ObjectId gameId = new ObjectId(event.getModalId().split(":")[2]);
        String label = event.getModalId().split(":")[4];
        if (userId.equals(event.getUser().getId())) {
            Objects.requireNonNull(event.getValue("sub"));
            ModalMapping mm = event.getValue("sub");
            Objects.requireNonNull(mm);
            double bet = Double.parseDouble(mm.getAsString());
            Player player = playerController.getPlayer(event.getUser().getId());
            boolean isJoin = label.equals("JOIN");
            if (!checkBets(bet, player)) {
                event.reply("bet is illegal").setEphemeral(true).queue();
                return;
            }
            if (!checkGameConfig(gameId, isJoin)) {
                event.reply("Not satisfied with the player number requirement")
                        .setEphemeral(true)
                        .queue();
                return;
            }

            if (isJoin) {
                Objects.requireNonNull(event.getUser());
                blackjackController.joinGame(gameId, event.getUser(), bet, event);
            } else {
                if (!userId.equals(
                        blackjackController
                                .getBlackjackRepository()
                                .get(gameId)
                                .getHolder()
                                .getDiscordId())) {
                    event.reply(
                                    "You are not the holder of the game, please wait the holder to start...")
                            .setEphemeral(true)
                            .queue();
                    return;
                }
                blackjackController.startGame(gameId, bet, event);
            }
            if (event.isAcknowledged()) {
                event.getHook().sendMessage("You bet$" + bet).setEphemeral(true).queue();
            } else {
                event.reply("You bet$" + bet).setEphemeral(true).queue();
            }
        }
    }

    private boolean checkBets(double bet, Player player) {
        if (player.getBalance() < bet || bet <= 0) return false;
        return true;
    }

    private boolean checkGameConfig(ObjectId gameId, boolean isJoin) {
        return blackjackController.checkConfig(gameId, isJoin);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        User user = event.getUser();
        String eventId = event.getButton().getId();
        Objects.requireNonNull(eventId);
        ObjectId id = new ObjectId(eventId.split(":")[2]);
        String gameName = eventId.split(":")[3];
        String label = event.getButton().getLabel();
        if (label.equals("JOIN") && blackjackController.containsUserId(id, user)) {
            event.reply("You have already joined").setEphemeral(true).queue();
            return;
        }
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
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        log.info("blackjack");
        event.reply(createStartGameMessageBuilder(event).build()).queue();
    }

    private MessageCreateBuilder createStartGameMessageBuilder(
            @Nonnull SlashCommandInteractionEvent event) {
        User gameStarter = event.getUser();
        String gameName = Constant.BLACKJACK_GAME_NAME;
        ObjectId gameId = null;

        EmbedBuilder embedBuilder =
                new EmbedBuilder()
                        .setTitle(gameName)
                        .setDescription(gameStarter.getAsMention() + "start a new game")
                        .setImage("attachment://cover_image.png")
                        .setColor(Color.BLUE);

        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.addEmbeds(embedBuilder.build());
        OptionMapping op1 = event.getOption("min-players");
        OptionMapping op2 = event.getOption("max-players");
        Objects.requireNonNull(op1);
        Objects.requireNonNull(op2);
        int minNumberOfPlayers = op1.getAsInt();
        int maxNumberOfPlayers = op2.getAsInt();
        gameId = blackjackController.newGame(minNumberOfPlayers, maxNumberOfPlayers, gameStarter);

        Button join =
                Button.primary(this.getName() + ":join" + ":" + gameId + ":" + gameName, "JOIN");
        Button start =
                Button.danger(this.getName() + ":start" + ":" + gameId + ":" + gameName, "START");

        String filePath = "/blackjack.png";
        InputStream is = getClass().getResourceAsStream(filePath);
        Objects.requireNonNull(is);
        messageCreateBuilder
                .addFiles(FileUpload.fromData(is, "cover_image.png"))
                .addActionRow(join, start);
        return messageCreateBuilder;
    }
}
