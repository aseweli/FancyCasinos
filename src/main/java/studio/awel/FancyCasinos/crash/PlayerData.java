package studio.awel.FancyCasinos.crash;

import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerData {

    private final Player player;
    private final double bet;

    public PlayerData(Player player, double bet) {
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
        if (!(o instanceof PlayerData)) return false;
        PlayerData that = (PlayerData) o;
        return player.getUniqueId().equals(that.player.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getUniqueId());
    }
}
