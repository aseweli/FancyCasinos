package studio.awel.FancyCasinos.crash;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class CrashIntermissionGUI {

    private static final int[] YELLOW_PANES = {1, 7, 17, 27, 35, 37, 39, 41};
    private static final int[] WHITE_PANES = {0, 2, 6, 8, 18, 26, 36, 38, 41, 44};
    private static final int[] LEADERBOARD_BUNDLE_SLOTS = {15, 24, 33};
    private static final int[] LEADERBOARD_SKULL_SLOTS = {16, 25, 34};

    public static Inventory create(Player viewer, CrashGame game, PlayerData data, int leaderboardPage, int secondsUntilStart) {
        Inventory inv = Bukkit.createInventory(null, 45, "Global Crash (" +
                formatTime(secondsUntilStart) + " until start)");

        // fill panes
        for (int slot : YELLOW_PANES) {
            inv.setItem(slot, pane(Material.YELLOW_STAINED_GLASS_PANE, "§f"));
        }
        for (int slot : WHITE_PANES) {
            inv.setItem(slot, pane(Material.WHITE_STAINED_GLASS_PANE, "§f"));
        }

        // game status - 13
        inv.setItem(3, gameStatusSign(game, secondsUntilStart));

        // htp - 13
        inv.setItem(4, howToPlayBook());

        // bet info - 5 
        inv.setItem(5, yourBetMap(data));

        // leadernpards - 15/16 24/25, 33/34
        List<CrashLeaderboardEntry> leaderboard = game.getLeaderboard();
        int start = leaderboardPage * 3;
        for (int i = 0; i < 3; i++) {
            int pos = start + i;
            if (pos < leaderboard.size()) {
                CrashLeaderboardEntry entry = leaderboard.get(pos);
                inv.setItem(LEADERBOARD_BUNDLE_SLOTS[i], leaderboardBundle(i, entry, pos));
                inv.setItem(LEADERBOARD_SKULL_SLOTS[i], leaderboardSkull(entry));
            } else {
                inv.setItem(LEADERBOARD_BUNDLE_SLOTS[i], emptyLeaderboardBundle(i, pos));
                inv.setItem(LEADERBOARD_SKULL_SLOTS[i], emptyLeaderboardSkull());
            }
        }

        // leaderboard page buttons - 42, 43
        if (leaderboard.size() > 3) {
            if (leaderboardPage > 0) {
                inv.setItem(42, pageButton("Previous", leaderboardPage));
            }
            if ((leaderboardPage + 1) * 3 < leaderboard.size()) {
                inv.setItem(43, pageButton("Next", leaderboardPage + 2));
            }
        }

        // intermission status - 22 or 20/22
        if (game.isPlayerInGame(viewer.getUniqueId())) {
            inv.setItem(22, clock("Intermission", secondsUntilStart));
        } else {
            inv.setItem(22, joinWool(game));
            inv.setItem(20, clock("Intermission", secondsUntilStart));
        }

        // leave game Slot 40
        if (game.isPlayerInGame(viewer.getUniqueId())) {
            inv.setItem(40, leaveBarrier());
        } else {
            inv.setItem(40, pane(Material.WHITE_STAINED_GLASS_PANE, "§f"));
        }

        return inv;
    }

    // --- helper methodos

    private static ItemStack pane(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack gameStatusSign(CrashGame game, int secondsUntilStart) {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eGame Status");
        List<String> lore = new ArrayList<>();
        lore.add("Game ID: §f" + game.getId());
        lore.add("Status: §f" + game.getStatus());
        lore.add("Odds: §f" + String.format("%.2f", game.getOdds()));
        lore.add("Players: §f" + game.getPlayerCount());
        lore.add("Total Pot: $" + formatMoney(game.getTotalPot()));
        lore.add("Time until start: §f" + formatTime(secondsUntilStart));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack howToPlayBook() {
        ItemStack item = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bHow to Play");
        List<String> lore = new ArrayList<>();
        lore.add("Crash is a game of risk and reward!");
        lore.add("Place your bet, then watch the multiplier rise.");
        lore.add("Cash out before the crash to win!");
        lore.add("If you don't cash out in time, you lose your bet.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack yourBetMap(PlayerData data) {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Your Bet");
        List<String> lore = new ArrayList<>();
        lore.add("Amount: $" + formatMoney(data.getBet()));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack leaderboardBundle(int i, CrashLeaderboardEntry entry, int pos) {
        Material mat;
        String color;
        switch (i) {
            case 0: mat = Material.LIGHT_BLUE_BUNDLE; color = "§b"; break;
            case 1: mat = Material.LIME_BUNDLE; color = ""; break;
            default: mat = Material.BUNDLE; color = "§f"; break;
        }
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color + "#" + (pos + 1) + " bet");
        List<String> lore = new ArrayList<>();
        lore.add("Amount: $" + formatMoney(entry.getBetAmount()));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack leaderboardSkull(CrashLeaderboardEntry entry) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        OfflinePlayer offline = Bukkit.getOfflinePlayer(entry.getPlayerUUID());
        meta.setOwningPlayer(offline);
        meta.setDisplayName("§e" + offline.getName());
        List<String> lore = new ArrayList<>();
        lore.add("Multiplier: §f1.00x (+$0.00)");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack emptyLeaderboardBundle(int i, int pos) {
        Material mat;
        String color;
        switch (i) {
            case 0: mat = Material.LIGHT_BLUE_BUNDLE; color = "§b"; break;
            case 1: mat = Material.LIME_BUNDLE; color = ""; break;
            default: mat = Material.BUNDLE; color = "§f"; break;
        }
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color + "#" + (pos + 1) + " bet");
        List<String> lore = new ArrayList<>();
        lore.add("No bet yet.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack emptyLeaderboardSkull() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName("No player");
        List<String> lore = new ArrayList<>();
        lore.add("Multiplier: §f1.00x (+$0.00)");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack pageButton(String name, int page) {
        ItemStack item = new ItemStack(Material.OAK_BUTTON);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add("Page: §f" + page);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack clock(String name, int seconds) {
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("" + name);
        List<String> lore = new ArrayList<>();
        lore.add("Time left: §f" + formatTime(seconds));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack joinWool(CrashGame game) {
        ItemStack item = new ItemStack(Material.LIME_WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Join Game");
        List<String> lore = new ArrayList<>();
        if (game.getPlayerCount() < game.getMinPlayers()) {
            lore.add("Players required until game countdown.");
        } else {
            lore.add("Click to join the game!");
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack leaveBarrier() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Leave Game");
        item.setItemMeta(meta);
        return item;
    }

    // --- Formatting helpers ---

    private static String formatMoney(double amount) {
        return String.format("%,.2f", amount);
    }

    private static String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return (min > 0 ? min + "m " : "") + sec + "s";
    }
}
