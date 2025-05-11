package studio.awel.FancyCasinos.slots

import com.samjakob.spigui.buttons.SGButton
import com.samjakob.spigui.item.ItemBuilder
import com.samjakob.spigui.menu.SGMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.scheduler.BukkitRunnable
import studio.awel.FancyCasinos.FancyCasinos
import studio.awel.FancyCasinos.utilities.MoneyUtil

import studio.awel.FancyCasinos.utilities.cigan.translate
import java.util.function.Consumer

class SlotGUI {

    val machine = Machine()

    private val slotPositions = arrayOf(
        // Top row
        10, 11, 12,
        // Middle row (winning line)
        19, 20, 21,
        // Bottom row
        28, 29, 30
    )

    // Updated to use only gold-related items
    private val slotItems = mapOf(
        Material.GOLD_NUGGET to 0.5,
        Material.GOLD_INGOT to 1.0,
        Material.RAW_GOLD to 1.5,
        Material.RAW_GOLD_BLOCK to 2.5,
        Material.GOLD_BLOCK to 5.0
    )

    fun openMenu(player: Player) {
        val menu: SGMenu = FancyCasinos.spiGUI.create("Slots", 5)

        setupSlotMachine(menu)

        val spin = ItemBuilder(Material.CLOCK)
            .name("&a&lSPIN")
            .lore(
                "",
                "&8&l| &aClick here to pay for a spin",
                "&8&l| &aYou are betting &a&n$0&a."
            )
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .build()

        val spinButton = SGButton(spin).withListener { event: InventoryClickEvent ->
            val user = event.whoClicked as Player
            user.closeInventory()

            MoneyUtil.typeInChat(user, "Slots Bet", 15L,
                Consumer<String> { input ->
                    val amount = input.toDoubleOrNull()
                    if (amount == null) {
                        user.sendMessage("&cInvalid amount. Please enter a number.".translate())
                        return@Consumer
                    } else {
                        user.sendMessage("&aYou have bet $amount.".translate())

                        val animationMenu = FancyCasinos.spiGUI.create("Slots", 5)
                        setupSlotMachine(animationMenu)
                        user.openInventory(animationMenu.inventory)

                        startSlotAnimation(user, animationMenu, amount)
                    }
                },
                Runnable {
                    user.sendMessage("&cBetting cancelled.".translate())
                }
            )
        }

        menu.setButton(24, spinButton)
        player.openInventory(menu.inventory)
    }

    private fun setupSlotMachine(menu: SGMenu) {
        for (i in 0 until 45) {
            val glassItem = ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .name(" ")
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build()

            menu.setButton(i, SGButton(glassItem))
        }

        for (i in 18..22) {
            val yellowGlass = ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                .name("")
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build()

            menu.setButton(i, SGButton(yellowGlass))
        }

        for (pos in slotPositions) {
            val defaultItem = ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
                .name(" ")
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build()

            menu.setButton(pos, SGButton(defaultItem))
        }

        val instructionsItem = ItemBuilder(Material.OAK_SIGN)
            .name("&7&lINSTRUCTIONS")
            .lore(
                "",
                "&8&l| &7You pay for spins and every spin makes a",
                "&8&l| &7random amount of money. You can get double",
                "&8&l| &7the money if you get a golden",
                "&8&l| &7spin."
            )
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .build()
        menu.setButton(15, SGButton(instructionsItem))

        val earningsItem = ItemBuilder(Material.CHEST)
            .name("&f&lEARNINGS")
            .lore(
                "",
                "&8&l| &fYou have earned &a&n$0&f in total",
                "&8&l| &fYou have earned &a&n$0&f in this spin.",
                "&7",
                "&8&l| &fClick to claim &f&n$0&f.",
            )
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .build()
        menu.setButton(33, SGButton(earningsItem))
    }

    private fun startSlotAnimation(player: Player, menu: SGMenu, betAmount: Double) {
        var animationCount = 0
        val maxAnimations = 20

        object : BukkitRunnable() {
            override fun run() {
                if (animationCount >= maxAnimations) {
                    cancel()

                    val winnings = machine.spin(betAmount.toDouble())
                    displayFinalResult(player, menu, winnings, betAmount)
                    return
                }

                for (pos in slotPositions) {
                    val randomMaterial = slotItems.keys.random()
                    val multiplier = slotItems[randomMaterial] ?: 1.0

                    val slotItem = ItemBuilder(randomMaterial)
                        .name("&e&l${materialToName(randomMaterial).toUpperCase()} &8(${multiplier}x)")
                        .flag(ItemFlag.HIDE_ATTRIBUTES)
                        .build()

                    menu.setButton(pos, SGButton(slotItem))
                    player.openInventory.topInventory.setItem(pos, slotItem)
                }

                player.updateInventory()
                animationCount++
            }
        }.runTaskTimer(FancyCasinos.getPlugin(FancyCasinos::class.java), 5L, 5L)
    }

    private fun displayFinalResult(player: Player, menu: SGMenu, winnings: Machine.SpinResult, betAmount: Double) {
        val rolledBlocks = winnings.rolledBlocks

        for (i in rolledBlocks.indices) {
            if (i >= 3) break

            val slotBlock = rolledBlocks[i]
            val material = nameToMaterial(slotBlock.name)

            val slotItem = ItemBuilder(material)
                .name("&f&l${slotBlock.name}")
                .lore("&eMultiplier: &f${slotBlock.multiplier}x")
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build()

            val pos = 19 + i
            menu.setButton(pos, SGButton(slotItem))
            player.openInventory.topInventory.setItem(pos, slotItem)
        }

        player.updateInventory()

        Bukkit.getScheduler().runTaskLater(
            FancyCasinos.getPlugin(
                FancyCasinos::class.java), Runnable {
            showEndingScreen(player, winnings, betAmount)
        }, 30L)
    }

    private fun showEndingScreen(player: Player, winnings: Machine.SpinResult, betAmount: Double) {
        val isWin = winnings.finalAmount > betAmount
        val title = if (isWin) "&a&lYOU WON!" else "&c&lYOU LOST!"
        val resultScreen = FancyCasinos.spiGUI.create(title.translate(), 5)

        val bgMaterial = if (isWin) Material.LIME_STAINED_GLASS_PANE else Material.RED_STAINED_GLASS_PANE
        for (i in 0 until 45) {
            val bgItem = ItemBuilder(bgMaterial)
                .name(" ")
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build()
            resultScreen.setButton(i, SGButton(bgItem))
        }

        val amountDiff = if (isWin) winnings.finalAmount else (betAmount - winnings.finalAmount)
        val resultMaterial = if (isWin) Material.EMERALD else Material.BARRIER
        val resultItem = ItemBuilder(resultMaterial)
            .name(if (isWin) "&a&l+$${amountDiff}" else "&c&l-$${amountDiff}")
            .lore(
                "",
                "&7You bet: &a&n$${betAmount}",
                "&7Final amount: &a&n$${winnings.finalAmount}",
                "",
                if (isWin) "&aWell Done!" else "&cBetter luck next time!"
            )
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .build()
        resultScreen.setButton(22, SGButton(resultItem))

        for (i in winnings.rolledBlocks.indices) {
            if (i >= 3) break

            val slotBlock = winnings.rolledBlocks[i]
            val material = nameToMaterial(slotBlock.name)

            val slotItem = ItemBuilder(material)
                .name("&e&l${slotBlock.name}")
                .lore(
                    "",
                    "&fMultiplier: &e${slotBlock.multiplier}x"
                )
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build()

            resultScreen.setButton(11 + i, SGButton(slotItem))
        }

        val multiplier = winnings.rolledBlocks.fold(1.0) { acc, block -> acc * block.multiplier }
        val multiplierItem = ItemBuilder(Material.EXPERIENCE_BOTTLE)
            .name("&e&lTotal Multiplier")
            .lore(
                "",
                "&fx${String.format("%.2f", multiplier)}"
            )
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .build()
        resultScreen.setButton(31, SGButton(multiplierItem))

        val playAgainItem = ItemBuilder(Material.EMERALD)
            .name("&a&lPLAY AGAIN")
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .build()
        val playAgainButton = SGButton(playAgainItem).withListener { event: InventoryClickEvent ->
            val user = event.whoClicked as Player
            user.closeInventory()

            MoneyUtil.typeInChat(user, "Slots Bet", 15L,
                Consumer<String> { input ->
                    val amount = input.toDoubleOrNull()
                    if (amount == null) {
                        user.sendMessage("&cInvalid amount. Please enter a number.".translate())
                        return@Consumer
                    } else {
                        user.sendMessage("&aYou have bet $amount.".translate())

                        val animationMenu = FancyCasinos.spiGUI.create("Slots", 5)
                        setupSlotMachine(animationMenu)
                        user.openInventory(animationMenu.inventory)

                        startSlotAnimation(user, animationMenu, amount)
                    }
                },
                Runnable {
                    user.sendMessage("&cBetting cancelled.".translate())
                }
            )
        }
        resultScreen.setButton(38, playAgainButton)

        val exitItem = ItemBuilder(Material.BARRIER)
            .name("&c&lEXIT")
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .build()
        val exitButton = SGButton(exitItem).withListener { event: InventoryClickEvent ->
            (event.whoClicked as Player).closeInventory()
        }
        resultScreen.setButton(42, exitButton)

        player.openInventory(resultScreen.inventory)
    }

    private fun nameToMaterial(blockName: String): Material {
        return when {
            blockName.contains("Gold Nugget", ignoreCase = true) -> Material.GOLD_NUGGET
            blockName.contains("Gold Ingot", ignoreCase = true) -> Material.GOLD_INGOT
            blockName.contains("Raw Gold", ignoreCase = true) && blockName.contains("Block", ignoreCase = true) -> Material.RAW_GOLD_BLOCK
            blockName.contains("Raw Gold", ignoreCase = true) -> Material.RAW_GOLD
            blockName.contains("Gold Block", ignoreCase = true) -> Material.GOLD_BLOCK
            else -> Material.BARRIER
        }
    }

    private fun materialToName(material: Material): String {
        return when (material) {
            Material.GOLD_NUGGET -> "Gold Nugget"
            Material.GOLD_INGOT -> "Gold Ingot"
            Material.RAW_GOLD -> "Raw Gold"
            Material.RAW_GOLD_BLOCK -> "Raw Gold Block"
            Material.GOLD_BLOCK -> "Gold Block"
            else -> "Unknown"
        }
    }
}