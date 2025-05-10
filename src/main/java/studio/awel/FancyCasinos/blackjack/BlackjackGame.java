package studio.awel.FancyCasinos.blackjack;

import studio.awel.FancyCasinos.utilities.Gambling;
import studio.awel.FancyCasinos.utilities.awel.bjMath;
import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {

    /**
     * !! Not my code !!
     * Did edit some parts by myself.
     */


    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private double bet;
    private boolean gameEnded = false;

    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    public void startGame(double bet) {
        this.bet = bet;
        this.gameEnded = false;

        // Make sure you initialize hands before using them
        this.playerHand = new Hand();
        this.dealerHand = new Hand();

        // Create and shuffle a new deck
        this.deck = new Deck();
        deck.shuffle();

        // Deal initial cards - using dealCard() instead of drawCard()
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        // Check for blackjack
        if (playerHand.isBlackjack() || dealerHand.isBlackjack()) {
            gameEnded = true;
        }
    }

    public Card playerHit() {
        if (gameEnded) return null;

        Card card = deck.dealCard();
        playerHand.addCard(card);

        if (playerHand.isBusted()) {
            gameEnded = true;
        }

        return card;
    }

    public List<Card> playerStand() {
        if (gameEnded) return new ArrayList<>();

        List<Card> dealtCards = new ArrayList<>();
        while (dealerHand.getValue() < 17) {
            Card card = deck.dealCard();
            dealerHand.addCard(card);
            dealtCards.add(card);
        }

        gameEnded = true;
        return dealtCards;
    }

    public GameResult getGameResult() {
        if (!gameEnded && !playerHand.isBusted()) {
            return null;
        }

        if (playerHand.isBlackjack() && !dealerHand.isBlackjack()) {
            return new GameResult(bjMath.ResultType.PLAYER_BLACKJACK, bet * 1.5);
        }

        if (playerHand.isBusted()) {
            return new GameResult(bjMath.ResultType.PLAYER_BUST, 0);
        }

        if (dealerHand.isBusted()) {
            return new GameResult(bjMath.ResultType.DEALER_BUST, bet);
        }

        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        if (playerValue > dealerValue) {
            return new GameResult(bjMath.ResultType.PLAYER_WIN, bet);
        } else if (playerValue < dealerValue) {
            return new GameResult(bjMath.ResultType.DEALER_WIN, 0);
        } else {
            return new GameResult(bjMath.ResultType.PUSH, bet); // Tie, return the bet
        }
    }

    public boolean canDoubleDown() {
        return playerHand.getCards().size() == 2 && !gameEnded;
    }

    public Card doubleDown() {
        if (!canDoubleDown()) return null;

        this.bet *= 2;
        Card card = playerHit();
        if (!playerHand.isBusted()) {
            playerStand();
        }
        return card;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Card getVisibleDealerCard() {
        if (dealerHand.getCards().isEmpty()) {
            return null;
        }
        return dealerHand.getCards().get(0);
    }

    public double getBet() {
        return bet;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }
}