package studio.awel.FancyCasinos;

import co.aikar.commands.BukkitCommandManager;
import com.google.errorprone.annotations.Keep;
import com.samjakob.spigui.SpiGUI;
import org.bukkit.plugin.java.JavaPlugin;
import studio.awel.FancyCasinos.commands.devCommand;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.events.KeepUI;
import studio.awel.FancyCasinos.slots.SlotsCommand;
import studio.awel.FancyCasinos.utilities.cigan.ChatUtil;

public final class FancyCasinos extends JavaPlugin {

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
        registerEvents();
        ChatUtil.INSTANCE.startTTC(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new KeepUI(this), this);
    }

    public void registerCommands(){
        commandManager.registerCommand(new devCommand(configManager));
        commandManager.registerCommand(new SlotsCommand());

    }
}
