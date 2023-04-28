package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.controller.SlotMachineController;
import edu.northeastern.cs5500.starterbot.model.Player;
import edu.northeastern.cs5500.starterbot.util.Constant;
import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class SlotMachineGameCommand implements SlashCommandHandler, ButtonHandler, ModalHandler {

    @Inject SlotMachineController slotMachineController;
    @Inject PlayerController playerController;

    @Inject
    public SlotMachineGameCommand() {}

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
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
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String userId = event.getModalId().split(":")[1];
        ObjectId gameId = new ObjectId(event.getModalId().split(":")[2]);
        if (userId.equals(event.getUser().getId())) {
            Double bet = Double.valueOf(event.getValue("sub").getAsString());
            Player player = playerController.getPlayer(event.getUser().getId());
            if (!checkBets(bet, player)) {
                event.reply("bet is illegal").setEphemeral(true).queue();
                return;
            }
            slotMachineController.startGame(gameId, bet, event);
        }
    }

    @NotNull
    @Override
    public String getName() {
        return Constant.SLOTMACHINE_GAME_NAME.toLowerCase();
    }

    @NotNull
    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), "start a slot machine game!");
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        event.reply(createStartGameMessageBuilder(event).build()).queue();
    }

    private boolean checkBets(double bet, Player player) {
        if (player.getBalance() < bet || bet <= 0) return false;
        return true;
    }

    private MessageCreateBuilder createStartGameMessageBuilder(
            @Nonnull SlashCommandInteractionEvent event) {
        String gameName = Constant.SLOTMACHINE_GAME_NAME;
        User gameStarter = event.getUser();
        ObjectId gameId = null;
        File file = null;
        EmbedBuilder embedBuilder =
                new EmbedBuilder()
                        .setTitle(gameName)
                        .setDescription(gameStarter.getAsMention() + "start a new game")
                        .setImage("attachment://cover_image.png")
                        .setColor(Color.BLUE);
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.addEmbeds(embedBuilder.build());
        gameId = slotMachineController.newGame(gameStarter);
        Button start =
                Button.danger(this.getName() + ":start" + ":" + gameId + ":" + gameName, "START");
        String filePath = "/slotMachine.png";
        InputStream is = getClass().getResourceAsStream(filePath);
        Objects.requireNonNull(is);
        messageCreateBuilder
            .addFiles(FileUpload.fromData(is, "cover_image.png"))
            .addActionRow(start);
        return messageCreateBuilder;
    }
}
