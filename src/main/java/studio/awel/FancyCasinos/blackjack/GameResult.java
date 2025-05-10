package studio.awel.FancyCasinos.blackjack;

import studio.awel.FancyCasinos.utilities.awel.bjMath;

public class GameResult {
    private final bjMath.ResultType result;
    private final double winAmount;

    public GameResult(bjMath.ResultType result, double winAmount) {
        this.result = result;
        this.winAmount = winAmount;
    }

    public bjMath.ResultType getResult() {
        return result;
    }

    public double getWinAmount() {
        return winAmount;
    }
}
