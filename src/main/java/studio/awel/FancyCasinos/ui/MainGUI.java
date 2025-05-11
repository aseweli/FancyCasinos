package studio.awel.FancyCasinos.ui;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.blackjack.BlackjackGUI;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.mines.MinesGUI;
import studio.awel.FancyCasinos.utilities.MoneyUtil;
import studio.awel.FancyCasinos.utilities.ColorFormater;
import studio.awel.FancyCasinos.utilities.CustomItem;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static studio.awel.FancyCasinos.FancyCasinos.spiGUI;

public class MainGUI {
    private static final Logger LOGGER = Logger.getLogger("FancyCasinos Menu Manager");
    private static final double MIN_BET_AMOUNT = 1.0;
    private static final int MAX_ROWS = 6;
    private static final int MIN_ROWS = 1;

    private final ConfigManager configManager;
    private final FancyCasinos fancyCasinos;
    private final Map<Character, ItemInfo> itemInfoMap = new HashMap<>();

    public MainGUI(ConfigManager configManager, FancyCasinos fancyCasinos) {
        this.configManager = configManager;
        this.fancyCasinos = fancyCasinos;
        initializeItemInfoMap();
    }


    public void openGUI(Player player) {
        String layout = configManager.getConfig().guiLayout().replace("\n", "");
        int rows = calculateRows(layout);

        if (rows < MIN_ROWS || rows > MAX_ROWS) {
            LOGGER.severe("The menu is not formatted correctly, please recreate the config.yml file.");
            player.sendMessage(ColorFormater.c("&cThere was an error opening the casino menu. Please contact an administrator."));
            return;
        }

        SGMenu menu = spiGUI.create(ColorFormater.c(configManager.getConfig().guiName()), rows);

        // Fill menu with items based on layout
        for (int i = 0; i < layout.length() && i < rows * 9; i++) {
            if (layout.charAt(i) == '-') {
                setBlankItem(menu, i);
            } else {
                setGameItem(menu, layout.charAt(i), i);
            }
        }

        player.openInventory(menu.getInventory());
    }

    private int calculateRows(String layout) {
        return Math.max(MIN_ROWS, Math.min(layout.length() / 9 + (layout.length() % 9 > 0 ? 1 : 0), MAX_ROWS));
    }

    private void setBlankItem(SGMenu menu, int slot) {
        SGButton button = createButton(
                configManager.getConfig().blankObject(),
                "",
                "",
                event -> {}
        );
        menu.setButton(slot, button);
    }

    private void setGameItem(SGMenu menu, char type, int slot) {
        ItemInfo itemInfo = getItemInfo(type);

        SGButton button = createButton(
                itemInfo.material,
                itemInfo.name,
                itemInfo.lore,
                event -> handleItemClick(event, type)
        );

        menu.setButton(slot, button);
    }


    private void handleItemClick(InventoryClickEvent event, char type) {
        Player player = (Player) event.getWhoClicked();

        switch (type) {
            case 'x':
                player.closeInventory();
                break;
            case 'b':
                if (player.hasPermission("fancycasinos.blackjack")) {
                    handleGameBet(player, "Blackjack bet", amount ->
                            new BlackjackGUI(player, amount, configManager).openGUI());
                } else {
                    player.sendMessage(ColorFormater.c(configManager.getConfig().permissionDeniedMessage()));
                }
                break;
            case 'm':
                if (player.hasPermission("fancycasinos.mines")) {
                    handleGameBet(player, "Mines bet", amount ->
                            new MinesGUI(configManager).openGUI(player, amount));
                } else {
                    player.sendMessage(ColorFormater.c(configManager.getConfig().permissionDeniedMessage()));
                }
                break;
        }
    }

    private void handleGameBet(Player player, String prompt, Consumer<Double> gameStarter) {
        player.closeInventory();
        MoneyUtil.typeInChat(player, prompt, 15L,
                input -> {
                    try {
                        double amount = Double.parseDouble(input);
                        if (amount < MIN_BET_AMOUNT) {
                            String[] message = ColorFormater.c(configManager.getConfig().invalidValueMessage()).split("</nl>");
                            player.sendTitle(message[0], message[1], 10, 70, 20);
                            return;
                        }
                        gameStarter.accept(amount);
                    } catch (NumberFormatException e) {
                        String[] message = ColorFormater.c(configManager.getConfig().invalidValueMessage()).split("</nl>");
                        player.sendTitle(message[0], message[1], 10, 70, 20);
                    }
                },
                () -> {
                    String[] message = ColorFormater.c(configManager.getConfig().betTimeoutMessage()).split("</nl>");
                    player.sendTitle(message[0], message[1], 10, 70, 20);
                }
        );
    }


    private SGButton createButton(Material material, String name, String lore, Consumer<InventoryClickEvent> clickHandler) {
        if (material == null) {
            material = Material.BARRIER;
            name = "§cError";
            lore = "§7Invalid material for this item";
            LOGGER.warning("Null material used for button");
        }

        ItemBuilder itemBuilder;

        if (material != Material.AIR) {
            String[] loreLines = ColorFormater.c(lore).split("</nl>");
            itemBuilder = new ItemBuilder(material)
                    .name(ColorFormater.c(name))
                    .lore(loreLines)
                    .amount(1)
                    .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        } else {
            itemBuilder = new ItemBuilder(Material.AIR).amount(1);
        }

        SGButton button = new SGButton(itemBuilder.build());
        button.withListener(clickHandler::accept);
        return button;
    }


    private void initializeItemInfoMap() {
        itemInfoMap.put('s', new ItemInfo(
                configManager.getConfig().slotsItem(),
                configManager.getConfig().slotsName(),
                configManager.getConfig().slotsDescription()
        ));

        itemInfoMap.put('m', new ItemInfo(
                configManager.getConfig().minesItem(),
                configManager.getConfig().minesName(),
                configManager.getConfig().minesDescription()
        ));

        itemInfoMap.put('c', new ItemInfo(
                configManager.getConfig().crashItem(),
                configManager.getConfig().crashName(),
                configManager.getConfig().crashDescription()
        ));

        itemInfoMap.put('b', new ItemInfo(
                configManager.getConfig().blackjackItem(),
                configManager.getConfig().blackjackName(),
                configManager.getConfig().blackjackDescription()
        ));

        itemInfoMap.put('x', new ItemInfo(
                configManager.getConfig().exitItem(),
                configManager.getConfig().exitName(),
                configManager.getConfig().exitDescription()
        ));

        try {
            Map<Character, CustomItem> customItems = parseCustomItems();
            for (Map.Entry<Character, CustomItem> entry : customItems.entrySet()) {
                char key = entry.getKey();
                CustomItem item = entry.getValue();
                itemInfoMap.put(key, new ItemInfo(item.material, item.name, item.lore));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading custom items", e);
        }
    }


    private ItemInfo getItemInfo(char type) {
        ItemInfo info = itemInfoMap.get(type);

        if (info == null) {
            LOGGER.warning("No definition found for item type: '" + type + "'");
            return new ItemInfo(
                    Material.BARRIER,
                    "§cInvalid Item",
                    "§7This item is not configured properly"
            );
        }

        return info;
    }


    private Map<Character, CustomItem> parseCustomItems() {
        Map<Character, CustomItem> items = new HashMap<>();

        try {
            for (String definition : configManager.getConfig().customItemDefinitions()) {
                if (definition == null || definition.isEmpty()) {
                    continue;
                }

                String[] parts = definition.split(":", 4);
                if (parts.length >= 4 && !parts[0].isEmpty()) {
                    char key = parts[0].charAt(0);
                    try {
                        Material material = Material.valueOf(parts[1].toUpperCase());
                        String name = parts[2];
                        String lore = parts[3];
                        items.put(key, new CustomItem(material, name, lore));
                    } catch (IllegalArgumentException e) {
                        LOGGER.warning("Invalid material for custom item '" + parts[0] + "': " + parts[1]);
                    }
                } else {
                    LOGGER.warning("Invalid custom item format: " + definition);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing custom items", e);
            return Collections.emptyMap();
        }

        return items;
    }

    /**
     * Helper class to store item information
     */
    private static class ItemInfo {
        final Material material;
        final String name;
        final String lore;

        ItemInfo(Material material, String name, String lore) {
            this.material = material;
            this.name = name;
            this.lore = lore;
        }
    }
}