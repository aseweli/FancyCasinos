package studio.awel.FancyCasinos.crash;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import studio.awel.FancyCasinos.FancyCasinos;
import studio.awel.FancyCasinos.utilities.MoneyUtil;

import java.util.*;
import java.util.function.Consumer;

public class CrashGame {
    private enum GameState {
        WAITING, RUNNING, CRASHED
    }

    private final HashMap<PlayerData, Double> players = new HashMap<>();
    private double multiplier = 1.0;
    private double crashPoint;
    private GameState state = GameState.WAITING;
    private BukkitTask gameTask;
    private static int nextId = 1;
    private final int id = nextId++;

    private Consumer<Double> onMultiplierUpdate;
    private Consumer<Double> onCrash;
    private Consumer<PlayerData> onPlayerCashout;

    private static final double MULTIPLIER_INCREMENT = 0.05;
    private static final long UPDATE_TICKS = 2L;
    private static final double MIN_CRASH = 1.1;
    private static final double MAX_CRASH = 10.0;

    public CrashGame() {
        generateCrashPoint();
    }

    public boolean addPlayer(PlayerData player) {
        if (state != GameState.WAITING) {
            return false;
        }
        players.put(player, -1.0);
        return true;
    }

    public double cashout(PlayerData player) {
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

    public CrashGame onPlayerCashout(Consumer<PlayerData> callback) {
        this.onPlayerCashout = callback;
        return this;
    }


    public int getId() {
        return id;
    }


    public String getStatus() {
        switch (state) {
            case WAITING:
                return "Intermission";
            case RUNNING:
                return "Running";
            case CRASHED:
                return "Crashed";
            default:
                return "Unknown";
        }
    }


    public double getOdds() {
        return (MIN_CRASH + MAX_CRASH) / 2.0;
    }


    public int getPlayerCount() {
        return players.size();
    }


    public double getTotalPot() {
        return players.keySet().stream().mapToDouble(PlayerData::getBet).sum();
    }


    public boolean isPlayerInGame(UUID uuid) {
        return players.keySet().stream().anyMatch(pd -> pd.getPlayer().getUniqueId().equals(uuid));
    }


    public int getMinPlayers() {
        return 1; // Or make this configurable
    }


    public List<CrashLeaderboardEntry> getLeaderboard() {
        return players.keySet().stream()
                .sorted((a, b) -> Double.compare(b.getBet(), a.getBet()))
                .map(pd -> new CrashLeaderboardEntry(pd.getPlayer().getUniqueId(), pd.getBet()))
                .toList();
    }

    public void removePlayer(UUID uuid) {
        players.keySet().removeIf(pd -> pd.getPlayer().getUniqueId().equals(uuid));
    }


    public Set<PlayerData> getPlayerSet() {
        return new HashSet<>(players.keySet());
    }


    public Map<PlayerData, Double> getPlayers() {
        return new HashMap<>(players);
    }
}
