package studio.awel.FancyCasinos.utilities.awel;

import org.bukkit.entity.Player;

public class PlaySounds {

    public static void sound(Player p, String sound){
        switch (sound.toLowerCase()){
            case "click":
                p.playSound(p.getLocation(), "ui.button.click", 1, 1);
                break;
            case "error":
                p.playSound(p.getLocation(), "entity.villager.no", 1, 1);
                break;
            case "success":
                p.playSound(p.getLocation(), "block.amethyst_block.resonate", 1, 1);
                break;
            case "bomb":
                p.playSound(p.getLocation(), "entity.generic.explode", 1, 1);
                break;
            case "lose":
                p.playSound(p.getLocation(), "entity.ender_dragon.hurt", 1, 1);
                break;
            case "win":
                p.playSound(p.getLocation(), "entity.player.levelup", 1, 1);
                break;
            case "tie":
                p.playSound(p.getLocation(), "entity.allay.item_given", 3, 2);
                break;
            case "open":
                p.playSound(p.getLocation(), "block.barrel.open", 1, 1);
                break;
            default:
                break;
        }
    }
}
