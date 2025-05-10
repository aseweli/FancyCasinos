package studio.awel.FancyCasinos.utilities;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Gambling {

    public static HashMap<Player, String> playerGame = new HashMap<>();

    public static void indentPlayerGame(Player player, String game){
        if (playerGame.containsKey(player)){
            playerGame.replace(player, game);
        }else {
            playerGame.put(player, game);
        }
    }

    public static void endPlayerGame(Player player){
        if (playerGame.containsKey(player)){
            playerGame.remove(player);
        }
    }

    public static boolean isPlayerInGame(Player player){
        return playerGame.containsKey(player);
    }

    public static String getPlayerGame(Player player){
        return playerGame.get(player);
    }

    public static ArrayList<Player> getAllPlayersInGame(String game){
        ArrayList<Player> players = new ArrayList<>();
        for (Player player : playerGame.keySet()){
            if (playerGame.get(player).equalsIgnoreCase(game)){
                players.add(player);
            }
        }
        return players;
    }
}
