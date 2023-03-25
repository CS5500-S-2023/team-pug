package edu.northeastern.cs5500.starterbot.command;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;


import java.util.HashMap;
import java.util.Random;

@Singleton
@Slf4j
public class SlotMachineCommand implements SlashCommandHandler, ButtonHandler {
    // Define the symbols and their payouts
    private static final HashMap<String, Integer> SYMBOLS = new HashMap<String, Integer>() {{
        put(":apple:", 5);
        put(":grapes:", 10);
        put(":tangerine:", 15);
        put(":watermelon:", 20);
        put(":cherries:", 25);
        put(":strawberry:", 30);
        put(":pineapple:", 35);
        put(":peach:", 40);
        put(":moneybag:", 50);
    }};

    @Inject
    public SlotMachineCommand() {}

    @Override
    @Nonnull
    public String getName() {
        return "slot-machine-game";
    }

    @Override
    @Nonnull
    public CommandData getCommandData() {
        return Commands.slash(getName(), "start to play slot machine game");
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {


        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder =
                messageCreateBuilder.addActionRow(
                        Button.primary(this.getName() + ":start", "Start"),
                        Button.danger(this.getName() + ":cancel", "Cancel"));
        messageCreateBuilder = messageCreateBuilder.setContent("Do want to start?");
        event.reply(messageCreateBuilder.build()).setEphemeral(true).queue();

    }
    
    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        if(event.getButton().getLabel().equals("Start"))
        {
                    
            String[] reels = {spin(), spin(), spin()};
            int payout = sumPayouts(reels);

            // Send the result to the Discord channel
            //event.getChannel().sendMessage(String.format("%s %s %s\nPayout: %d", reels[0], reels[1], reels[2], payout)).queue();
            event.reply(String.format("%s %s %s\nPayout: %d", reels[0], reels[1], reels[2], payout)).queue();
        }
        else
        {
            event.reply(event.getButton().getLabel()).queue();
        }
        
    }


    private static String spin() {
        // Randomly select a symbol from the list of symbols
        String[] symbols = SYMBOLS.keySet().toArray(new String[SYMBOLS.size()]);
        return symbols[new Random().nextInt(symbols.length)];
    }

    private static int sumPayouts(String[] reels) {
        // Calculate the total payout based on the combination of symbols
        int payout = 0;
        for (String symbol : reels) {
            payout += SYMBOLS.get(symbol);
        }
        return payout;
    }
}
