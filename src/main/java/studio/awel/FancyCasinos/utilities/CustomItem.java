package studio.awel.xCasinos.utilities;

import org.bukkit.Material;

public class CustomItem {
    public final Material material;
    public final String name;
    public final String lore;

    public CustomItem(Material material, String name, String lore) {
        this.material = material;
        this.name = name;
        this.lore = lore;
    }
}
