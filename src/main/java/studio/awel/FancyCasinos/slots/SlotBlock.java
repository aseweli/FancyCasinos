package studio.awel.FancyCasinos.slots;

import java.util.Objects;

public class SlotBlock {
    private final String name;
    private final double multiplier;

    public SlotBlock(String name, double multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotBlock slotBlock = (SlotBlock) o;
        return Double.compare(slotBlock.multiplier, multiplier) == 0 &&
                Objects.equals(name, slotBlock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, multiplier);
    }

    @Override
    public String toString() {
        return "SlotBlock{" +
                "name='" + name + '\'' +
                ", multiplier=" + multiplier +
                '}';
    }
}