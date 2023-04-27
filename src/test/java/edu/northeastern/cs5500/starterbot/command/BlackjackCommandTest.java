package edu.northeastern.cs5500.starterbot.command;

import static edu.northeastern.cs5500.starterbot.util.Constant.BLACKJACK_GAME_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.northeastern.cs5500.starterbot.controller.BlackjackController;
import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BlackjackCommandTest {
    Button button;
    ButtonInteractionEvent buttonInteractionEvent;
    ReplyCallbackAction replyCallbackAction;
    ObjectId gameId;
    User user;

    @BeforeEach
    void setUp() {
        button = Mockito.mock(Button.class);
        buttonInteractionEvent = mock(ButtonInteractionEvent.class);
        replyCallbackAction = mock(ReplyCallbackAction.class);
        user = mock(User.class);

        when(buttonInteractionEvent.getButton()).thenReturn(button);
        when(buttonInteractionEvent.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("123");
        when(button.getLabel()).thenReturn("label");
        gameId = new ObjectId();
        String id = gameId.toString();
        String userId = "userId123";
        String buttonId = BLACKJACK_GAME_NAME + ":DOUBLEDOWN" + ":" + id + ":" + userId;
        when(button.getId()).thenReturn(buttonId);
        when(replyCallbackAction.setEphemeral(Mockito.anyBoolean()))
                .thenReturn(replyCallbackAction);
        when(buttonInteractionEvent.reply(Mockito.anyString())).thenReturn(replyCallbackAction);
    }

    @Test
    void getName() {
        BlackjackCommand blackjackCommand = new BlackjackCommand();
        assertEquals(BLACKJACK_GAME_NAME, blackjackCommand.getName());
    }

    @Test
    void onButtonInteraction() {
        BlackjackCommand blackjackCommand = new BlackjackCommand();
        blackjackCommand.blackjackController =
                new BlackjackController(
                        new InMemoryRepository<>(),
                        new PlayerController(new InMemoryRepository<>()));
        blackjackCommand.onButtonInteraction(buttonInteractionEvent);
        verify(buttonInteractionEvent).reply("The game has already finished...");
    }

    @Test
    void onButtonInteractionTest() {
        BlackjackCommand blackjackCommand = new BlackjackCommand();
        blackjackCommand.blackjackController = mock(BlackjackController.class);
        when(blackjackCommand.blackjackController.containsGameId(gameId)).thenReturn(true);
        blackjackCommand.onButtonInteraction(buttonInteractionEvent);
        verify(buttonInteractionEvent).reply("Please Wait, It is not your turn...");
    }
}
