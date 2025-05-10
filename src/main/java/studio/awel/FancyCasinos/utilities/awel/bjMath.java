package studio.awel.xCasinos.utilities.awel;

import studio.awel.xCasinos.blackjack.Card;

import java.util.ArrayList;
import java.util.List;

import studio.awel.xCasinos.blackjack.Deck;
import studio.awel.xCasinos.blackjack.GameResult;
import studio.awel.xCasinos.blackjack.Hand;

public class bjMath {

    /**
     * This class was not written by me, it was copied
     */


    public enum ResultType {
        PLAYER_BLACKJACK,
        PLAYER_WIN,
        DEALER_WIN,
        PUSH,
        PLAYER_BUST,
        DEALER_BUST
    }

    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private double bet;
    private boolean gameEnded = false;

    public bjMath() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    public void startGame(double betAmount) {
        playerHand.getCards().clear();
        dealerHand.getCards().clear();

        this.bet = betAmount;
        this.gameEnded = false;

        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
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

            return new GameResult(ResultType.PLAYER_BLACKJACK, bet * 1.5);
        }

        if (playerHand.isBusted()) {
            return new GameResult(ResultType.PLAYER_BUST, 0);
        }

        if (dealerHand.isBusted()) {
            return new GameResult(ResultType.DEALER_BUST, bet);
        }

        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        if (playerValue > dealerValue) {
            return new GameResult(ResultType.PLAYER_WIN, bet);
        } else if (playerValue < dealerValue) {
            return new GameResult(ResultType.DEALER_WIN, 0);
        } else {
            return new GameResult(ResultType.PUSH, bet);
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

    public double getBet() {
        return bet;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public Card getVisibleDealerCard() {
        if (dealerHand.getCards().isEmpty()) {
            return null;
        }
        return dealerHand.getCards().get(0);
    }

    public String getFormattedAmount(double amount) {
        if (amount >= 1000000000) {
            return String.format("%.1fb", amount / 1000000000);
        } else if (amount >= 1000000) {
            return String.format("%.1fm", amount / 1000000);
        } else if (amount >= 1000) {
            return String.format("%.1fk", amount / 1000);
        }
        return String.format("%.2f", amount);
    }
}