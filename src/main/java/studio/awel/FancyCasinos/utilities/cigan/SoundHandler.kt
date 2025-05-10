package studio.awel.FancyCasinos.utilities.cigan

import org.bukkit.Sound
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import studio.awel.FancyCasinos.FancyCasinos

/**
 * Sound Handler
 * Plays custom rhythms to players
 */

class SoundHandler(private val plugin: FancyCasinos) {

    fun playSound(player: Player, soundName: String) {
        val config: FileConfiguration = plugin.config
        val sounds = config.getStringList("sounds.$soundName")

        if (sounds != null) {
            for (sound in sounds) {
                val parts = sound.split(":")
                if (parts.size == 2) {
                    val soundType = Sound.valueOf(parts[0].uppercase())
                    val delay = parts[1].toLong()
                    plugin.server.scheduler.runTaskLater(plugin, Runnable {
                        player.playSound(player.location, soundType, 1.0f, 1.0f)
                    }, delay)
                }
            }
        } else {
            player.sendMessage("Sound $soundName not found in configuration.")
        }
    }
}