package tech.dlii.opencomputers.common.inventory;

import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.SlotType;

import java.util.*;

public final class InventorySlots {

    public record InventorySlot(int index, String type, int tier) {}

    public static final Map<Integer, InventorySlots> CASE = Map.of(
            // Case (Tier 1)
            Tier.ONE, new InventorySlots.Builder()
                    .put(SlotType.CARD, Tier.ONE, Tier.ONE)
                    .put(SlotType.CPU, Tier.ONE)
                    .put(SlotType.EEPROM, Tier.ANY)
                    .put(SlotType.STORAGE, Tier.ONE)
                    .put(SlotType.MEMORY, Tier.ONE, Tier.ONE)
                    .build(),

            // Case (Tier 2)
            Tier.TWO, new InventorySlots.Builder()
                    .put(SlotType.CARD, Tier.TWO, Tier.ONE)
                    .put(SlotType.CPU, Tier.TWO)
                    .put(SlotType.EEPROM, Tier.ANY)
                    .put(SlotType.STORAGE, Tier.TWO, Tier.ONE)
                    .put(SlotType.MEMORY, Tier.TWO, Tier.TWO)
                    .build(),

            // Case (Tier 3)
            Tier.THREE, new InventorySlots.Builder()
                    .put(SlotType.CARD, Tier.THREE, Tier.TWO, Tier.TWO)
                    .put(SlotType.CPU, Tier.THREE)
                    .put(SlotType.EEPROM, Tier.ANY)
                    .put(SlotType.FLOPPY, Tier.ONE)
                    .put(SlotType.STORAGE, Tier.THREE, Tier.TWO)
                    .put(SlotType.MEMORY, Tier.THREE, Tier.THREE)
                    .build(),

            // Case (Creative)
            Tier.FOUR, new InventorySlots.Builder()
                    .put(SlotType.CARD, Tier.THREE, Tier.THREE, Tier.THREE)
                    .put(SlotType.CPU, Tier.THREE)
                    .put(SlotType.EEPROM, Tier.ANY)
                    .put(SlotType.FLOPPY, Tier.ONE)
                    .put(SlotType.STORAGE, Tier.THREE, Tier.THREE)
                    .put(SlotType.MEMORY, Tier.THREE, Tier.THREE)
                    .build()
    );

    private final List<InventorySlot> slotsByIndex;
    private final Map<String, List<InventorySlot>> slotsByType;
    private static final List<InventorySlot> EMPTY = Collections.unmodifiableList(new ArrayList<>());

    private InventorySlots(Map<String, List<Integer>> slots) {
        List<InventorySlot> slotsByIndex = new ArrayList<>();
        Map<String, List<InventorySlot>> slotsByType = new HashMap<>();

        for (Map.Entry<String, List<Integer>> entry : slots.entrySet()) {
            String type = entry.getKey();
            List<Integer> tiers = entry.getValue();
            if (!slotsByType.containsKey(type)) {
                slotsByType.put(type, new ArrayList<>());
            }
            for (int tier : tiers) {
                InventorySlot slot = new InventorySlot(slotsByIndex.size(), type, tier);
                slotsByIndex.add(slot);
                slotsByType.get(type).add(slot);
            }
            slotsByType.put(type, Collections.unmodifiableList(slotsByType.get(type)));
        }
        this.slotsByIndex = Collections.unmodifiableList(slotsByIndex);
        this.slotsByType = Collections.unmodifiableMap(slotsByType);
    }

    public List<InventorySlot> asList() {
        return slotsByIndex;
    }

    public InventorySlot get(int index) {
        return slotsByIndex.get(index);
    }

    public List<InventorySlot> get(String slotType) {
        return slotsByType.getOrDefault(slotType, EMPTY);
    }

    public int size() {
        return slotsByIndex.size();
    }

    protected static class Builder {
        private final Map<String, List<Integer>> slots = new HashMap<>();

        public Builder put(String key, int... values) {
            if (!slots.containsKey(key)) {
                slots.put(key, new ArrayList<>());
            }
            List<Integer> valueList = slots.get(key);;
            for (int v : values) {
                valueList.add(v);
            }
            return this;
        }

        public InventorySlots build() {
            return new InventorySlots(slots);
        }
    }
}

