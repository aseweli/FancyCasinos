package studio.awel.xCasinos.ui;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import studio.awel.xCasinos.config.ConfigManager;
import studio.awel.xCasinos.utilities.ColorFormater;
import studio.awel.xCasinos.utilities.CustomItem;
import studio.awel.xCasinos.utilities.cigan.ChatUtil;
import studio.awel.xCasinos.utilities.cigan.ChatUtilKt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static studio.awel.xCasinos.XCasinos.spiGUI;

public class MainGUI {

    ConfigManager configManager;
    private final static Logger logger = Logger.getLogger("XCasino Menu Manager");
    SGMenu menu;

    public MainGUI(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void openGUI(Player player){
        String title = configManager.getConfig().guiName();

        int x = configManager.getConfig().guiLayout().replace("\n", "").length();
        String layout = configManager.getConfig().guiLayout().replace("\n", "");
        int rows = x/9;
        if (rows < 1 || rows > 6){
            logger.severe("The menu is not formatted correctly, please recreate the config.yml file.");
        }
        menu = spiGUI.create(title, rows);

        for (int i = 0; i < layout.length(); i++){
            if (layout.charAt(i) == '-'){
                Material material = configManager.getConfig().blankObject();
                ItemBuilder item = new ItemBuilder(material)
                        .name("")
                        .lore("")
                        .amount(1)
                        .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                SGButton button = new SGButton(item.build());
                menu.setButton(i, button);
            }else {
                issueGamemodes(layout.charAt(i), i);
            }
        }
        player.openInventory(menu.getInventory());
    }

    public void issueGamemodes(char slot, int i) {
        Material material = null;
        String name = "";
        String lore = "";
        Map<Character, CustomItem> customItems = parseCustomItems();

        switch (slot) {
            case 's':
                material = configManager.getConfig().slotsItem();
                name = configManager.getConfig().slotsName();
                lore = configManager.getConfig().slotsDescription();
                break;
            case 'm':
                material = configManager.getConfig().minesItem();
                name = configManager.getConfig().minesName();
                lore = configManager.getConfig().minesDescription();
                break;
            case 'c':
                material = configManager.getConfig().crashItem();
                name = configManager.getConfig().crashName();
                lore = configManager.getConfig().crashDescription();
                break;
            case 'b':
                material = configManager.getConfig().blackjackItem();
                name = configManager.getConfig().blackjackName();
                lore = configManager.getConfig().blackjackDescription();
                break;
            case 'x':
                material = configManager.getConfig().exitItem();
                name = configManager.getConfig().exitName();
                lore = configManager.getConfig().exitDescription();
                break;
            default:
                // Check if it's a custom item
                CustomItem customItem = customItems.get(slot);
                if (customItem != null) {
                    material = customItem.material;
                    name = customItem.name;
                    lore = customItem.lore;
                } else {
                    material = Material.BARRIER;
                    name = "§cInvalid Item";
                    lore = "§7This item is not configured properly";
                    logger.warning("No definition found for custom item character: '" + slot + "'");
                }
        }

        if (material == null) {
            material = Material.BARRIER;
            name = "§cError";
            lore = "§7Invalid material for this item";
            logger.warning("Null material for menu item: " + slot);
        }


        lore = ColorFormater.c(lore);
        String[] results = lore.split("</nl>");

        ItemBuilder item;
        if (material != Material.AIR){
            item = new ItemBuilder(material)
                    .name(ColorFormater.c(name))
                    .lore(results)
                    .amount(1)
                    .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        } else {
            item = new ItemBuilder(Material.AIR)
                    .amount(1);
        }


        SGButton button = new SGButton(item.build());
        menu.setButton(i, button);

        button.withListener(inventoryClickEvent -> {
            Player user = (Player) inventoryClickEvent.getWhoClicked();
            switch (slot) {
                case 'x':
                    user.closeInventory();
                    break;
                case 'm':
                    user.closeInventory();
                    ChatUtilKt.typeInChat(user, "Mines bet", 15L, (String found) -> {
                        try {
                            double amount = Double.parseDouble(found);
                            if (amount < 1) {
                                user.sendMessage("Invalid amount");
                                return null;
                            }
                            (new MinesGUI(configManager)).openGUI(user, amount);
                        } catch (NumberFormatException e) {
                            user.sendMessage("[Null] Invalid amount");
                        }
                        return null;
                    }, () -> {
                        user.sendMessage("Ran out of time buddy!");
                        return null;
                    });
                    break;
            }
        });
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
                        logger.warning("Invalid material for custom item '" + parts[0] + "': " + parts[1]);
                    }
                } else {
                    logger.warning("Invalid custom item format: " + definition);
                }
            }
        } catch (Exception e) {
            logger.severe("Error parsing custom items: " + e.getMessage());
        }

        return items;
    }
}