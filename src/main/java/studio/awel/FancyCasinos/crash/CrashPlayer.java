package studio.awel.FancyCasinos.crash;

import org.bukkit.entity.Player;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrashPlayer)) return false;
        CrashPlayer that = (CrashPlayer) o;
        return player.getUniqueId().equals(that.player.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getUniqueId());
    }
}
