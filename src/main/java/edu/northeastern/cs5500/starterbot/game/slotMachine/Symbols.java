package edu.northeastern.cs5500.starterbot.game.slotMachine;

import java.util.HashMap;

/**
 * Symbols class provides a mapping of symbols to their respective payout values in the slot machine
 * game.
 */
public class Symbols {
    /**
     * A static HashMap containing the symbols and their corresponding payout values. The keys are
     * strings representing the symbols, and the values are integers representing the payout values.
     */
    public static final HashMap<String, Integer> SYMBOLS =
            new HashMap<String, Integer>() {
                {
                    put(":apple:", 5);
                    put(":grapes:", 10);
                    put(":tangerine:", 15);
                    put(":watermelon:", 20);
                    put(":cherries:", 25);
                    put(":strawberry:", 30);
                    put(":pineapple:", 35);
                    put(":peach:", 40);
                    put(":moneybag:", 50);
                }
            };
}
