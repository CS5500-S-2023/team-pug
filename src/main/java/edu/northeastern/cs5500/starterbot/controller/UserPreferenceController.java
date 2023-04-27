package edu.northeastern.cs5500.starterbot.controller;

import com.mongodb.lang.Nullable;
import edu.northeastern.cs5500.starterbot.model.UserPreference;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * UserPreferenceController is a class for managing user preference-related operations, such as
 * setting and getting the preferred name for a user.
 */
public class UserPreferenceController {

    GenericRepository<UserPreference> userPreferenceRepository;
    /**
     * Constructor for the UserPreferenceController.
     *
     * @param userPreferenceRepository A GenericRepository object that handles the storage of
     *     UserPreference objects.
     */
    @Inject
    UserPreferenceController(GenericRepository<UserPreference> userPreferenceRepository) {
        this.userPreferenceRepository = userPreferenceRepository;

        if (userPreferenceRepository.count() == 0) {
            UserPreference userPreference = new UserPreference();
            userPreference.setDiscordUserId("1234");
            userPreference.setPreferredName("Alex");
            userPreferenceRepository.add(userPreference);
        }
    }
    /**
     * Sets the preferred name for the user with the given Discord member ID.
     *
     * @param discordMemberId A String containing the Discord member ID.
     * @param preferredName A String containing the preferred name of the user.
     */
    public void setPreferredNameForUser(String discordMemberId, String preferredName) {
        UserPreference userPreference = getUserPreferenceForMemberId(discordMemberId);

        userPreference.setPreferredName(preferredName);
        userPreferenceRepository.update(userPreference);
    }
    /**
     * Retrieves the preferred name for the user with the given Discord member ID.
     *
     * @param discordMemberId A String containing the Discord member ID.
     * @return A String representing the preferred name of the user, or null if not found.
     */
    @Nullable
    public String getPreferredNameForUser(String discordMemberId) {
        return getUserPreferenceForMemberId(discordMemberId).getPreferredName();
    }
    /**
     * Retrieves the UserPreference object for the user with the given Discord member ID. If the
     * user preference is not found, a new UserPreference object will be created and added to the
     * repository.
     *
     * @param discordMemberId A String containing the Discord member ID.
     * @return A UserPreference object representing the user preference for the given Discord member
     *     ID.
     */
    @Nonnull
    public UserPreference getUserPreferenceForMemberId(String discordMemberId) {
        Collection<UserPreference> userPreferences = userPreferenceRepository.getAll();
        for (UserPreference currentUserPreference : userPreferences) {
            if (currentUserPreference.getDiscordUserId().equals(discordMemberId)) {
                return currentUserPreference;
            }
        }

        UserPreference userPreference = new UserPreference();
        userPreference.setDiscordUserId(discordMemberId);
        userPreferenceRepository.add(userPreference);
        return userPreference;
    }
}
