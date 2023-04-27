package edu.northeastern.cs5500.starterbot.command;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/**
 * CommandModule is a Dagger module that provides instances of various command handlers. This class
 * is responsible for providing the dependencies required for SlashCommandHandler, ButtonHandler,
 * and ModalHandler implementations.
 */
@Module
public class CommandModule {

    /**
     * Provides an instance of PlayerInfoCommand.
     *
     * @param playerInfoCommand The PlayerInfoCommand instance.
     * @return A SlashCommandHandler representing the PlayerInfoCommand.
     */
    @Provides
    @IntoSet
    public SlashCommandHandler providePlayerInfoCommand(PlayerInfoCommand playerInfoCommand) {
        return playerInfoCommand;
    }
    /**
     * Provides an instance of SetUserNameCommand.
     *
     * @param setUserNameCommand The SetUserNameCommand instance.
     * @return A SlashCommandHandler representing the SetUserNameCommand.
     */
    @Provides
    @IntoSet
    public SlashCommandHandler provideSetNameCommand(SetUserNameCommand setUserNameCommand) {
        return setUserNameCommand;
    }
    /**
     * Provides an instance of GameCommand.
     *
     * @param gameCommand The GameCommand instance.
     * @return A SlashCommandHandler representing the GameCommand.
     */
    @Provides
    @IntoSet
    public SlashCommandHandler provideGameSlashCommand(GameCommand gameCommand) {
        return gameCommand;
    }
    /**
     * Provides an instance of GameCommand as a ButtonHandler.
     *
     * @param gameCommand The GameCommand instance.
     * @return A ButtonHandler representing the GameCommand.
     */
    @Provides
    @IntoSet
    public ButtonHandler provideGameButtonCommand(GameCommand gameCommand) {
        return gameCommand;
    }
    /**
     * Provides an instance of GameCommand as a ModalHandler.
     *
     * @param gameCommand The GameCommand instance.
     * @return A ModalHandler representing the GameCommand.
     */
    @Provides
    @IntoSet
    public ModalHandler provideGameModalCommand(GameCommand gameCommand) {
        return gameCommand;
    }

    /**
     * Provides an instance of BlackjackCommand.
     *
     * @param blackjackCommand The BlackjackCommand instance.
     * @return A ButtonHandler representing the BlackjackCommand.
     */
    @Provides
    @IntoSet
    public ButtonHandler provideBlackjackCommand(BlackjackCommand blackjackCommand) {
        return blackjackCommand;
    }
    /**
     * Provides an instance of SlotMachineCommand.
     *
     * @param slotMachineCommand The SlotMachineCommand instance.
     * @return A ButtonHandler representing the SlotMachineCommand.
     */
    @Provides
    @IntoSet
    public ButtonHandler provideSlotMachineCommand(SlotMachineCommand slotMachineCommand) {
        return slotMachineCommand;
    }
}
