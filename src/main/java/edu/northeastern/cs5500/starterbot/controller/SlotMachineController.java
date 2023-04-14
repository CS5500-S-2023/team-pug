package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachineGame;
import edu.northeastern.cs5500.starterbot.game.slotMachine.SlotMachinePlayer;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.view.SlotMachineView;
import java.util.Collection;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import org.bson.types.ObjectId;

public class SlotMachineController {
    private GenericRepository<SlotMachineGame> slotMachineRepository;
    private PlayerController playerController;

    @Inject
    public SlotMachineController(
            GenericRepository<SlotMachineGame> slotMachineRepository,
            PlayerController playerController) {
        this.slotMachineRepository = slotMachineRepository;
        this.playerController = playerController;
    }

    public ObjectId newGame(User player) {
        SlotMachineGame slotMachineGame =
                new SlotMachineGame(
                        new Config("BLACKJACK_GAME_NAME"), new SlotMachinePlayer(player));
        slotMachineRepository.add(slotMachineGame);
        return slotMachineGame.getId();
    }

    public void startGame(ObjectId gameId, @Nonnull ButtonInteractionEvent event) {
        SlotMachineGame slotMachineGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(slotMachineGame);
        SlotMachinePlayer currentPlayer = slotMachineGame.getCurrentPlayer();
        if (!slotMachineGame.canStart()) {
            sendMessage(event, "unable to play");
        } else {
            // "Game start"
            sendMessage(
                    event,
                    SlotMachineView.createSlotMachineMessageBuilder(currentPlayer.getUser(), gameId)
                            .build());
        }
    }

    private SlotMachineGame getGameFromObjectId(ObjectId id) {
        Collection<SlotMachineGame> collection = slotMachineRepository.getAll();
        for (SlotMachineGame slotMachineGame : collection) {
            if (slotMachineGame.getId().equals(id)) return slotMachineGame;
        }
        return null;
    }

    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull String messageContent) {

        event.reply(messageContent).setEphemeral(true).queue();
    }

    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {
        event.reply(messageCreateData).setEphemeral(true).queue();
    }

    public void handlePlayerAction(
            ObjectId gameId, String action, @Nonnull ButtonInteractionEvent event) {
        SlotMachineGame slotMachineGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(slotMachineGame);
        SlotMachinePlayer currentPlayer = slotMachineGame.getCurrentPlayer();
        switch (action) {
            case "PLAY" -> {
                Pair<String[], Double> playResult = slotMachineGame.play(event);
                String[] reels = playResult.getLeft();
                double payout = playResult.getRight();
                updateBalance(new Result(currentPlayer.getUser(), payout));

                event.reply(
                                String.format(
                                        "%s %s %s\nPayout: %f",
                                        reels[0], reels[1], reels[2], payout))
                        .setEphemeral(true)
                        .queue();

                event.getHook()
                        .sendMessage(
                                SlotMachineView.createSlotMachineMessageBuilder(
                                                currentPlayer.getUser(), gameId)
                                        .build())
                        .setEphemeral(true)
                        .queue();
            }
            case "END" -> {
                slotMachineRepository.delete(slotMachineGame.getId());
                sendMessage(
                        event,
                        SlotMachineView.createSlotMachineResultMessageBuilder(
                                        currentPlayer.getUser(),
                                        playerController
                                                .getPlayer(currentPlayer.getUser().getId())
                                                .getBalance())
                                .build());
            }
        }
    }

    public boolean containsGameId(ObjectId id) {
        return slotMachineRepository.contains(id);
    }

    public void updateBalance(Result result) {
        String discordId = result.getUser().getId();
        Double amount = result.getBet();
        playerController.updateBalance(discordId, amount);
    }
}
