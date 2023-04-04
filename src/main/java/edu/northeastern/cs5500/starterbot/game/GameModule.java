package edu.northeastern.cs5500.starterbot.game;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import edu.northeastern.cs5500.starterbot.command.ButtonHandler;
import edu.northeastern.cs5500.starterbot.command.SlashCommandHandler;

@Module
public class GameModule {

    @Provides
    @IntoSet
    public SlashCommandHandler provideSlotMachineCommand(SlotMachine slotMachine) {
        return slotMachine;
    }

    @Provides
    @IntoSet
    public ButtonHandler provideSlotMachineClickHandler(SlotMachine slotMachine) {
        return slotMachine;
    }

}
