// package edu.northeastern.cs5500.starterbot.game.blackjack;

// /**
// * BlackjackDealer class represents a dealer in the game of Blackjack. It
// * extends the
// * BlackjackPlayer class with specific behaviors for a dealer.
// */
// public class BlackjackDealer extends BlackjackPlayer {
// public BlackjackDealer() {
// super();
// }

// /**
// * Constructs a BlackjackDealer instance with the specified User.
// *
// * @param discordId A User object representing the dealer in the game.
// */
// public BlackjackDealer(String discordId) {
// super(discordId);
// }

// /**
// * Constructs a BlackjackDealer instance with the specified User and bet.
// *
// * @param discordId A User object representing the dealer in the game.
// * @param bet A double value representing the initial bet amount.
// */
// public BlackjackDealer(String discordId, double bet) {
// super(discordId, bet);
// }

// /**
// * Checks if the dealer can play based on their current hand value.
// *
// * @return A boolean value. True if the dealer's hand value is less than or
// * equal to 17, false
// * otherwise.
// */
// @Override
// public boolean canPlay() {
// return getHand().getCurrentValue() <= 17;
// }

// /**
// * Indicates whether the dealer wants to play.
// *
// * @return A boolean value. Always returns true for the dealer.
// */
// @Override
// public boolean wantPlay() {
// return true;
// }
// }
