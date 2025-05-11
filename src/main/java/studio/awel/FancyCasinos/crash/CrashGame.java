package studio.awel.FancyCasinos.crash;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.utilities.MoneyUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class CrashGame {
    private enum GameState {
        WAITING, RUNNING, CRASHED
    }

    private final HashMap<CrashPlayer, Double> players = new HashMap<>();
    private double multiplier = 1.0;
    private double crashPoint;
    private GameState state = GameState.WAITING;
    private BukkitTask gameTask;

    private Consumer<Double> onMultiplierUpdate;
    private Consumer<Double> onCrash;
    private Consumer<CrashPlayer> onPlayerCashout;

    private static final double MULTIPLIER_INCREMENT = 0.05;
    private static final long UPDATE_TICKS = 2L;
    private static final double MIN_CRASH = 1.1;
    private static final double MAX_CRASH = 10.0;

    public CrashGame() {
        generateCrashPoint();
    }


    public boolean addPlayer(CrashPlayer player) {
        if (state != GameState.WAITING) {
            return false;
        }
        players.put(player, -1.0);
        return true;
    }


    public double cashout(CrashPlayer player) {
        if (state != GameState.RUNNING || !players.containsKey(player) || players.get(player) > 0) {
            return -1;
        }

        players.put(player, multiplier);
        double winnings = player.getBet() * multiplier;

        if (onPlayerCashout != null) {
            onPlayerCashout.accept(player);
        }

        MoneyUtil.getInstance().deposit(player.getPlayer(), winnings);

        return winnings;
    }


    public boolean startGame(FancyCasinos plugin) {
        if (state != GameState.WAITING || players.isEmpty()) {
            return false;
        }

        state = GameState.RUNNING;
        multiplier = 1.0;

        gameTask = new BukkitRunnable() {
            @Override
            public void run() {
                multiplier += MULTIPLIER_INCREMENT;

                if (onMultiplierUpdate != null) {
                    onMultiplierUpdate.accept(multiplier);
                }

                if (multiplier >= crashPoint) {
                    crash();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, UPDATE_TICKS);

        return true;
    }

    public void endGame() {
        if (gameTask != null) {
            gameTask.cancel();
        }

        if (state == GameState.RUNNING) {
            crash();
        }

        reset();
    }

    private void crash() {
        state = GameState.CRASHED;
        if (onCrash != null) {
            onCrash.accept(crashPoint);
        }
    }

    private void reset() {
        players.clear();
        state = GameState.WAITING;
        generateCrashPoint();
        multiplier = 1.0;
    }

    private void generateCrashPoint() {
        Random random = new Random();
        double randomValue = random.nextDouble();
        crashPoint = MIN_CRASH + (MAX_CRASH - MIN_CRASH) * Math.pow(randomValue, 2);
    }

    public CrashGame onMultiplierUpdate(Consumer<Double> callback) {
        this.onMultiplierUpdate = callback;
        return this;
    }

    public CrashGame onCrash(Consumer<Double> callback) {
        this.onCrash = callback;
        return this;
    }

    public CrashGame onPlayerCashout(Consumer<CrashPlayer> callback) {
        this.onPlayerCashout = callback;
        return this;
    }

    public Map<CrashPlayer, Double> getPlayers() {
        return new HashMap<>(players);
    }

    public double getMultiplier() {
        return multiplier;
    }

    public boolean isRunning() {
        return state == GameState.RUNNING;
    }

    public boolean hasCrashed() {
        return state == GameState.CRASHED;
    }

    public String formatMultiplier(double value) {
        return String.format("%.2fx", value);
    }
}