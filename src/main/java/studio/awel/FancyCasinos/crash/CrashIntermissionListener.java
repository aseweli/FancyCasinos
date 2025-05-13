package studio.awel.FancyCasinos.crash;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrashIntermissionListener implements Listener {

    // track leaderboard page per player
    private final Map<UUID, Integer> leaderboardPage = new HashMap<>();

    private final CrashMasterClass crashMaster;

    public CrashIntermissionListener(CrashMasterClass crashMaster) {
        this.crashMaster = crashMaster;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();

        // only handle glbal gui
        if (!event.getView().getTitle().startsWith("Global Crash")) return;

        event.setCancelled(true);

        int slot = event.getRawSlot();
        UUID uuid = player.getUniqueId();

        CrashGame game = crashMaster.getCurrentGame();
        int secondsUntilStart = crashMaster.getRemainingSeconds();
        int page = leaderboardPage.getOrDefault(uuid, 0);

        // find player's bet (needs to be imported)
        double bet = 0.0;
        for (PlayerData pd : game.getPlayerSet()) {
            if (pd.getPlayer().getUniqueId().equals(uuid)) {
                bet = pd.getBet();
                break;
            }
        }
        PlayerData data = new PlayerData(player, bet);

        // handle slot actions
        switch (slot) {
            case 22: // join game
                if (!game.isPlayerInGame(uuid)) {
                    // TODO: replace with bet input system
                    double betAmount = 100.0; // default bet
                    PlayerData pd = new PlayerData(player, betAmount);
                    if (crashMaster.addPlayerToBet(pd)) {
                        player.sendMessage("You joined the game with $" + betAmount + "!");
                        data = new PlayerData(player, betAmount);
                    } else {
                        player.sendMessage("Failed to join the game.");
                    }
                    // reset user pages to 0
                    leaderboardPage.put(uuid, 0);
                    player.openInventory(CrashIntermissionGUI.create(player, game, data, 0, secondsUntilStart));
                }
                break;
            case 40: // leave Game
                if (game.isPlayerInGame(uuid)) {
                    game.removePlayer(uuid);
                    player.sendMessage("You left the game.");
                    leaderboardPage.put(uuid, 0);
                    player.openInventory(CrashIntermissionGUI.create(player, game, new PlayerData(player, 0), 0, secondsUntilStart));
                }
                break;
            case 42: // previous leaderboard page
                if (page > 0) {
                    leaderboardPage.put(uuid, page - 1);
                    player.openInventory(CrashIntermissionGUI.create(player, game, data, page - 1, secondsUntilStart));
                }
                break;
            case 43: // next leaderboard page
                if ((page + 1) * 3 < game.getLeaderboard().size()) {
                    leaderboardPage.put(uuid, page + 1);
                    player.openInventory(CrashIntermissionGUI.create(player, game, data, page + 1, secondsUntilStart));
                }
                break;
            default:
                // no action for other slots
                break;
        }
    }
}
