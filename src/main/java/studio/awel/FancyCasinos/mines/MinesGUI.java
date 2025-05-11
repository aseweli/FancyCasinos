package studio.awel.FancyCasinos.mines;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.utilities.ColorFormater;
import studio.awel.FancyCasinos.utilities.Gambling;
import studio.awel.FancyCasinos.utilities.awel.PlaySounds;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static studio.awel.FancyCasinos.FancyCasinos.spiGUI;

public class MinesGUI {
    private final ConfigManager manager;
    private SGMenu firstMenu;
    private SGMenu secondMenu;
    private boolean active;

    // Common slot layouts
    private static final int[] CHAIN_SLOTS = {3, 12, 21, 30, 39, 48};
    private static final int[] BLACK_PANE_SLOTS = {0, 1, 2, 9, 11, 18, 20, 27, 29, 36, 38, 45, 46, 47};
    private static final int[] GLASS_SLOTS = {49, 50, 51, 52, 53};
    private static final int[] GAME_SLOTS = {4, 5, 6, 7, 8, 13, 14, 15, 16, 17, 22, 23, 24, 25, 26, 31, 32, 33, 34, 35, 40, 41, 42, 43, 44};

    public MinesGUI(ConfigManager config) {
        this.manager = config;
    }

    public void openGUI(Player player, double amount) {
        String title = manager.getConfig().minesMenuSelect();
        firstMenu = spiGUI.create(ColorFormater.c(title + ColorFormater.addIdentifier("m")), 6);

        setSlot(IntStream.range(0, 54).toArray(), firstMenu, Material.BARRIER, " ", "", event -> {});
        setupCommonUI(firstMenu, false);
        setupDifficultyOptions(player, amount);

        player.openInventory(firstMenu.getInventory());
    }

    private void setupDifficultyOptions(Player player, double amount) {
        Material[] materials = {
                manager.getConfig().minesFourItem(),
                manager.getConfig().minesThreeItem(),
                manager.getConfig().minesTwoItem(),
                manager.getConfig().minesOneItem()
        };

        String[] names = {
                manager.getConfig().minesFourName(),
                manager.getConfig().minesThreeName(),
                manager.getConfig().minesTwoName(),
                manager.getConfig().minesOneName()
        };

        int[] bombs = {4, 3, 2, 1};
        int[] slots = {10, 19, 28, 37};
        double[] multipliers = {
                manager.getConfig().minesFourMultiplier(),
                manager.getConfig().minesThreeMultiplier(),
                manager.getConfig().minesTwoMultiplier(),
                manager.getConfig().minesOneMultiplier()
        };

        for (int i = 0; i < slots.length; i++) {
            final int bombCount = bombs[i];
            final int finalI = i;

            setSlot(new int[]{slots[i]}, firstMenu, materials[i], names[i], "", event -> {
                player.closeInventory();
                playMines(player, amount, bombCount, multipliers[finalI]);
            });
        }
    }

    public void playMines(Player player, double amount, int mines, double multi) {
        active = true;
        String title = manager.getConfig().minesMenuName();
        secondMenu = spiGUI.create(ColorFormater.c(title + " (1.00x)" + ColorFormater.addIdentifier("m")), 6);
        Gambling.indentPlayerGame(player, "[m]");

        // Set up common UI elements
        setupCommonUI(secondMenu, true);

        // Initialize game state
        double multiplier = multi - 1;
        double prize = 0;
        int safeTilesRemaining = GAME_SLOTS.length - mines;

        // Set up game information displays
        updateTracker(player, prize);
        updateInformation(player);
        updateCounter(player, mines, safeTilesRemaining);

        // Create bomb locations
        Set<Integer> bombSet = generateBombPositions(mines);

        // Set up game tiles
        setupGameTiles(player, amount, mines, multiplier, bombSet);

        player.openInventory(secondMenu.getInventory());
    }

    private void setupCommonUI(SGMenu menu, boolean isGameMenu) {
        setSlot(CHAIN_SLOTS, menu, Material.CHAIN, " ", "", event -> {});
        setSlot(BLACK_PANE_SLOTS, menu, Material.BLACK_STAINED_GLASS_PANE, " ", "", event -> {});
        Material glassMaterial = isGameMenu ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        setSlot(GLASS_SLOTS, menu, glassMaterial, " ", "", event -> {});
    }

    private Set<Integer> generateBombPositions(int mineCount) {
        Set<Integer> bombPositions = new HashSet<>(mineCount);
        Random random = new Random();

        while (bombPositions.size() < mineCount) {
            int index = random.nextInt(GAME_SLOTS.length);
            int slot = GAME_SLOTS[index];
            bombPositions.add(slot);
        }

        return bombPositions;
    }

    private void setupGameTiles(Player player, double amount, int mines, double multiplier, Set<Integer> bombSet) {
        final GameState gameState = new GameState(amount, multiplier);

        // Set up safe tiles
        for (int slot : GAME_SLOTS) {
            if (!bombSet.contains(slot)) {
                setSafeTile(player, slot, gameState);
            } else {
                setBombTile(player, slot, bombSet, gameState);
            }
        }
    }

    private void setSafeTile(Player player, int slot, GameState gameState) {
        SGButton button = createTileButton(Material.CREEPER_HEAD, "&7&oClick to mine!", "");
        button.withListener(event -> {
            if (!active) return;
            gameState.minesCleared++;
            gameState.currentMultiplier = ((gameState.multiplier * gameState.minesCleared) + 1);
            double reward = gameState.amount * gameState.multiplier;
            gameState.totalPrize += reward;
            ItemStack newItem = createNamedItem(Material.GOLD_NUGGET,
                    "&6+$" + formatNumberShort(reward, 1));
            secondMenu.setButton(slot, new SGButton(newItem));
            secondMenu.setName(ColorFormater.c(
                    String.format("%s (%.2fx)", manager.getConfig().minesMenuName(), gameState.currentMultiplier)
            ) + ColorFormater.addIdentifier("m"));
            updateTracker(player, gameState.totalPrize);
            updateCounter(player, gameState.minesCleared, GAME_SLOTS.length - gameState.minesCleared - gameState.minesCleared);
            PlaySounds.sound(player, "click");
            secondMenu.refreshInventory(player);
        });

        secondMenu.setButton(slot, button);
    }

    private void setBombTile(Player player, int slot, Set<Integer> bombSet, GameState gameState) {
        SGButton button = createTileButton(Material.CREEPER_HEAD, "&7&oClick to mine!", "");
        button.withListener(event -> {
            if (!active) return;
            active = false;

            ItemStack bombItem = createNamedItem(Material.TNT, "&c&oExplosion!");
            secondMenu.getButton(slot).setIcon(bombItem);

            revealAllTiles(bombSet, slot);

            setSlot(GLASS_SLOTS, secondMenu, Material.RED_STAINED_GLASS_PANE, " ", "", e -> {});

            updateTracker(player, gameState.totalPrize);
            secondMenu.refreshInventory(player);
            PlaySounds.sound(player, "bomb");
            Gambling.endPlayerGame(player);
        });

        secondMenu.setButton(slot, button);
    }

    private void revealAllTiles(Set<Integer> bombSet, int currentSlot) {
        for (int slot : GAME_SLOTS) {
            if (slot != currentSlot) {
                if (bombSet.contains(slot)) {
                    secondMenu.getButton(slot).setIcon(
                            createNamedItem(Material.TNT, "&c&oBomb!"));
                } else {
                    secondMenu.getButton(slot).setIcon(
                            createNamedItem(Material.COBWEB, "&cYou Lost!"));
                }
            }
        }
    }

    public void updateTracker(Player player, Double prize) {
        String name = manager.getConfig().TrackerName()
                .replace("{amount}", formatNumberShort(prize, 1));
        String lore = manager.getConfig().TrackerLore()
                .replace("{amount}", formatNumberShort(prize, 1));

        SGButton button = createButton(manager.getConfig().TrackerItem(), name, lore, event -> {
            if (active) {
                Gambling.endPlayerGame(player);
                player.closeInventory();
                player.sendMessage("Claimed " + formatNumberShort(prize, 1) + "!");
                active = false;
            }
        });

        secondMenu.setButton(37, button);
    }

    public void updateInformation(Player player) {
        String name = manager.getConfig().InformationName();
        String lore = manager.getConfig().InformationLore();

        SGButton button = createButton(manager.getConfig().InformationItem(), name, lore, event -> {});
        secondMenu.setButton(10, button);
    }

    public void updateCounter(Player player, int bombs, int left) {
        String bombName = manager.getConfig().BombCounterName()
                .replace("{amount}", String.valueOf(bombs));
        String bombLore = manager.getConfig().BombCounterLore()
                .replace("{amount}", String.valueOf(bombs));

        SGButton bombButton = createButton(manager.getConfig().BombCounterItem(), bombName, bombLore, event -> {});
        secondMenu.setButton(19, bombButton);

        String safeName = manager.getConfig().SafeCounterName()
                .replace("{amount}", String.valueOf(left));
        String safeLore = manager.getConfig().SafeCounterLore()
                .replace("{amount}", String.valueOf(left));

        SGButton safeButton = createButton(manager.getConfig().SafeCounterItem(), safeName, safeLore, event -> {});
        secondMenu.setButton(28, safeButton);
    }

    public static String formatNumberShort(double amount, int decimals) {
        String[] suffix = {"", "k", "m", "b", "t"};
        int index = 0;
        double value = amount;

        while (value >= 1000 && index < suffix.length - 1) {
            value /= 1000;
            index++;
        }

        String pattern = decimals <= 0 ? "#,##0" : "#,##0." + "0".repeat(decimals);
        DecimalFormat df = new DecimalFormat(pattern);

        if (decimals > 0 && value == (long) value) {
            df = new DecimalFormat("#,##0");
        }

        return df.format(value) + suffix[index];
    }

    public static String formatNumberShort(double amount) {
        return formatNumberShort(amount, 1);
    }

    private SGButton createButton(Material material, String name, String lore, Consumer<InventoryClickEvent> listener) {
        ItemBuilder builder = new ItemBuilder(material)
                .name(ColorFormater.c(name))
                .lore(ColorFormater.c(lore).split("</nl>"))
                .amount(1)
                .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        SGButton button = new SGButton(builder.build());
        button.withListener(event -> listener.accept(event));
        return button;
    }

    private SGButton createTileButton(Material material, String name, String lore) {
        return createButton(material, name, lore, event -> {});
    }

    private ItemStack createNamedItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ColorFormater.c(name));
        item.setItemMeta(meta);
        return item;
    }

    public void setSlot(int[] slots, SGMenu menu, Material material, String name, String lore, Consumer<InventoryClickEvent> listener) {
        SGButton button = createButton(material, name, lore, listener);
        for (int slot : slots) {
            menu.setButton(slot, button);
        }
    }

    private static class GameState {
        final double amount;
        final double multiplier;
        double totalPrize = 0;
        int minesCleared = 0;
        double currentMultiplier = 1.0;

        GameState(double amount, double multiplier) {
            this.amount = amount;
            this.multiplier = multiplier;
        }
    }
}