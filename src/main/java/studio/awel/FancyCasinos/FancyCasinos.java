package studio.awel.xCasinos;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.PaperCommandManager;
import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.plugin.java.JavaPlugin;
import studio.awel.xCasinos.commands.devCommand;
import studio.awel.xCasinos.config.ConfigManager;
import studio.awel.xCasinos.slots.SlotsCommand;
import studio.awel.xCasinos.utilities.cigan.ChatUtil;
import studio.awel.xCasinos.utilities.cigan.Logger;

public final class XCasinos extends JavaPlugin {

    BukkitCommandManager commandManager;
    public static SpiGUI spiGUI;
    ConfigManager configManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        commandManager = new BukkitCommandManager(this);
        spiGUI = new SpiGUI(this);

        configManager = new ConfigManager(this.getDataFolder());

        registerCommands();
        ChatUtil.INSTANCE.startTTC(this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        commandManager.registerCommand(new devCommand(configManager));
        commandManager.registerCommand(new SlotsCommand());

    }
}
