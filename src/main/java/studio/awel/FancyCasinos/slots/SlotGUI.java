package studio.awel.FancyCasinos.slots;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scheduler.BukkitRunnable;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.utilities.ColorFormater;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static studio.awel.FancyCasinos.FancyCasinos.spiGUI;

public class SlotGUI {
    private final Machine machine = new Machine();
    private final ConfigManager configManager;
    private final FancyCasinos plugin;
    private final Random random = new Random();

    private final int[] slotPositions = {
            // Top row
            10, 11, 12,
            // Middle row (winning line)
            19, 20, 21,
            // Bottom row
            28, 29, 30
    };

    private final Map<Material, Double> slotItems = new HashMap<>();

    public SlotGUI(ConfigManager configManager, FancyCasinos plugin) {
        this.configManager = configManager;
        this.plugin = plugin;

        slotItems.put(Material.GOLD_NUGGET, 0.5);
        slotItems.put(Material.GOLD_INGOT, 1.0);
        slotItems.put(Material.RAW_GOLD, 1.5);
        slotItems.put(Material.RAW_GOLD_BLOCK, 2.5);
        slotItems.put(Material.GOLD_BLOCK, 5.0);
    }

    public void openGUI(Player player, double betAmount) {
        SGMenu menu = spiGUI.create(
                ColorFormater.c(configManager.getConfig().slotsTitle()), 5);

        setupSlotMachine(menu);
        player.openInventory(menu.getInventory());
        startSlotAnimation(player, menu, betAmount);
    }

    private void setupSlotMachine(SGMenu menu) {
        // Use minimal background elements
        for (int i = 0; i < 45; i++) {
            // Only set borders with glass
            if (i < 9 || i > 35 || i % 9 == 0 || i % 9 == 8) {
                ItemBuilder glassItem = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                        .name(" ")
                        .flag(ItemFlag.HIDE_ATTRIBUTES);
                menu.setButton(i, new SGButton(glassItem.build()));
            }
        }

        // Yellow line for winning row
        for (int i = 18; i <= 22; i++) {
            ItemBuilder yellowGlass = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                    .name(" ")
                    .flag(ItemFlag.HIDE_ATTRIBUTES);
            menu.setButton(i, new SGButton(yellowGlass.build()));
        }

        // Empty slots
        for (int pos : slotPositions) {
            ItemBuilder defaultItem = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
                    .name(" ")
                    .flag(ItemFlag.HIDE_ATTRIBUTES);
            menu.setButton(pos, new SGButton(defaultItem.build()));
        }

        // Instructions
        ItemBuilder instructionsItem = new ItemBuilder(Material.OAK_SIGN)
                .name(ColorFormater.c(configManager.getConfig().slotsInstructionsTitle()))
                .lore(ColorFormater.c(configManager.getConfig().slotsInstructionsLore()).split("</nl>"))
                .flag(ItemFlag.HIDE_ATTRIBUTES);
        menu.setButton(15, new SGButton(instructionsItem.build()));
    }

    private void startSlotAnimation(Player player, SGMenu menu, double betAmount) {
        int[] animationCount = {0};
        final int maxAnimations = 20;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (animationCount[0] >= maxAnimations) {
                    cancel();

                    Machine.SpinResult winnings = machine.spin(betAmount);
                    displayFinalResult(player, menu, winnings, betAmount);
                    return;
                }

                for (int pos : slotPositions) {
                    Material[] materials = slotItems.keySet().toArray(new Material[0]);
                    Material randomMaterial = materials[random.nextInt(materials.length)];
                    double multiplier = slotItems.get(randomMaterial);

                    ItemBuilder slotItem = new ItemBuilder(randomMaterial)
                            .name(ColorFormater.c("&e&l" + materialToName(randomMaterial).toUpperCase() + " &8(" + multiplier + "x)"))
                            .flag(ItemFlag.HIDE_ATTRIBUTES);

                    menu.setButton(pos, new SGButton(slotItem.build()));
                    player.getOpenInventory().getTopInventory().setItem(pos, slotItem.build());
                }

                player.updateInventory();
                animationCount[0]++;
            }
        }.runTaskTimer(plugin, 5L, 5L);
    }

    private void displayFinalResult(Player player, SGMenu menu, Machine.SpinResult winnings, double betAmount) {
        java.util.List<SlotBlock> rolledBlocks = winnings.getRolledBlocks();

        for (int i = 0; i < rolledBlocks.size(); i++) {
            if (i >= 3) break;

            SlotBlock slotBlock = rolledBlocks.get(i);
            Material material = nameToMaterial(slotBlock.getName());

            ItemBuilder slotItem = new ItemBuilder(material)
                    .name(ColorFormater.c("&f&l" + slotBlock.getName()))
                    .lore(ColorFormater.c("&eMultiplier: &f" + slotBlock.getMultiplier() + "x"))
                    .flag(ItemFlag.HIDE_ATTRIBUTES);

            int pos = 19 + i;
            menu.setButton(pos, new SGButton(slotItem.build()));
            player.getOpenInventory().getTopInventory().setItem(pos, slotItem.build());
        }

        player.updateInventory();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            showEndingScreen(player, winnings, betAmount);
        }, 30L);
    }

    private void showEndingScreen(Player player, Machine.SpinResult winnings, double betAmount) {
        boolean isWin = winnings.getFinalAmount() > betAmount;
        String title = isWin ?
                configManager.getConfig().slotsWinTitle() :
                configManager.getConfig().slotsLoseTitle();

        SGMenu resultScreen = spiGUI.create(ColorFormater.c(title), 5);

        // Set minimal styled background (not all green)
        for (int i = 0; i < 45; i++) {
            // Only set borders and keep the original style
            if (i < 9 || i > 35 || i % 9 == 0 || i % 9 == 8) {
                Material borderMaterial = isWin ?
                        Material.LIME_STAINED_GLASS_PANE :
                        Material.RED_STAINED_GLASS_PANE;

                ItemBuilder borderItem = new ItemBuilder(borderMaterial)
                        .name(" ")
                        .flag(ItemFlag.HIDE_ATTRIBUTES);
                resultScreen.setButton(i, new SGButton(borderItem.build()));
            }
        }

        // Display result
        double amountDiff = isWin ? winnings.getFinalAmount() : (betAmount - winnings.getFinalAmount());
        Material resultMaterial = isWin ? Material.EMERALD : Material.BARRIER;

        String resultText = isWin ?
                configManager.getConfig().slotsWinAmount().replace("{amount}", String.format("%.2f", amountDiff)) :
                configManager.getConfig().slotsLoseAmount().replace("{amount}", String.format("%.2f", amountDiff));

        String[] loreText = isWin ?
                configManager.getConfig().slotsWinLore().split("</nl>") :
                configManager.getConfig().slotsLoseLore().split("</nl>");

        for (int i = 0; i < loreText.length; i++) {
            loreText[i] = loreText[i]
                    .replace("{bet}", String.format("%.2f", betAmount))
                    .replace("{final}", String.format("%.2f", winnings.getFinalAmount()));
        }

        ItemBuilder resultItem = new ItemBuilder(resultMaterial)
                .name(ColorFormater.c(resultText))
                .lore(ColorFormater.c(String.join("</nl>", loreText)).split("</nl>"))
                .flag(ItemFlag.HIDE_ATTRIBUTES);

        resultScreen.setButton(22, new SGButton(resultItem.build()));

        // Show rolled blocks
        for (int i = 0; i < winnings.getRolledBlocks().size(); i++) {
            if (i >= 3) break;

            SlotBlock slotBlock = winnings.getRolledBlocks().get(i);
            Material material = nameToMaterial(slotBlock.getName());

            ItemBuilder slotItem = new ItemBuilder(material)
                    .name(ColorFormater.c("&e&l" + slotBlock.getName()))
                    .lore("", ColorFormater.c("&fMultiplier: &e" + slotBlock.getMultiplier() + "x"))
                    .flag(ItemFlag.HIDE_ATTRIBUTES);

            resultScreen.setButton(11 + i, new SGButton(slotItem.build()));
        }

        // Total multiplier
        double multiplier = 1.0;
        for (SlotBlock block : winnings.getRolledBlocks()) {
            multiplier *= block.getMultiplier();
        }

        ItemBuilder multiplierItem = new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .name(ColorFormater.c(configManager.getConfig().slotsTotalMultiplierTitle()))
                .lore("", ColorFormater.c(configManager.getConfig().slotsTotalMultiplierValue()
                        .replace("{multiplier}", String.format("%.2f", multiplier))))
                .flag(ItemFlag.HIDE_ATTRIBUTES);

        resultScreen.setButton(31, new SGButton(multiplierItem.build()));

        // Play again button
        ItemBuilder playAgainItem = new ItemBuilder(Material.EMERALD)
                .name(ColorFormater.c(configManager.getConfig().slotsPlayAgainTitle()))
                .flag(ItemFlag.HIDE_ATTRIBUTES);

        SGButton playAgainButton = new SGButton(playAgainItem.build()).withListener(event -> {
            Player user = (Player) event.getWhoClicked();
            user.closeInventory();
            // Return to main menu
            plugin.getCommand("casino").execute(user, "casino", new String[0]);
        });

        resultScreen.setButton(38, playAgainButton);

        // Exit button
        ItemBuilder exitItem = new ItemBuilder(Material.BARRIER)
                .name(ColorFormater.c(configManager.getConfig().slotsExitTitle()))
                .flag(ItemFlag.HIDE_ATTRIBUTES);

        SGButton exitButton = new SGButton(exitItem.build()).withListener(event -> {
            ((Player) event.getWhoClicked()).closeInventory();
        });

        resultScreen.setButton(42, exitButton);

        player.openInventory(resultScreen.getInventory());
    }

    private Material nameToMaterial(String blockName) {
        if (blockName.toLowerCase().contains("gold nugget")) {
            return Material.GOLD_NUGGET;
        } else if (blockName.toLowerCase().contains("gold ingot")) {
            return Material.GOLD_INGOT;
        } else if (blockName.toLowerCase().contains("raw gold") && blockName.toLowerCase().contains("block")) {
            return Material.RAW_GOLD_BLOCK;
        } else if (blockName.toLowerCase().contains("raw gold")) {
            return Material.RAW_GOLD;
        } else if (blockName.toLowerCase().contains("gold block")) {
            return Material.GOLD_BLOCK;
        } else {
            return Material.BARRIER;
        }
    }

    private String materialToName(Material material) {
        switch (material) {
            case GOLD_NUGGET: return "Gold Nugget";
            case GOLD_INGOT: return "Gold Ingot";
            case RAW_GOLD: return "Raw Gold";
            case RAW_GOLD_BLOCK: return "Raw Gold Block";
            case GOLD_BLOCK: return "Gold Block";
            default: return "Unknown";
        }
    }
}