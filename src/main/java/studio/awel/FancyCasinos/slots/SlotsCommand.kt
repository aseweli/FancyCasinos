package studio.awel.FancyCasinos.slots

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.HelpCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("slots|slot|casinoslots|cslots")
class SlotsCommand: BaseCommand() {

    val machine: Machine = Machine()
    val slotGUI: SlotGUI = SlotGUI()

    @HelpCommand
    fun onHelp(sender: CommandSender) {
        sender.sendMessage("&cNot yet implemented")
    }

    @Default
    fun onCommand(sender: Player, args: String) {
        val isdev = 1

        if (isdev == 1) {
            // open gui
            slotGUI.openMenu(sender)

        } else {
            slotGUI.openMenu(sender)
        }
    }

}