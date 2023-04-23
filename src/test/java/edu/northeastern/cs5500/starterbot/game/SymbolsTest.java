package edu.northeastern.cs5500.starterbot.game;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import edu.northeastern.cs5500.starterbot.game.slotMachine.Symbols;
import org.junit.Test;

public class SymbolsTest {

    @Test
    public void testSymbols() {
        HashMap<String, Integer> symbols = Symbols.SYMBOLS;
        assertEquals(9, symbols.size());
        assertEquals(Integer.valueOf(5), symbols.get(":apple:"));
        assertEquals(Integer.valueOf(10), symbols.get(":grapes:"));
        assertEquals(Integer.valueOf(15), symbols.get(":tangerine:"));
        assertEquals(Integer.valueOf(20), symbols.get(":watermelon:"));
        assertEquals(Integer.valueOf(25), symbols.get(":cherries:"));
        assertEquals(Integer.valueOf(30), symbols.get(":strawberry:"));
        assertEquals(Integer.valueOf(35), symbols.get(":pineapple:"));
        assertEquals(Integer.valueOf(40), symbols.get(":peach:"));
        assertEquals(Integer.valueOf(50), symbols.get(":moneybag:"));
    }
}

