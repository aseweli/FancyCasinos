package studio.awel.FancyCasinos.crash;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.config.ConfigManager;
import studio.awel.FancyCasinos.utilities.MoneyUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CrashMasterClass {
    private static CrashMasterClass instance;
    private final FancyCasinos plugin;
    private final ConfigManager configManager;

    private CrashGame currentGame;
    private BukkitTask schedulerTask;
    public GamePhase currentPhase = GamePhase.WAITING;

    private int betPeriodSeconds = 15;
    private int minimumPlayers = 1;

    private Consumer<Integer> onCountdownUpdate;
    private Consumer<CrashGame> onGameStart;
    private Consumer<Double> onCrash;
    private Consumer<Double> onMultiplierUpdate;
    private Consumer<CrashPlayer> onPlayerCashout;

    private enum GamePhase {
        WAITING, COUNTDOWN, RUNNING
    }

    private CrashMasterClass(FancyCasinos plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.currentGame = new CrashGame();
        loadConfig();
        setupGameLoop();
    }

    public static CrashMasterClass getInstance(FancyCasinos plugin, ConfigManager configManager) {
        if (instance == null) {
            instance = new CrashMasterClass(plugin, configManager);
        }
        return instance;
    }

    public static CrashMasterClass getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CrashMasterClass not initialized with plugin instance");
        }
        return instance;
    }

    private void loadConfig() {
        ConfigurationSection config = plugin.getConfig().getConfigurationSection("crash");
        if (config != null) {
            betPeriodSeconds = config.getInt("betPeriodSeconds", 15);
            minimumPlayers = config.getInt("minimumPlayers", 1);
        }
    }

    private void setupGameLoop() {
        schedulerTask = new BukkitRunnable() {
            private int countdownSeconds = betPeriodSeconds;

            @Override
            public void run() {
                switch (currentPhase) {
                    case WAITING:
                        countdownSeconds = betPeriodSeconds;
                        currentPhase = GamePhase.COUNTDOWN;
                        break;

                    case COUNTDOWN:
                        if (countdownSeconds > 0) {
                            if (onCountdownUpdate != null) {
                                onCountdownUpdate.accept(countdownSeconds);
                            }
                            countdownSeconds--;
                        } else {
                            if (getCurrentGame().getPlayers().size() >= minimumPlayers) {
                                startGame();
                            } else {
                                currentPhase = GamePhase.WAITING;
                            }
                        }
                        break;

                    case RUNNING:
                        if (getCurrentGame().hasCrashed()) {
                            currentGame = new CrashGame();
                            setupGameCallbacks();
                            currentPhase = GamePhase.WAITING;
                        }
                        break;
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void setupGameCallbacks() {
        currentGame
                .onMultiplierUpdate(multiplier -> {
                    if (onMultiplierUpdate != null) {
                        onMultiplierUpdate.accept(multiplier);
                    }
                })
                .onCrash(crashPoint -> {
                    if (onCrash != null) {
                        onCrash.accept(crashPoint);
                    }
                })
                .onPlayerCashout(player -> {
                    if (onPlayerCashout != null) {
                        onPlayerCashout.accept(player);
                    }
                });
    }

    private void startGame() {
        currentPhase = GamePhase.RUNNING;
        setupGameCallbacks();

        if (currentGame.startGame(plugin) && onGameStart != null) {
            onGameStart.accept(currentGame);
        }
    }

    public boolean addPlayerToBet(CrashPlayer player) {
        if (currentPhase == GamePhase.RUNNING) {
            return false;
        }

        return currentGame.addPlayer(player);
    }

    public double cashoutPlayer(CrashPlayer player) {
        return currentGame.cashout(player);
    }

    public CrashGame getCurrentGame() {
        return currentGame;
    }

    public CrashMasterClass onCountdownUpdate(Consumer<Integer> callback) {
        this.onCountdownUpdate = callback;
        return this;
    }

    public CrashMasterClass onGameStart(Consumer<CrashGame> callback) {
        this.onGameStart = callback;
        return this;
    }

    public CrashMasterClass onCrash(Consumer<Double> callback) {
        this.onCrash = callback;
        return this;
    }

    public CrashMasterClass onMultiplierUpdate(Consumer<Double> callback) {
        this.onMultiplierUpdate = callback;
        return this;
    }

    public CrashMasterClass onPlayerCashout(Consumer<CrashPlayer> callback) {
        this.onPlayerCashout = callback;
        return this;
    }

    public void shutdown() {
        if (schedulerTask != null) {
            schedulerTask.cancel();
        }

        if (currentGame != null && currentGame.isRunning()) {
            currentGame.endGame();
        }
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public String getCurrentPhaseName() {
        return currentPhase.name();
    }

    public int getRemainingSeconds() {
        return betPeriodSeconds;
    }
}