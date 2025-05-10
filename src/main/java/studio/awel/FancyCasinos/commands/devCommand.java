package studio.awel.xCasinos.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.sun.tools.javac.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import studio.awel.xCasinos.config.ConfigManager;
import studio.awel.xCasinos.ui.MainGUI;
import studio.awel.xCasinos.ui.MinesGUI;
import studio.awel.xCasinos.utilities.ColorFormater;

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
                MainGUI t = new MainGUI(tx);
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
