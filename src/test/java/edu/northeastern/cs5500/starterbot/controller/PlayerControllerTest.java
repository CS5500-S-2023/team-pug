package edu.northeastern.cs5500.starterbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import edu.northeastern.cs5500.starterbot.model.Player;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerControllerTest {
    private PlayerController playerController;
    private GenericRepository<Player> playerRepository;

    @BeforeEach
    void setUp() {
        playerRepository = Mockito.mock(GenericRepository.class);
        playerController = new PlayerController(playerRepository);
    }

    @Test
    void testGetPlayer() {
        String discordUserId = "123456";
        Player player = new Player(discordUserId);
        Collection<Player> playerCollection = new ArrayList<>();
        playerCollection.add(player);

        when(playerRepository.getAll()).thenReturn(playerCollection);

        Player retrievedPlayer = playerController.getPlayer(discordUserId);
        assertNotNull(retrievedPlayer);
        assertEquals(discordUserId, retrievedPlayer.getDiscordUserId());

        verify(playerRepository, times(1)).getAll();
    }

    @Test
    void testSetPlayerName() {
        String discordUserId = "123456";
        String newName = "New Player";
        Player player = new Player(discordUserId);
        player.setUserName("Anonymous");
        Collection<Player> playerCollection = new ArrayList<>();
        playerCollection.add(player);

        when(playerRepository.getAll()).thenReturn(playerCollection);

        playerController.setPlayerName(discordUserId, newName);

        verify(playerRepository, times(1)).getAll();
        verify(playerRepository, times(1)).update(any());

        assertEquals(newName, player.getUserName());
    }

    @Test
    void testUpdateBalance() {
        String discordUserId = "123456";
        Double amount = 50.0;
        Player player = new Player(discordUserId);
        player.setBalance(100.0);

        when(playerRepository.getAll()).thenReturn(new ArrayList<>());
        when(playerRepository.add(any())).thenReturn(player);

        playerController.updateBalance(discordUserId, amount);

        verify(playerRepository, times(1)).getAll();
        verify(playerRepository, times(1)).add(any());

        assertEquals(100.0, player.getBalance());
    }
}
