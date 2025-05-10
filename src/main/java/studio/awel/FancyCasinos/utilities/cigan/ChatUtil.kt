package studio.awel.FancyCasinos.utilities.cigan

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import studio.awel.FancyCasinos.utilities.ColorFormater
import java.util.UUID

// Player Extension

fun Player.typeInChat(key: String, timeoutSeconds: Long, onRecieve: (String) -> Unit, onTimeout: (() -> Unit)? = null) {
    ChatUtil.listenFor(this, key, timeoutSeconds, onRecieve, onTimeout)
}

// Utility

object ChatUtil : Listener {
    private val waitingPlayers = HashMap<UUID, ChatPrompt>()
    private val scheduledTasks = mutableMapOf<UUID, BukkitTask>()

    data class ChatPrompt(
        val key: String,
        val callback: (String) -> Unit,
        val cancelCallback: (() -> Unit)? = null
    )

    fun startTTC(plugin: JavaPlugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    fun listenFor(player: Player, key: String, timeoutSeconds: Long, onRecieve: (String) -> Unit, onTimeout: (() -> Unit)? = null) {
        val uuid = player.uniqueId

        waitingPlayers[uuid]?.let {
            waitingPlayers.remove(uuid)
        }
        scheduledTasks[uuid]?.cancel()

        waitingPlayers[uuid] = ChatPrompt(key, onRecieve, onTimeout)
        player.sendMessage(ColorFormater.c("&aPlease type your &f&n$key&a in chat within &f&n$timeoutSeconds&a seconds"))

        val task = Bukkit.getScheduler().runTaskLater(JavaPlugin.getProvidingPlugin(ChatUtil::class.java), Runnable {
            if (waitingPlayers.containsKey(uuid)) {
                waitingPlayers.remove(uuid)
                scheduledTasks.remove(uuid)
                player.sendMessage("&cYou took too long.".translate())
                onTimeout?.invoke()
            }
        }, timeoutSeconds * 20)
        scheduledTasks[uuid] = task
    }

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val uuid = event.player.uniqueId
        val prompt = waitingPlayers.remove(uuid) ?: return

        event.isCancelled = true
        val rawMessage = event.message.trim()

        Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(ChatUtil::class.java), Runnable {
            prompt.callback(parseNumbers(rawMessage).toString())
        })
    }
    
    fun parseNumbers(input: String): Double{
        val multi = mapOf(
            "k" to 1_0000.0,
            "m" to 1_000_000.0,
            "b" to 1_000_000_000.0,
            "t" to 1_000_000_000_000.0,
        )
        
        val regex = Regex("""(\d+)([kmbt]?)""", RegexOption.IGNORE_CASE)
        val match = regex.matchEntire(input)
        
        return if (match != null){
            val(number, suffix) = match.destructured
            val multiplier = multi[suffix.lowercase()] ?: 1.0
            number.toDouble() * multiplier
        } else {
            input.toDoubleOrNull() ?: 0.0
        }
    }
}