package studio.awel.FancyCasinos.crash;

import java.util.UUID;

public class CrashLeaderboardEntry {
    private final UUID playerUUID;
    private final double betAmount;

    public CrashLeaderboardEntry(UUID playerUUID, double betAmount) {
        this.playerUUID = playerUUID;
        this.betAmount = betAmount;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public double getBetAmount() {
        return betAmount;
    }
}
