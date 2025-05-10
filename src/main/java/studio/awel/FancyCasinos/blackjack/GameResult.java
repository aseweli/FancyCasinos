package studio.awel.xCasinos.blackjack;

import studio.awel.xCasinos.utilities.awel.bjMath;

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
