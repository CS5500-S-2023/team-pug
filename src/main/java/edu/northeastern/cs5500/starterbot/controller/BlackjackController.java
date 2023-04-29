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
                        new BlackjackPlayer(holder.getId()));
        blackjackRepository.add(blackjackGame);
        return blackjackGame.getId();
    }

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

    public boolean canStart(ObjectId gameId) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        return blackjackGame.canStart();
    }

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

    private void showCard(BlackjackPlayer currentPlayer, @Nonnull ButtonInteractionEvent event) {
        for (Card card : currentPlayer.getHand().getCards()) {
            String cardImagePath = BlackjackUtils.mapCardImageLocation(card);
            sendPrivateFile(event, cardImagePath);
        }
    }

    private void sendMessage(@Nonnull ModalInteractionEvent event, @Nonnull String messageContent) {
        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageContent).queue();
        } else {
            event.reply(messageContent).queue();
        }
    }

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

    private void sendMessage(
            @Nonnull ModalInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {

        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageCreateData).queue();

        } else {
            event.reply(messageCreateData).queue();
        }
    }

    private void sendMessage(
            @Nonnull ButtonInteractionEvent event, @Nonnull MessageCreateData messageCreateData) {

        if (event.isAcknowledged()) {
            event.getHook().sendMessage(messageCreateData).queue();

        } else {
            event.reply(messageCreateData).queue();
        }
    }

    private void updateBalance(List<Result> results) {
        for (Result result : results) {
            String discordId = result.getDiscordId();
            Double amount = result.getBet();
            playerController.updateBalance(discordId, amount);
        }
    }

    public boolean containsGameId(ObjectId id) {
        return blackjackRepository.contains(id);
    }

    public boolean containsUserId(ObjectId gameID, User user) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameID);
        Objects.requireNonNull(blackjackGame);
        if (blackjackGame.containsId(user.getId())) {
            return true;
        }
        return false;
    }

    public GenericRepository<BlackjackGame> getBlackjackRepository() {
        return blackjackRepository;
    }

    public boolean checkConfig(ObjectId gameId, boolean isJoin) {
        BlackjackGame blackjackGame = getGameFromObjectId(gameId);
        Objects.requireNonNull(blackjackGame);
        if (isJoin) return blackjackGame.canJoin();
        else return blackjackGame.canStart();
    }
}
