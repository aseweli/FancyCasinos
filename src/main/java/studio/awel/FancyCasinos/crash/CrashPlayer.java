package studio.awel.FancyCasinos.crash;

import org.bukkit.entity.Player;

public class CrashPlayer {
    
    private final Player player;
    private final double bet;

    public CrashPlayer(Player player, double bet) {
        this.player = player;
        this.bet = bet;
    }

    public Player getPlayer() {
        return player;
    }

    public double getBet() {
        return bet;
    }
}
