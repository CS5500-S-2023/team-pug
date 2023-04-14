package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.game.Config;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackGame;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackNormalPlayer;
import edu.northeastern.cs5500.starterbot.game.blackjack.BlackjackPlayer;
import edu.northeastern.cs5500.starterbot.game.blackjack.Card;
import edu.northeastern.cs5500.starterbot.game.blackjack.Result;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.util.BlackjackUtils;
import edu.northeastern.cs5500.starterbot.view.BlackjackView;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.bson.types.ObjectId;

/** Connect with blackjack game and UI */
public class BlackjackController {
    private GenericRepository<BlackjackGame> blackjackRepository;
    private PlayerController playerController;

    @Inject
    public BlackjackController(
            GenericRepository<BlackjackGame> blackjackRepository,
            PlayerController playerController) {
        this.blackjackRepository = blackjackRepository;
        this.playerController = playerController;
    }

    public ObjectId newGame(int min, int max, User holder) {
        BlackjackGame blackjackGame =
                new BlackjackGame(
                        new Config("BLACKJACK_GAME_NAME", min, max),
                        new BlackjackNormalPlayer(holder));
        blackjackRepository.add(blackjackGame);
        return blackjackGame.getId();
    }

    public void startGame(ObjectId gameId, @Nonnull ButtonInteractionEvent event) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        BlackjackPlayer currentPlayer = blackjackGame.getCurrentPlayer();
        if (!blackjackGame.canStart()) {
            sendMessage(event, "unable to play due to the minimum size");
        } else {
            // "Game start"
            blackjackGame.initPlayerCard();
            sendMessage(
                    event,
                    BlackjackView.createBlackjackMessageBuilder(currentPlayer.getUser(), gameId)
                            .build());
        }
    }

    public void joinGame(ObjectId gameId, User user, @Nonnull ButtonInteractionEvent event) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        if (blackjackGame.canJoin()) {
            blackjackGame.joinPlayer(new BlackjackNormalPlayer(user));
            sendMessage(event, user.getAsMention() + ": joined");
        } else {
            sendMessage(event, "Unable to join due to the maximum size");
        }
    }

    private BlackjackGame getGameFromObjectId(ObjectId id) {
        Collection<BlackjackGame> collection = blackjackRepository.getAll();
        for (BlackjackGame blackjackGame : collection) {
            if (blackjackGame.getId().equals(id)) return blackjackGame;
        }
        return null;
    }

    public void handlePlayerAction(
            ObjectId gameId, String action, @Nonnull ButtonInteractionEvent event) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        BlackjackPlayer currentPlayer = blackjackGame.getCurrentPlayer();
        switch (action) {
            case "SHOW CARD" -> {
                showCard(currentPlayer, event);
                return;
            }
            case "HIT" -> {
                // add a new card
                blackjackGame.hit();
            }
            case "SURRENDER" -> {
                // TODO surrender, end the game
            }
            case "STAND" -> {
                blackjackGame.stand();
            }
            case "DOUBLEDOWN" -> {
                // TODO double the bet

            }
        }
        // move to the next player
        if (!blackjackGame.isEndOfRound()) {
            BlackjackPlayer blackjackPlayer = blackjackGame.nextPlayer();
            sendMessage(
                    event,
                    BlackjackView.createBlackjackMessageBuilder(blackjackPlayer.getUser(), gameId)
                            .build());
        } else {
            // check if need to end the game
            System.out.println("End of Round");
            if (blackjackGame.getDealer().getHand().isBust()) {
                // dealer lose
                List<Result> results = blackjackGame.shareDealerBets();
                updateBalance(results);
                sendMessage(
                        event, BlackjackView.createBlackjackResultMessageBuilder(results).build());
                blackjackRepository.delete(blackjackGame.getId());
                return;
            }
            int continuePlayerSize = 0;
            for (BlackjackPlayer blackjackPlayer : blackjackGame.getPlayers()) {
                if (!blackjackPlayer.getHand().isBust() && !blackjackPlayer.isStop()) {
                    continuePlayerSize++;
                }
            }
            if (continuePlayerSize < blackjackGame.getConfig().getMinPlayers()) {
                // one player win
                List<Result> results = blackjackGame.shareAllBets();
                updateBalance(results);
                // blackjackGame.removeAllplayers();
                sendMessage(
                        event, BlackjackView.createBlackjackResultMessageBuilder(results).build());
                blackjackRepository.delete(blackjackGame.getId());
                return;
            }
            // continue
            BlackjackPlayer blackjackPlayer = blackjackGame.nextPlayer();
            sendMessage(
                    event,
                    BlackjackView.createBlackjackMessageBuilder(blackjackPlayer.getUser(), gameId)
                            .build());
        }
    }

    private void showCard(BlackjackPlayer currentPlayer, @Nonnull ButtonInteractionEvent event) {
        for (Card card : currentPlayer.getHand().getCards()) {
            // System.out.println(card.toString());
            String cardImagePath = BlackjackUtils.mapCardImageLocation(card);
            sendPrivateFile(event, cardImagePath);
        }
    }

    private void sendPrivateMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull String messageContent) {
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageContent).setEphemeral(true).queue();
        } else {
            event.reply(messageContent).setEphemeral(true).queue();
        }
    }

    private void sendPrivateFile(@Nonnull ButtonInteractionEvent event, String filePath) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setImage("attachment://card_image.png");
        MessageCreateBuilder messageCreateBuilder =
                new MessageCreateBuilder()
                        .addEmbeds(embedBuilder.build())
                        .addFiles(FileUpload.fromData(new File(filePath), "card_image.png"));
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageCreateBuilder.build()).setEphemeral(true).queue();
        } else {
            event.reply(messageCreateBuilder.build()).setEphemeral(true).queue();
        }
    }

    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull String messageContent) {

        event.reply(messageContent).queue();
    }

    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {
        event.reply(messageCreateData).queue();
    }

    private void updateBalance(List<Result> results) {
        for (Result result : results) {
            String discordId = result.getUser().getId();
            Double amount = result.getBet();
            playerController.updateBalance(discordId, amount);
        }
    }

    public boolean containsGameId(ObjectId id) {
        return blackjackRepository.contains(id);
    }
}
