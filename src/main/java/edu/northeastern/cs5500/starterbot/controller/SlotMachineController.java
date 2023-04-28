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
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import org.bson.types.ObjectId;

/**
 * SlotMachineController handles the game logic and interaction between the SlotMachineGame and the
 * Discord events.
 */
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

    /**
     * Creates a new SlotMachine game and saves it in the repository.
     *
     * @param player the User who started the game
     * @return ObjectId the unique identifier of the new game
     */
    public ObjectId newGame(User player) {
        SlotMachineGame slotMachineGame =
                new SlotMachineGame(
                        new Config("BLACKJACK_GAME_NAME"), new SlotMachinePlayer(player.getId()));
        slotMachineRepository.add(slotMachineGame);
        return slotMachineGame.getId();
    }

    /**
     * Starts the SlotMachine game for the given gameId and bet amount.
     *
     * @param gameId the unique identifier of the game
     * @param bet the bet amount for the game
     * @param event the ModalInteractionEvent that triggered the start of the game
     */
    public void startGame(ObjectId gameId, double bet, @Nonnull ModalInteractionEvent event) {
        SlotMachineGame slotMachineGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(slotMachineGame);
        // SlotMachinePlayer currentPlayer = slotMachineGame.getCurrentPlayer();
        // System.out.println(currentPlayer.getDiscordId());
        slotMachineGame.getHolder().setBet(bet);
        slotMachineRepository.update(slotMachineGame);
        System.out.println(slotMachineGame.getCurrentPlayer().getBet());
        if (!slotMachineGame.canStart()) {
            sendMessage(event, "unable to play");
        } else {
            // "Game start"
            sendMessage(
                    event,
                    SlotMachineView.createSlotMachineMessageBuilder(event.getUser(), gameId)
                            .build());
        }
    }

    /**
     * Retrieves a SlotMachineGame object based on the given ObjectId.
     *
     * @param id the unique identifier of the game
     * @return SlotMachineGame the game object, or null if not found
     */
    public SlotMachineGame getGameFromObjectId(ObjectId id) {
        Collection<SlotMachineGame> collection = slotMachineRepository.getAll();
        for (SlotMachineGame slotMachineGame : collection) {
            if (slotMachineGame.getId().equals(id)) return slotMachineGame;
        }
        return null;
    }

    private void sendMessage(@Nonnull ModalInteractionEvent event, @Nonnull String messageContent) {

        event.reply(messageContent).setEphemeral(true).queue();
    }

    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull String messageContent) {

        event.reply(messageContent).setEphemeral(true).queue();
    }

    private void sendMessage(
            @Nonnull ModalInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {
        event.reply(messageCreateData).setEphemeral(true).queue();
    }

    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {
        event.reply(messageCreateData).setEphemeral(true).queue();
    }

    /**
     * Handles the player's action in the SlotMachine game.
     *
     * @param gameId the unique identifier of the game
     * @param action the action the player took, either "PLAY" or "END"
     * @param event the ButtonInteractionEvent that triggered the action
     */
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
                updateBalance(new Result(event.getUser().getId(), payout));

                event.reply(
                                String.format(
                                        "%s %s %s\nPayout: %f",
                                        reels[0], reels[1], reels[2], payout))
                        .setEphemeral(true)
                        .queue();

                event.getHook()
                        .sendMessage(
                                SlotMachineView.createSlotMachineMessageBuilder(
                                                event.getUser(), gameId)
                                        .build())
                        .setEphemeral(true)
                        .queue();
            }
            case "END" -> {
                slotMachineRepository.delete(slotMachineGame.getId());
                sendMessage(
                        event,
                        SlotMachineView.createSlotMachineResultMessageBuilder(
                                        event.getUser(),
                                        playerController
                                                .getPlayer(currentPlayer.getDiscordId())
                                                .getBalance())
                                .build());
            }
        }
    }

    public boolean containsGameId(ObjectId id) {
        return slotMachineRepository.contains(id);
    }

    /**
     * Updates the player's balance based on the game result.
     *
     * @param result the Result object containing the User and bet amount
     */
    public void updateBalance(Result result) {
        String discordId = result.getDiscordId();
        Double amount = result.getBet();
        playerController.updateBalance(discordId, amount);
    }
}
