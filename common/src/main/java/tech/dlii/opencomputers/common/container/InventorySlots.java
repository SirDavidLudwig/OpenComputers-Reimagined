package tech.dlii.opencomputers.common.container;

import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.Slot;

public final class InventorySlots {

//    private InventorySlots() {} // no instances
//
//    protected static InventorySlot slot(Slot slot, Tier tier) {
//        return new InventorySlot(slot, tier);
//    }
//
//    public static final InventorySlot[][] computer = new InventorySlot[][]{
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.ONE),
//                    slot(Slot.CARD, Tier.ONE),
//                    slot(Slot.MEMORY, Tier.ONE),
//                    slot(Slot.HDD, Tier.ONE),
//                    slot(Slot.CPU, Tier.ONE),
//                    slot(Slot.MEMORY, Tier.ONE),
//                    slot(Slot.EEPROM, Tier.ONE)
//            },
//
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.CARD, Tier.ONE),
//                    slot(Slot.MEMORY, Tier.TWO),
//                    slot(Slot.MEMORY, Tier.TWO),
//                    slot(Slot.HDD, Tier.TWO),
//                    slot(Slot.HDD, Tier.ONE),
//                    slot(Slot.CPU, Tier.TWO),
//                    slot(Slot.EEPROM, Tier.ANY)
//            },
//
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.TWO),
//                    slot(Slot.FLOPPY, Tier.ONE),
//                    slot(Slot.CPU, Tier.THREE),
//                    slot(Slot.EEPROM, Tier.ANY)
//            },
//
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.FLOPPY, Tier.ONE),
//                    slot(Slot.CPU, Tier.THREE),
//                    slot(Slot.EEPROM, Tier.ANY)
//            }
//    };
//
//    public static final InventorySlot[][] server = new InventorySlot[][]{
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.CPU, Tier.TWO),
//                    slot(Slot.COMPONENT_BUS, Tier.TWO),
//                    slot(Slot.MEMORY, Tier.TWO),
//                    slot(Slot.MEMORY, Tier.TWO),
//                    slot(Slot.HDD, Tier.TWO),
//                    slot(Slot.HDD, Tier.TWO),
//                    slot(Slot.EEPROM, Tier.ANY)
//            },
//
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.CPU, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.EEPROM, Tier.ANY)
//            },
//
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CPU, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.CARD, Tier.TWO),
//                    slot(Slot.EEPROM, Tier.ANY)
//            },
//
//            new InventorySlot[]{
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CPU, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.COMPONENT_BUS, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.MEMORY, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.HDD, Tier.THREE),
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.CARD, Tier.THREE),
//                    slot(Slot.EEPROM, Tier.ANY)
//            }
//    };
//
//    public static final InventorySlot[] relay = new InventorySlot[]{
//            slot(Slot.CPU, Tier.THREE),
//            slot(Slot.MEMORY, Tier.THREE),
//            slot(Slot.HDD, Tier.THREE),
//            slot(Slot.CARD, Tier.THREE)
//    };
//
//    public static final InventorySlot[] switchSlots = new InventorySlot[]{
//            slot(Slot.CPU, Tier.THREE),
//            slot(Slot.MEMORY, Tier.THREE),
//            slot(Slot.HDD, Tier.THREE)
//    };
//
//    // Record version
//    public record InventorySlot(Slot slot, Tier tier) {}
}

