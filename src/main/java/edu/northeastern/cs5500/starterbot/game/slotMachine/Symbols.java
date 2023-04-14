package edu.northeastern.cs5500.starterbot.game.slotMachine;

import java.util.HashMap;

public class Symbols {

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
