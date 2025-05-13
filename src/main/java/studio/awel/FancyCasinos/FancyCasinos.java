package studio.awel.FancyCasinos;

import co.aikar.commands.BukkitCommandManager;
import com.samjakob.spigui.SpiGUI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import studio.awel.FancyCasinos.commands.CasinoCommand;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.crash.CrashMasterClass;
import studio.awel.FancyCasinos.events.KeepUI;
import studio.awel.FancyCasinos.utilities.MoneyUtil;

import java.util.logging.Logger;

public final class FancyCasinos extends JavaPlugin {

    BukkitCommandManager commandManager;
    public static SpiGUI spiGUI;
    ConfigManager configManager;
    CrashMasterClass crashMasterClass;
    private static Economy economy = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        commandManager = new BukkitCommandManager(this);
        spiGUI = new SpiGUI(this);
        crashMasterClass = CrashMasterClass.getInstance(this, configManager);
        configManager = new ConfigManager(this.getDataFolder());

        if (!setupEconomy()) {
            Logger logger = getLogger();
            logger.severe("No Vault compatible economy plugin found!");
            logger.severe("Please install an economy plugin or disable this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerCommands();
        registerEvents();
        MoneyUtil.getInstance().initialize(this);

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new KeepUI(this), this);
    }

    public void registerCommands(){
         commandManager.registerCommand(new CasinoCommand(configManager));

    }
}
