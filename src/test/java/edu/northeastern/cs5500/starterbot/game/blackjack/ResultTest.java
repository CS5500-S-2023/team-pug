package edu.northeastern.cs5500.starterbot.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;

import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ResultTest {

    @Test
    void testResultConstructor() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn("123");
        Mockito.when(user.getName()).thenReturn("testUser");
        Double bet = 10.0;
        // Result result = new Result(user, bet);
        // assertNotNull(result.getUser());
        // assertEquals(user, result.getUser());
        //     assertNotNull(result.getBet());
        //     assertEquals(bet, result.getBet());
    }
}
