package edu.northeastern.cs5500.starterbot.model;

import static org.junit.jupiter.api.Assertions.*;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPreferenceTest {

    UserPreference userPreference;

    @BeforeEach
    void setUp() throws Exception {
        userPreference = new UserPreference();
    }

    @Test
    void testGetId() {
        ObjectId id = new ObjectId();
        userPreference.setId(id);
        assertEquals(id, userPreference.getId());
    }

    @Test
    void testGetDiscordUserId() {
        String discordUserId = "1234567890";
        userPreference.setDiscordUserId(discordUserId);
        assertEquals(discordUserId, userPreference.getDiscordUserId());
    }

    @Test
    void testGetPreferredName() {
        String preferredName = "Bob";
        userPreference.setPreferredName(preferredName);
        assertEquals(preferredName, userPreference.getPreferredName());
    }

}
