package studio.awel.FancyCasinos.mines;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.utilities.ColorFormater;
import studio.awel.FancyCasinos.utilities.Gambling;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static studio.awel.FancyCasinos.FancyCasinos.spiGUI;

public class MinesGUI {

    ConfigManager manager;
    SGMenu firstMenu;
    SGMenu secondMenu;
    boolean active;

    public MinesGUI(ConfigManager config) {
        manager = config;
    }

    public void openGUI(Player player, double amount) {
        String title = manager.getConfig().minesMenuSelect();
        firstMenu = spiGUI.create(title, 6);

        int[] allSlots = new int[54];
        for (int i = 0; i < 54; i++) allSlots[i] = i;
        setSlot(allSlots, firstMenu, Material.BARRIER, " ", "", event -> {});

        int[] chains = {3, 12, 21, 30, 39, 48};
        setSlot(chains, firstMenu, Material.CHAIN, " ", "", event -> {});

        int[] blackPane = {0,1,2,9,11,18,20,27,29,36,38,45,46,47};
        setSlot(blackPane, firstMenu, Material.BLACK_STAINED_GLASS_PANE, " ", "", event -> {});

        int[] glass = {49, 50, 51, 52, 53};
        setSlot(glass, firstMenu, Material.RED_STAINED_GLASS_PANE, " ", "", event -> {});

        Material[] materials = {manager.getConfig().minesFourItem(), manager.getConfig().minesThreeItem(), manager.getConfig().minesTwoItem(), manager.getConfig().minesOneItem()};
        String[] names = {manager.getConfig().minesFourName(), manager.getConfig().minesThreeName(), manager.getConfig().minesTwoName(), manager.getConfig().minesOneName()};
        int[] bombs = {4, 3, 2, 1};
        int[] dye = {10, 19, 28, 37};
        double[] multipliers = {manager.getConfig().minesFourMultiplier(), manager.getConfig().minesThreeMultiplier(), manager.getConfig().minesTwoMultiplier(), manager.getConfig().minesOneMultiplier()};
        for (int i = 0; i < dye.length; i++) {
            final int bombCount = bombs[i];
            int[] slot = {dye[i]};
            int finalI = i;
            setSlot(slot, firstMenu, materials[i], names[i], "", event -> {
                player.closeInventory();
                playMines(player, amount, bombCount, multipliers[finalI]);
            });
        }

        player.openInventory(firstMenu.getInventory());
    }

    public void playMines(Player player, double amount, int mines, double multi) {
        Bukkit.broadcastMessage("Starting mine for player" + player + " with $" + amount + " on the line" + " playing with " + mines + " bombs!");
        active = true;
        String title = manager.getConfig().minesMenuName();
        secondMenu = spiGUI.create(title + " (1.00x)", 6);
        Gambling.indentPlayerGame(player, "Mines");

        int[] chains = {3, 12, 21, 30, 39, 48};
        setSlot(chains, secondMenu, Material.CHAIN, " ", "", event -> {});

        int[] blackPane = {0,1,2,9,11,18,20,27,29,36,38,45,46,47};
        setSlot(blackPane, secondMenu, Material.BLACK_STAINED_GLASS_PANE, " ", "", event -> {});

        int[] glass = {49, 50, 51, 52, 53};
        setSlot(glass, secondMenu, Material.LIME_STAINED_GLASS_PANE, " ", "", event -> {});

        AtomicReference<Double> mx = new AtomicReference<>((double) 0);

        AtomicReference<Double> prize = new AtomicReference<>((double) 0);
        AtomicInteger safe = new AtomicInteger(25 - mines);
        updateTracker(player, prize.get());
        updateInformation(player);
        updateCounter(player, mines, safe.get());


        int[] slots = {4, 5, 6, 7, 8, 13, 14, 15, 16, 17, 22, 23, 24, 25, 26, 31, 32, 33, 34, 35, 40, 41, 42, 43, 44};
        int[] bombs = returnBombs(slots, mines);
        Set<Integer> bombSet = Arrays.stream(bombs).boxed().collect(Collectors.toSet());
        int[] safeSlots = Arrays.stream(slots).filter(slot -> !bombSet.contains(slot)).toArray();

        setSlot(safeSlots, secondMenu, Material.CREEPER_HEAD, "&7&oClick to mine!", "", event -> {
            if (active) {
                int x = event.getSlot();
                mx.getAndSet(mx.get() + 1);
                ItemStack newItem = new ItemStack(Material.GOLD_NUGGET);
                ItemMeta newItemMeta = newItem.getItemMeta();
                newItemMeta.setDisplayName(ColorFormater.c("&6+$" + formatNumberShort(amount * (multi - 1), 1)));
                secondMenu.setName(String.format("%s (%.2fx)", title, (((multi - 1) * mx.get()) + 1)));
                prize.updateAndGet(v -> (v + amount * (multi - 1)));
                safe.getAndDecrement();
                newItem.setItemMeta(newItemMeta);

                SGButton newButton = new SGButton(newItem);
                newButton.withListener(e -> {});
                secondMenu.setButton(x, newButton);

                updateTracker(player, prize.get());
                updateCounter(player, mines, safe.get());
                secondMenu.refreshInventory(event.getWhoClicked());
            }
        });

        setSlot(bombs, secondMenu, Material.CREEPER_HEAD, "&7&oClick to mine!", "", event -> {
            if (active) {
                int x = event.getSlot();
                ItemStack newItem = new ItemStack(Material.TNT);
                ItemMeta newItemMeta = newItem.getItemMeta();
                newItemMeta.setDisplayName(ColorFormater.c("&c&oExplosion!"));
                newItem.setItemMeta(newItemMeta);
                updateTracker(player, prize.get());
                secondMenu.getButton(x).setIcon(newItem);

                for (int slot : slots) {
                    if (slot != x && !bombSet.contains(slot)) {
                        ItemStack lostItem = new ItemStack(Material.COBWEB);
                        ItemMeta lostItemMeta = lostItem.getItemMeta();
                        lostItemMeta.setDisplayName(ColorFormater.c("&cYou Lost!"));
                        lostItem.setItemMeta(lostItemMeta);
                        secondMenu.getButton(slot).setIcon(lostItem);
                    } else if (slot != x && bombSet.contains(slot)) {
                        ItemStack bombItem = new ItemStack(Material.TNT);
                        ItemMeta bombItemMeta = bombItem.getItemMeta();
                        bombItemMeta.setDisplayName(ColorFormater.c("&c&oBomb!"));
                        bombItem.setItemMeta(bombItemMeta);
                        secondMenu.getButton(slot).setIcon(bombItem);
                    }
                }

                secondMenu.refreshInventory(event.getWhoClicked());
                active = false;
                Gambling.endPlayerGame(player);
            }
        });

        player.openInventory(secondMenu.getInventory());
    }

    public void updateTracker(Player player, Double prize) {
        int[] slots = {37};
        String name = manager.getConfig().TrackerName().replace("{amount}", formatNumberShort(prize, 1));
        String lore = manager.getConfig().TrackerLore().replace("{amount}", formatNumberShort(prize, 1));
        setSlot(slots, secondMenu, manager.getConfig().TrackerItem(), name, lore, event -> {
            Gambling.endPlayerGame(player);
            player.closeInventory();
            player.sendMessage("Would've claimed " + formatNumberShort(prize, 1) + "!");
        });
    }

    public void updateInformation(Player player){
        int[] slots = {10};
        String name = manager.getConfig().InformationName();
        setSlot(slots, secondMenu, manager.getConfig().InformationItem(), name, manager.getConfig().InformationLore(), event -> {});
    }

    public void updateCounter(Player player, int bombs, int left){
        int[] slots = {19};
        String name = manager.getConfig().BombCounterName().replace("{amount}", bombs + "");
        String lore = manager.getConfig().BombCounterLore().replace("{amount}", bombs + "");
        setSlot(slots, secondMenu, manager.getConfig().BombCounterItem(), name, lore, event -> {});

        int[] slot = {28};
        name = manager.getConfig().SafeCounterName().replace("{amount}", left + "");
        lore = manager.getConfig().SafeCounterLore().replace("{amount}", left + "");
        setSlot(slot, secondMenu, manager.getConfig().SafeCounterItem(), name, lore, event -> {});

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

    public void setSlot(int[] slots, SGMenu menu, Material material, String name, String lore, Consumer<InventoryClickEvent> listener) {
        lore = ColorFormater.c(lore);
        String[] result = lore.split("</nl>");
        for (int slot : slots) {
            ItemBuilder item = new ItemBuilder(material)
                    .name(ColorFormater.c(name))
                    .lore(result)
                    .amount(1)
                    .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            SGButton button = new SGButton(item.build());
            menu.setButton(slot, button);
            button.withListener(event -> listener.accept(event));
        }
    }

    public int[] returnBombs(int[] values, int bombs){
        int[] results = new int[bombs];
        HashSet<Integer> selectedIndexes = new HashSet<>();
        Random r = new Random();

        int c = 0;
        while (c < bombs){
            int index = r.nextInt(values.length);
            if (!selectedIndexes.contains(index)){
                selectedIndexes.add(index);
                results[c] = values[index];
                c++;
            }
        }
        return results;
    }
}