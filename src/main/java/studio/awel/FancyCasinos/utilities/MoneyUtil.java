package studio.awel.FancyCasinos.utilities;

import net.milkbowl.vault.economy.Economy;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import studio.awel.FancyCasinos.FancyCasinos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyUtil {
    private static MoneyUtil instance = new MoneyUtil();
    private JavaPlugin plugin;
    private Economy economy;

    private MoneyUtil() {}

    public static MoneyUtil getInstance() {
        if (instance == null) {
            instance = new MoneyUtil();
        }
        return instance;
    }

    public void initialize(FancyCasinos plugin) {
        this.plugin = plugin;
        this.economy = FancyCasinos.getEconomy();
    }

    public static void typeInChat(Player player, String key, long timeoutSeconds, Consumer<String> onReceive, Runnable onTimeout) {
        getInstance().promptWithAnvil(player, key, onReceive, onTimeout);
    }

    public void promptWithAnvil(Player player, String key, Consumer<String> onReceive, Runnable onTimeout) {
        if (plugin == null) {
            throw new IllegalStateException("MoneyUtil not initialized with plugin instance");
        }



        final boolean[] inputProcessed = {false};

        new AnvilGUI.Builder()
                .plugin(plugin)
                .title("Enter " + key)
                .text("Enter amount...")
                .itemLeft(new ItemStack(Material.PAPER))
                .onClick((slot, stateSnapshot) -> {
                    if (slot == AnvilGUI.Slot.OUTPUT) {
                        String input = stateSnapshot.getText();
                        try {
                            double amount = parseNumbers(input);
                            inputProcessed[0] = true;
                            onReceive.accept(String.valueOf(amount));
                            return AnvilGUI.Response.close();
                        } catch (NumberFormatException e) {
                            return AnvilGUI.Response.text("Invalid number");
                        }
                    }
                    return AnvilGUI.Response.text("Enter amount...");
                })
                .onClose(p -> {
                    if (!inputProcessed[0] && onTimeout != null) {
                        onTimeout.run();
                    }
                })
                .open(player);
    }

    public static double parseNumbers(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0.0;
        }

        Map<String, Double> multi = new HashMap<>();
        multi.put("k", 10000.0);
        multi.put("m", 1000000.0);
        multi.put("b", 1000000000.0);
        multi.put("t", 1000000000000.0);

        Pattern pattern = Pattern.compile("(\\d+)([kmbt]?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String number = matcher.group(1);
            String suffix = matcher.group(2);
            double multiplier = suffix.isEmpty() ? 1.0 : multi.getOrDefault(suffix.toLowerCase(), 1.0);
            return Double.parseDouble(number) * multiplier;
        } else {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
    }

    public double getBalance(Player player) {
        return economy.getBalance(player);
    }

    public boolean hasEnough(Player player, double amount) {
        return economy.has(player, amount);
    }

    public boolean withdraw(Player player, double amount) {
        return economy.withdrawPlayer(player, amount).transactionSuccess();
    }

    public boolean deposit(Player player, double amount) {
        return economy.depositPlayer(player, amount).transactionSuccess();
    }


}