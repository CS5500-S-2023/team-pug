package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackGame;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackPlayer;
import edu.northeastern.cs5500.starterbot.game.blackjack.Card;
import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.util.BlackjackUtils;
import edu.northeastern.cs5500.starterbot.view.BlackjackView;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the main controller for the blackjack game within a Discord bot
 * application. It handles game creation, player actions, and game flow.
 */
public class BlackjackController {
    private GenericRepository<BlackjackGame> blackjackRepository;
    private PlayerController playerController;
    /**
     * Creates a new instance of the BlackjackController with the specified game repository and
     * player controller.
     *
     * @param blackjackRepository a {@link GenericRepository<BlackjackGame>} for storing blackjack
     *     games
     * @param playerController a {@link PlayerController} for managing players and their balances
     */
    @Inject
    public BlackjackController(
            GenericRepository<BlackjackGame> blackjackRepository,
            PlayerController playerController) {
        this.blackjackRepository = blackjackRepository;
        this.playerController = playerController;
    }
    /**
     * Creates a new blackjack game with the specified minimum and maximum number of players and
     * assigns the holder of the game.
     *
     * @param min the minimum number of players required to start the game
     * @param max the maximum number of players allowed to join the game
     * @param holder a {@link User} who holds the game
     * @return the {@link ObjectId} of the created game
     */
    public ObjectId newGame(int min, int max, User holder) {
        BlackjackGame blackjackGame =
                new BlackjackGame(
                        new Config("BLACKJACK_GAME_NAME", min, max),
                        new BlackjackPlayer(holder.getId()));
        blackjackRepository.add(blackjackGame);
        return blackjackGame.getId();
    }
    /**
     * Starts a blackjack game with the specified game ID, bet amount, and modal interaction event.
     *
     * @param gameId the {@link ObjectId} of the game to start
     * @param bet the amount the player has bet
     * @param event the {@link ModalInteractionEvent} representing the start game action
     */
    public void startGame(ObjectId gameId, double bet, @NotNull ModalInteractionEvent event) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        int index = blackjackGame.getCurrentIndex();
        blackjackGame.getPlayers().get(index).setBet(bet);
        blackjackGame.initPlayerCard();
        blackjackRepository.update(blackjackGame);
        sendMessage(
                event,
                BlackjackView.createBlackjackMessageBuilder(event.getUser(), gameId).build());
    }
    /**
     * Allows a user to join the game with the specified game ID, bet amount, and modal interaction
     * event.
     *
     * @param gameId the {@link ObjectId} of the game to join
     * @param user the {@link User} who wants to join the game
     * @param bet the amount the user wants to bet
     * @param event the {@link ModalInteractionEvent} representing the join game action
     */
    public void joinGame(
            ObjectId gameId, User user, double bet, @NotNull ModalInteractionEvent event) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        BlackjackPlayer normalPlayerayer = new BlackjackPlayer(user.getId());
        normalPlayerayer.setBet(bet);
        blackjackGame.joinPlayer(normalPlayerayer);
        blackjackRepository.update(blackjackGame);
        Objects.requireNonNull(event);
        sendMessage(event, user.getAsMention() + ": joined");
    }

    /**
     * get game via ID
     *
     * @param id
     * @return
     */
    private BlackjackGame getGameFromObjectId(ObjectId id) {
        Collection<BlackjackGame> collection = blackjackRepository.getAll();
        for (BlackjackGame blackjackGame : collection) {
            if (blackjackGame.getId().equals(id)) return blackjackGame;
        }
        return null;
    }
    /**
     * Handles the player's action during the game, including "SHOW CARD", "HIT", "SURRENDER",
     * "STAND", and "DOUBLE DOWN".
     *
     * @param gameId the {@link ObjectId} of the game
     * @param action the action the player wants to perform
     * @param event the {@link ButtonInteractionEvent} representing the player's action
     */
    public void handlePlayerAction(
            ObjectId gameId, String action, @Nonnull ButtonInteractionEvent event) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        int index = blackjackGame.getCurrentIndex();
        BlackjackPlayer currentPlayer = blackjackGame.getPlayers().get(index);
        switch (action) {
            case "SHOW CARD" -> {
                showCard(currentPlayer, event);
                return;
            }
            case "HIT" -> {
                blackjackGame.hit();
                blackjackRepository.update(blackjackGame);
            }
            case "SURRENDER" -> {
                blackjackGame.surrender();
                blackjackRepository.update(blackjackGame);
            }
            case "STAND" -> {
                blackjackGame.stand();
                blackjackRepository.update(blackjackGame);
            }
            case "DOUBLE DOWN" -> {
                Double bet = blackjackGame.doubledown();
                blackjackRepository.update(blackjackGame);
                event.reply("You bet $" + bet.toString()).setEphemeral(true).queue(null, null);
            }
            default -> {}
        }
        // move to the next player
        if (!blackjackGame.isEndOfRound()) {
            BlackjackPlayer blackjackPlayer = blackjackGame.nextPlayer();
            blackjackRepository.update(blackjackGame);
            String discordId = blackjackPlayer.getDiscordId();
            Objects.requireNonNull(discordId);
            event.getJDA()
                    .retrieveUserById(discordId)
                    .queue(
                            user -> {
                                sendMessage(
                                        event,
                                        BlackjackView.createBlackjackMessageBuilder(user, gameId)
                                                .build());
                            });

        } else {
            // check if need to end the game
            if (blackjackGame.getDealer().getHand().isBust()) {
                List<Result> results = blackjackGame.shareAllBets();
                updateBalance(results);
                sendMessage(
                        event,
                        BlackjackView.createBlackjackResultMessageBuilder(results, event).build());
                ObjectId gameId1 = blackjackGame.getId();
                Objects.requireNonNull(gameId1);
                blackjackRepository.delete(gameId1);
                return;
            }
            int continuePlayerSize = 0;
            for (BlackjackPlayer blackjackPlayer : blackjackGame.getPlayers()) {
                if (!blackjackPlayer.getHand().isBust() && !blackjackPlayer.isStop()) {
                    continuePlayerSize++;
                }
            }
            if (continuePlayerSize < blackjackGame.getConfig().getMinPlayers()) {
                List<Result> results = blackjackGame.shareAllBets();
                updateBalance(results);
                sendMessage(
                        event,
                        BlackjackView.createBlackjackResultMessageBuilder(results, event).build());
                ObjectId gameId1 = blackjackGame.getId();
                Objects.requireNonNull(gameId1);
                blackjackRepository.delete(gameId1);
                return;
            }
            // continue
            BlackjackPlayer blackjackPlayer = blackjackGame.nextPlayer();
            blackjackRepository.update(blackjackGame);
            String discordId = blackjackPlayer.getDiscordId();
            Objects.requireNonNull(discordId);
            event.getJDA()
                    .retrieveUserById(discordId)
                    .queue(
                            user -> {
                                sendMessage(
                                        event,
                                        BlackjackView.createBlackjackMessageBuilder(user, gameId)
                                                .build());
                            });
        }
    }
    /**
     * Shows the player's cards in a private message.
     *
     * @param currentPlayer the {@link BlackjackPlayer} whose cards need to be shown
     * @param event the {@link ButtonInteractionEvent} representing the show card action
     */
    private void showCard(BlackjackPlayer currentPlayer, @Nonnull ButtonInteractionEvent event) {
        for (Card card : currentPlayer.getHand().getCards()) {
            String cardImagePath = BlackjackUtils.mapCardImageLocation(card);
            sendPrivateFile(event, cardImagePath);
        }
    }
    /**
     * Sends a message to the channel where the interaction event occurred.
     *
     * @param event the {@link ModalInteractionEvent} representing the interaction
     * @param messageContent the content of the message to send
     */
    private void sendMessage(@Nonnull ModalInteractionEvent event, @Nonnull String messageContent) {
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageContent).queue();
        } else {
            event.reply(messageContent).queue();
        }
    }
    /**
     * Sends a private file to the user who triggered the {@link ButtonInteractionEvent}.
     *
     * @param event the {@link ButtonInteractionEvent} representing the interaction
     * @param filePath the path to the file to send
     */
    private void sendPrivateFile(@Nonnull ButtonInteractionEvent event, String filePath) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setImage("attachment://card_image.png");
        InputStream is = getClass().getResourceAsStream(filePath);
        Objects.requireNonNull(is);
        MessageCreateBuilder messageCreateBuilder =
                new MessageCreateBuilder()
                        .addEmbeds(embedBuilder.build())
                        .addFiles(FileUpload.fromData(is, "card_image.png"));
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageCreateBuilder.build()).setEphemeral(true).queue();
        } else {
            event.reply(messageCreateBuilder.build()).setEphemeral(true).queue();
        }
    }
    /**
     * Sends a message to the channel where the interaction event occurred.
     *
     * @param event the {@link ModalInteractionEvent} representing the interaction
     * @param messageCreateData the {@link MessageCreateData} to send
     */
    private void sendMessage(
            @Nonnull ModalInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {

        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageCreateData).queue();

        } else {
            event.reply(messageCreateData).queue();
        }
    }
    /**
     * Sends a message to the channel where the interaction event occurred.
     *
     * @param event the {@link ButtonInteractionEvent} representing the interaction
     * @param messageCreateData the {@link MessageCreateData} to send
     */
    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {

        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageCreateData).queue();

        } else {
            event.reply(messageCreateData).queue();
        }
    }
    /**
     * Updates the balance of the players based on the results of the game.
     *
     * @param results the {@link List} of {@link Result} objects representing the results of the
     *     game
     */
    private void updateBalance(List<Result> results) {
        for (Result result : results) {
            String discordId = result.getDiscordId();
            Double amount = result.getBet();
            playerController.updateBalance(discordId, amount);
        }
    }
    /**
     * Checks if the game with the specified game ID exists in the repository.
     *
     * @param id the {@link ObjectId} representing the game ID
     * @return true if the game ID exists in the repository, false otherwise
     */
    public boolean containsGameId(ObjectId id) {
        return blackjackRepository.contains(id);
    }
    /**
     * Checks if the user with the specified ID is in the game with the specified game ID.
     *
     * @param gameID the {@link ObjectId} representing the game ID
     * @param user the {@link User} object representing the user
     * @return true if the user ID is in the game, false otherwise
     */
    public boolean containsUserId(ObjectId gameID, User user) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameID);
        Objects.requireNonNull(blackjackGame);
        if (blackjackGame.containsId(user.getId())) {
            return true;
        }
        return false;
    }
    /**
     * Gets the {@link GenericRepository} for {@link BlackjackGame}.
     *
     * @return the {@link GenericRepository} for {@link BlackjackGame}
     */
    public GenericRepository<BlackjackGame> getBlackjackRepository() {
        return blackjackRepository;
    }
    /**
     * Checks the game configuration for the specified game ID and determines if the game can be
     * started or joined based on the input parameter.
     *
     * @param gameId the {@link ObjectId} representing the game ID
     * @param isJoin a boolean indicating whether the game should be checked for joining (true) or
     *     starting (false)
     * @return true if the game can be joined or started based on the input parameter, false
     *     otherwise
     */
    public boolean checkConfig(ObjectId gameId, boolean isJoin) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        if (isJoin) return blackjackGame.canJoin();
        else return blackjackGame.canStart();
    }
}
