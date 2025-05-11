package studio.awel.FancyCasinos.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.utilities.Gambling;

public class KeepUI implements Listener {

    private final FancyCasinos plugin;

    public KeepUI(FancyCasinos plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKeepUI(InventoryCloseEvent e) {
        if (Gambling.isPlayerInGame((Player) e.getPlayer())) {
            Inventory closedInventory = e.getInventory();
            Player player = (Player) e.getPlayer();
            try {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    String gameId = Gambling.getPlayerGame(player);
                    String game = "";
                    if (gameId != null) {
                        game = gameId.toLowerCase();
                    }
                    if (player.getOpenInventory().getTitle() == null || !player.getOpenInventory().getTitle().toLowerCase().contains(game)) {
                        player.openInventory(closedInventory);
                    }
                }, 1L);
            } catch (Exception ex) {
                plugin.getLogger().warning("An error occurred while trying to keep the UI open: " + ex.getMessage());
            }
        }
    }

}
