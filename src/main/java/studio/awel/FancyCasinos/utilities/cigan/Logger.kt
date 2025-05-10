package studio.awel.FancyCasinos.utilities.cigan

import org.bukkit.Bukkit

object Logger {
    fun log(msg: String, status: String) {
        if (status == "err" || status == "error") {
            Bukkit.getConsoleSender().sendMessage("&#ae4ff7[XCASINOS] &#FF0000[ERROR] &c$msg".translate())
        } else if (status == "warning") {
            Bukkit.getConsoleSender().sendMessage("&#ae4ff7[XCASINOS] &#ed480c[WARNING] &#f78928$msg".translate())
        } else if (status == "success") {
            Bukkit.getConsoleSender().sendMessage("&#ae4ff7[XCASINOS] &#0ced35[SUCCESS] &a$msg".translate())
        } else if (status == "info") {
            Bukkit.getConsoleSender().sendMessage("&#ae4ff7[XCASINOS] &#03b1fc[INFO] &#69cefa$msg".translate())
        } else if (status == "debug"){
            Bukkit.getConsoleSender().sendMessage("&#ae4ff7[XCASINOS] &#03b1fc[DEBUG] &#69cefa$msg".translate())
        }
    }
}