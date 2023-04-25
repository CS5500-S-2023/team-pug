package edu.northeastern.cs5500.starterbot.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.awt.Color;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SlotMachineViewTest {

    @Test
    void createSlotMachineMessageBuilder() {
        User user = Mockito.mock(User.class);
        ObjectId gameId = ObjectId.get();
        when(user.getId()).thenReturn("123456");
        when(user.getAsMention()).thenReturn("@User");

        MessageCreateBuilder messageBuilder =
                SlotMachineView.createSlotMachineMessageBuilder(user, gameId);

        assertNotNull(messageBuilder);

        MessageEmbed embed = messageBuilder.getEmbeds().get(0);
        assertEquals("SlotMachine", embed.getTitle());
        assertEquals(Color.BLUE, embed.getColor());
        assertEquals(" Have Fun! @User", embed.getDescription());
    }

    @Test
    void createSlotMachineResultMessageBuilder() {
        User user = Mockito.mock(User.class);
        when(user.getName()).thenReturn("TestUser");
        Double balance = 1000.0;

        MessageCreateBuilder messageBuilder =
                SlotMachineView.createSlotMachineResultMessageBuilder(user, balance);

        assertNotNull(messageBuilder);

        MessageEmbed embed = messageBuilder.getEmbeds().get(0);
        assertEquals("SlotMachine Result", embed.getTitle());
        assertEquals("TestUser", embed.getFields().get(0).getName());
        assertEquals("1000.0", embed.getFields().get(0).getValue());
    }
}
