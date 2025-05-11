package studio.awel.FancyCasinos.slots;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Machine {
    private final List<SlotBlock> blocks = List.of(
            new SlotBlock("Gold Nugget", 0.5),
            new SlotBlock("Gold Ingot", 1.0),
            new SlotBlock("Raw Gold", 1.5),
            new SlotBlock("Raw Gold Block", 2.5),
            new SlotBlock("Gold Block", 5.0)
    );

    private final Random random = new Random();

    public static class SpinResult {
        private final List<SlotBlock> rolledBlocks;
        private final double finalAmount;

        public SpinResult(List<SlotBlock> rolledBlocks, double finalAmount) {
            this.rolledBlocks = rolledBlocks;
            this.finalAmount = finalAmount;
        }

        public List<SlotBlock> getRolledBlocks() {
            return rolledBlocks;
        }

        public double getFinalAmount() {
            return finalAmount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SpinResult that = (SpinResult) o;
            return Double.compare(finalAmount, that.finalAmount) == 0 &&
                    Objects.equals(rolledBlocks, that.rolledBlocks);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rolledBlocks, finalAmount);
        }

        @Override
        public String toString() {
            return "SpinResult{" +
                    "rolledBlocks=" + rolledBlocks +
                    ", finalAmount=" + finalAmount +
                    '}';
        }
    }

    public SpinResult spin(double bet) {
        List<SlotBlock> rolled = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            rolled.add(blocks.get(random.nextInt(blocks.size())));
        }

        double combinedMultiplier = 1.0;
        for (SlotBlock block : rolled) {
            combinedMultiplier *= block.getMultiplier();
        }

        double result = bet * combinedMultiplier;

        return new SpinResult(rolled, result);
    }
}