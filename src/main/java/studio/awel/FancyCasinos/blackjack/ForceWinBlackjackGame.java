package studio.awel.FancyCasinos.blackjack;

import studio.awel.FancyCasinos.utilities.awel.bjMath;

class ForceWinBlackjackGame extends BlackjackGame {
    private final BlackjackGame originalGame;


    public ForceWinBlackjackGame(BlackjackGame originalGame) {
        this.originalGame = originalGame;
    }

    @Override
    public GameResult getGameResult() {
        if (originalGame.getPlayerHand().getValue() == 21 &&
                originalGame.getPlayerHand().getCards().size() == 2) {
            return new GameResult(bjMath.ResultType.PLAYER_BLACKJACK, originalGame.getBet() * 1.5);
        } else {
            return new GameResult(bjMath.ResultType.PLAYER_WIN, originalGame.getBet());
        }
    }

    @Override
    public Hand getPlayerHand() {
        return originalGame.getPlayerHand();
    }

    @Override
    public Hand getDealerHand() {
        return originalGame.getDealerHand();
    }

    @Override
    public Card getVisibleDealerCard() {
        return originalGame.getVisibleDealerCard();
    }

    @Override
    public double getBet() {
        return originalGame.getBet();
    }

    @Override
    public boolean isGameEnded() {
        return true;
    }
}