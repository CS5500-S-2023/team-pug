package edu.northeastern.cs5500.starterbot.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BlackjackControllerTest {
    BlackjackController blackjackController;
    ReplyCallbackAction replyCallbackAction = mock(ReplyCallbackAction.class);
    User user;
    User user2;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        user2 = mock(User.class);
        PlayerController playerController = new PlayerController(new InMemoryRepository<>());
        blackjackController = new BlackjackController(new InMemoryRepository<>(), playerController);
    }

    @Test
    void newGame() {
        ObjectId id = blackjackController.newGame(1, 2, user);
        assertEquals(1, blackjackController.getBlackjackRepository().getAll().size());
    }

    @Test
    void startGame() {
        ModalInteractionEvent event = mock(ModalInteractionEvent.class);
        when(event.reply(anyString())).thenReturn(replyCallbackAction);
        when(event.reply(Mockito.any(MessageCreateData.class))).thenReturn(replyCallbackAction);

        ObjectId id = blackjackController.newGame(1, 2, user);
        blackjackController.startGame(id, 100, event);
        assertEquals(
                2,
                blackjackController
                        .getBlackjackRepository()
                        .get(id)
                        .getPlayers()
                        .get(0)
                        .getHand()
                        .getCards()
                        .size());
    }

    @Test
    void joinGame() {
        User user2 = mock(User.class);
        ModalInteractionEvent event = mock(ModalInteractionEvent.class);
        when(event.reply(anyString())).thenReturn(replyCallbackAction);

        ObjectId id = blackjackController.newGame(1, 2, user);
        blackjackController.joinGame(id, user2, 100.0, event);
        int playerSize = blackjackController.getBlackjackRepository().get(id).getPlayers().size();
        assertEquals(2, playerSize);
    }

    @Test
    void handlePlayerAction() {
        when(user.getId()).thenReturn("user1");
        when(user.getName()).thenReturn("user1");
        when(user2.getId()).thenReturn("user2");
        when(user2.getName()).thenReturn("user2");

        ButtonInteractionEvent buttonInteractionEvent = mock(ButtonInteractionEvent.class);
        when(buttonInteractionEvent.reply(Mockito.any(MessageCreateData.class)))
                .thenReturn(replyCallbackAction);
        ModalInteractionEvent event = mock(ModalInteractionEvent.class);
        when(event.reply(Mockito.any(MessageCreateData.class))).thenReturn(replyCallbackAction);
        when(event.reply(Mockito.anyString())).thenReturn(replyCallbackAction);
        when(replyCallbackAction.setEphemeral(Mockito.anyBoolean()))
                .thenReturn(replyCallbackAction);

        ObjectId id = blackjackController.newGame(2, 2, user);
        blackjackController.joinGame(id, user2, 100, event);
        blackjackController.startGame(id, 10, event);
        blackjackController.handlePlayerAction(id, "SHOW CARD", buttonInteractionEvent);
        assertEquals(
                user.getId(),
                blackjackController
                        .getBlackjackRepository()
                        .get(id)
                        .getCurrentPlayer()
                        .getUser()
                        .getId());
        blackjackController.handlePlayerAction(id, "HIT", buttonInteractionEvent);
        assertEquals(
                3,
                blackjackController
                        .getBlackjackRepository()
                        .get(id)
                        .getPlayers()
                        .get(0)
                        .getHand()
                        .getCards()
                        .size());

        blackjackController.handlePlayerAction(id, "STAND", buttonInteractionEvent);
        assertNull(blackjackController.getBlackjackRepository().get(id));
    }

    @Test
    void containsGameId() {
        ObjectId id = blackjackController.newGame(1, 2, user);
        assertTrue(blackjackController.containsGameId(id));
    }

    @Test
    void containsUserId() {
        when(user.getId()).thenReturn("user123");
        ObjectId id = blackjackController.newGame(1, 2, user);
        assertTrue(blackjackController.containsUserId(id, user));
    }
}
