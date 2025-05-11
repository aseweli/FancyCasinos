package studio.awel.FancyCasinos.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.ui.MainGUI;
import studio.awel.FancyCasinos.utilities.ColorFormater;


@CommandAlias("casino")
@CommandPermission("fancycasinos.use")
public class CasinoCommand extends BaseCommand {


    ConfigManager configManager;

    public CasinoCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Default
    public void casinoCommand(CommandSender sender) {
        if (sender instanceof Player) {
            MainGUI menu = new MainGUI(configManager, FancyCasinos.getPlugin(FancyCasinos.class));
            menu.openGUI((Player) sender);
        } else {
            sender.sendMessage("This command can only be used by players");
        }
    }

    @Subcommand("reload")
    @CommandPermission("fancycasinos.admin")
    public void reloadCommand(CommandSender sender) {
        long startTime = System.currentTimeMillis();
        configManager.reload();
        long elapsedTime = System.currentTimeMillis() - startTime;

        String message = configManager.getConfig().reloadTimeMessage()
                .replace("{ms}", String.valueOf(elapsedTime));
        sender.sendMessage(ColorFormater.c(message));
    }

    @HelpCommand
    public void helpCommand(CommandSender sender) {
        sender.sendMessage(ColorFormater.c(configManager.getConfig().helpMessage().replace("</nl>", "\n")));
    }
}
