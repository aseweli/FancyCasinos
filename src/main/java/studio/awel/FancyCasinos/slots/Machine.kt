package studio.awel.FancyCasinos.slots

class Machine {
    private val blocks = listOf(
        SlotBlock("Gold Nugget", 0.5),
        SlotBlock("Gold Ingot", 1.0),
        SlotBlock("Raw Gold", 1.5),
        SlotBlock("Raw Gold Block", 2.5),
        SlotBlock("Gold Block", 5.0)
    )

    data class SpinResult(
        val rolledBlocks: List<SlotBlock>,
        val finalAmount: Double
    )

    fun spin(bet: Double): SpinResult {
        val rolled = List(3) { blocks.random() }

        val combinedMultiplier = rolled.fold(1.0) { acc, block -> acc * block.multiplier }
        val result = bet * combinedMultiplier

        return SpinResult(rolled, result)
    }
}