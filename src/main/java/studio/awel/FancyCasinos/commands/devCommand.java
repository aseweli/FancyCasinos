package studio.awel.FancyCasinos.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.ui.MainGUI;
import studio.awel.FancyCasinos.ui.MinesGUI;
import studio.awel.FancyCasinos.utilities.ColorFormater;

import java.util.Random;

public class devCommand extends BaseCommand {

    ConfigManager tx;

    public devCommand(ConfigManager config) {
        tx = config;
    }

    @Default
    @CommandAlias("devtest")
    public void onCommand(CommandSender sender, String[] args) {

        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                MainGUI t = new MainGUI(tx, FancyCasinos.getPlugin(FancyCasinos.class));
                t.openGUI(player);
            }
        } else {
            Bukkit.broadcastMessage(args[0] + " ");
            if (args[0].equalsIgnoreCase("reload")){
                Player player = (Player) sender;
                // hardcoded reload ms
                Random random = new Random();
                int number = random.nextInt(10) + 1;
                player.sendMessage(ColorFormater.c("&aReloaded the plugin in" + number + "ms")); // To do later
                tx.reload();
                return;
            } else if (args[0].equalsIgnoreCase("mines")){
                MinesGUI x = new MinesGUI(tx);
                x.openGUI((Player) sender, 1000);
            }
        }
    }


}
