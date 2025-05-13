package studio.awel.FancyCasinos.crash;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.utilities.ColorFormater;
import studio.awel.FancyCasinos.utilities.MoneyUtil;
import studio.awel.FancyCasinos.utilities.awel.PlaySounds;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static studio.awel.FancyCasinos.FancyCasinos.spiGUI;

public class CrashGUI {
    private static final Logger LOGGER = Logger.getLogger("FancyCasinos");
    private static final int INVENTORY_SIZE = 54;

    private final FancyCasinos plugin;
    private final ConfigManager configManager;
    private final CrashMasterClass crashManager;

    private SGMenu menu;
    private final Map<UUID, Double> playerBets = new HashMap<>();

    private boolean isGameRunning = false;
    private double currentMultiplier = 1.0;

    public CrashGUI(FancyCasinos plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.crashManager = CrashMasterClass.getInstance();

        setupCrashCallbacks();
    }

    private void setupCrashCallbacks() {
        crashManager
                .onCountdownUpdate(this::updateCountdown)
                .onGameStart(this::startGameDisplay)
                .onMultiplierUpdate(this::updateMultiplier)
                .onCrash(this::showCrash)
                .onPlayerCashout(this::showPlayerCashout);
    }

    public void openGUI(Player player) {
        menu = spiGUI.create(
                ColorFormater.c(configManager.getConfig().crashTitle()),
                6
        );

        setupInitialLayout(player);
        player.openInventory(menu.getInventory());
        PlaySounds.sound(player, "open");
    }

    private void setupInitialLayout(Player player) {
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            setBlankItem(menu, i);
        }

        String status = crashManager.getCurrentPhaseName();
        if (status.equals("COUNTDOWN")) {
            status += " - " + crashManager.getRemainingSeconds() + "s";
        }

        setInfoItem(menu, 4,
                configManager.getConfig().crashInfoItem(),
                configManager.getConfig().crashStatusTitle(),
                configManager.getConfig().crashStatusDescription()
                        .replace("%status%", status)
        );

        if (isGameRunning && playerBets.containsKey(player.getUniqueId())) {
            setBetDisabledButton(menu, 20, player);
            setCashoutButton(menu, 24, player, true);
        } else if (isGameRunning) {
            setBetDisabledButton(menu, 20, player);
            setCashoutButton(menu, 24, player, false);
        } else {
            setBetButton(menu, 20, player);
            setCashoutButton(menu, 24, player, false);
        }

        setPlayerInfoItem(menu, 40, player);
    }

    private void updateCountdown(int seconds) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTitle().equals(ColorFormater.c(configManager.getConfig().crashTitle()))) {
                setInfoItem(menu, 4,
                        configManager.getConfig().crashInfoItem(),
                        configManager.getConfig().crashCountdownTitle(),
                        configManager.getConfig().crashCountdownDescription()
                                .replace("%seconds%", String.valueOf(seconds))
                );
                refreshMenu(player);
            }
        });
    }

    private void startGameDisplay(CrashGame game) {
        isGameRunning = true;
        currentMultiplier = 1.0;

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTitle().equals(ColorFormater.c(configManager.getConfig().crashTitle()))) {
                setInfoItem(menu, 4,
                        configManager.getConfig().crashRunningItem(),
                        configManager.getConfig().crashGameStartTitle(),
                        configManager.getConfig().crashGameStartDescription()
                                .replace("%multiplier%", formatMultiplier(currentMultiplier))
                );
                boolean hasBet = playerBets.containsKey(player.getUniqueId());
                setCashoutButton(menu, 24, player, hasBet);
                setBetDisabledButton(menu, 20, player);
                refreshMenu(player);
            }
        });
    }

    private void updateMultiplier(double multiplier) {
        this.currentMultiplier = multiplier;

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTitle().equals(ColorFormater.c(configManager.getConfig().crashTitle()))) {
                setInfoItem(menu, 4,
                        configManager.getConfig().crashRunningItem(),
                        configManager.getConfig().crashMultiplierTitle(),
                        configManager.getConfig().crashMultiplierDescription()
                                .replace("%multiplier%", formatMultiplier(multiplier))
                );

                if (playerBets.containsKey(player.getUniqueId())) {
                    setCashoutButton(menu, 24, player, true);
                }

                refreshMenu(player);
            }
        });
    }

    private void showCrash(double crashPoint) {
        isGameRunning = false;

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTitle().equals(ColorFormater.c(configManager.getConfig().crashTitle()))) {
                setInfoItem(menu, 4,
                        configManager.getConfig().crashCrashedItem(),
                        configManager.getConfig().crashGameEndTitle(),
                        configManager.getConfig().crashGameEndDescription()
                                .replace("%multiplier%", formatMultiplier(crashPoint))
                );

                setCashoutButton(menu, 24, player, false);
                setBetButton(menu, 20, player);

                refreshMenu(player);
            }
        });

        playerBets.clear();
    }

    private void showPlayerCashout(CrashPlayer player) {
        Player bukkitPlayer = player.getPlayer();
        if (bukkitPlayer == null) return;

        if (bukkitPlayer.getOpenInventory().getTitle().equals(ColorFormater.c(configManager.getConfig().crashTitle()))) {
            bukkitPlayer.sendMessage(ColorFormater.c(
                    configManager.getConfig().crashCashoutMessage()
                            .replace("%amount%", String.format("%.2f", player.getBet() * currentMultiplier))
                            .replace("%multiplier%", formatMultiplier(currentMultiplier))
            ));

            setCashoutButton(menu, 24, bukkitPlayer, false);
            setPlayerInfoItem(menu, 40, bukkitPlayer);

            refreshMenu(bukkitPlayer);
        }
    }

    private void refreshMenu(Player player) {
        player.updateInventory();
    }

    private void handleBetClick(Player player) {
        player.closeInventory();

        MoneyUtil.typeInChat(player, configManager.getConfig().crashBetPrompt(), 15L,
                input -> {
                    try {
                        double amount = MoneyUtil.parseNumbers(input);

                        if (amount < configManager.getConfig().crashMinBetAmount()) {
                            player.sendMessage(ColorFormater.c(
                                    configManager.getConfig().minBetMessage()
                                            .replace("%min%", String.valueOf(configManager.getConfig().crashMinBetAmount()))
                            ));
                            openGUI(player);
                            return;
                        }

                        if (!MoneyUtil.getInstance().hasEnough(player, amount)) {
                            player.sendMessage(ColorFormater.c(configManager.getConfig().insufficientFundsMessage()));
                            openGUI(player);
                            return;
                        }

                        CrashPlayer crashPlayer = new CrashPlayer(player, amount);
                        if (crashManager.addPlayerToBet(crashPlayer)) {
                            MoneyUtil.getInstance().withdraw(player, amount);
                            playerBets.put(player.getUniqueId(), amount);

                            player.sendMessage(ColorFormater.c(
                                    configManager.getConfig().crashBetPlacedMessage()
                                            .replace("%amount%", String.format("%.2f", amount))
                            ));
                        } else {
                            player.sendMessage(ColorFormater.c(configManager.getConfig().crashBetFailedMessage()));
                        }

                        openGUI(player);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ColorFormater.c(configManager.getConfig().invalidValueMessage()));
                        openGUI(player);
                    }
                },
                () -> {
                    player.sendMessage(ColorFormater.c(configManager.getConfig().betTimeoutMessage()));
                    openGUI(player);
                }
        );
    }

    private void handleCashoutClick(Player player) {
        if (!isGameRunning || !playerBets.containsKey(player.getUniqueId())) {
            return;
        }

        CrashPlayer crashPlayer = null;
        for (Map.Entry<CrashPlayer, Double> entry : crashManager.getCurrentGame().getPlayers().entrySet()) {
            if (entry.getKey().getPlayer().getUniqueId().equals(player.getUniqueId()) &&
                    entry.getValue() < 0) {
                crashPlayer = entry.getKey();
                break;
            }
        }

        if (crashPlayer != null) {
            crashManager.cashoutPlayer(crashPlayer);
        }
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

    private void setInfoItem(SGMenu menu, int slot, Material material, String name, String lore) {
        SGButton button = createButton(
                material,
                name,
                lore,
                event -> {}
        );
        menu.setButton(slot, button);
    }

    private void setBetButton(SGMenu menu, int slot, Player player) {
        SGButton button = createButton(
                configManager.getConfig().crashBetItem(),
                configManager.getConfig().crashBetTitle(),
                configManager.getConfig().crashBetDescription(),
                event -> handleBetClick(player)
        );
        menu.setButton(slot, button);
    }

    private void setBetDisabledButton(SGMenu menu, int slot, Player player) {
        SGButton button = createButton(
                configManager.getConfig().crashBetDisabledItem(),
                configManager.getConfig().crashBetDisabledTitle(),
                configManager.getConfig().crashBetDisabledDescription(),
                event -> {}
        );
        menu.setButton(slot, button);
    }

    private void setCashoutButton(SGMenu menu, int slot, Player player, boolean enabled) {
        if (enabled) {
            SGButton button = createButton(
                    configManager.getConfig().crashCashoutItem(),
                    configManager.getConfig().crashCashoutTitle(),
                    configManager.getConfig().crashCashoutDescription()
                            .replace("%multiplier%", formatMultiplier(currentMultiplier))
                            .replace("%amount%", String.format("%.2f",
                                    playerBets.getOrDefault(player.getUniqueId(), 0.0) * currentMultiplier)
                            ),
                    event -> handleCashoutClick(player)
            );
            menu.setButton(slot, button);
        } else {
            SGButton button = createButton(
                    configManager.getConfig().crashCashoutDisabledItem(),
                    configManager.getConfig().crashCashoutDisabledTitle(),
                    configManager.getConfig().crashCashoutDisabledDescription(),
                    event -> {}
            );
            menu.setButton(slot, button);
        }
    }

    private void setPlayerInfoItem(SGMenu menu, int slot, Player player) {
        double balance = MoneyUtil.getInstance().getBalance(player);
        double bet = playerBets.getOrDefault(player.getUniqueId(), 0.0);

        SGButton button = createButton(
                configManager.getConfig().playerInfoItem(),
                configManager.getConfig().playerInfoTitle()
                        .replace("%player%", player.getName()),
                configManager.getConfig().playerInfoDescription()
                        .replace("%balance%", String.format("%.2f", balance))
                        .replace("%bet%", String.format("%.2f", bet)),
                event -> {}
        );
        menu.setButton(slot, button);
    }

    private SGButton createButton(Material material, String name, String lore, Consumer<InventoryClickEvent> clickHandler) {
        if (material == null) {
            material = Material.BARRIER;
            name = "§cError";
            lore = "§7Invalid material for this item";
        }

        String[] loreLines = ColorFormater.c(lore).split("</nl>");
        ItemBuilder itemBuilder = new ItemBuilder(material)
                .name(ColorFormater.c(name))
                .lore(loreLines)
                .amount(1)
                .flag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

        SGButton button = new SGButton(itemBuilder.build());
        button.withListener(clickHandler::accept);
        return button;
    }

    private String formatMultiplier(double value) {
        return String.format("%.2fx", value);
    }
}