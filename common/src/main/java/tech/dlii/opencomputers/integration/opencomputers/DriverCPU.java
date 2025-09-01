package tech.dlii.opencomputers.integration.opencomputers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.Tier;
import tech.dlii.opencomputers.api.driver.item.CallBudget;
import tech.dlii.opencomputers.api.driver.item.MutableControlProcessingUnit;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.common.item.CPU;
import tech.dlii.opencomputers.common.item.Items;
import tech.dlii.opencomputers.config.Configuration;

import java.util.Collection;
import java.util.List;

public class DriverCPU extends OpenComputersItem implements MutableControlProcessingUnit, CallBudget {

    private final List<Item> COMPATIBLE_ITEMS;

    public DriverCPU() {
        this.COMPATIBLE_ITEMS = List.of(
                Items.CPU_TIER_1.get(),
                Items.CPU_TIER_2.get(),
                Items.CPU_TIER_3.get()
        );
    }

    @Override
    public double getCallBudget(ItemStack stack) {
        return Configuration.CALL_BUDGETS[tier(stack)];
    }

    @Override
    public Collection<Class<? extends Architecture>> allArchitectures() {
        return API.machine.architectures();
    }

    @Override
    public void setArchitecture(ItemStack stack, Class<? extends Architecture> architecture) {
        if (!worksWith(stack)) {
            throw new IllegalArgumentException("Unsupported CPU type.");
        }

    }

    @Override
    public int supportedComponents(ItemStack stack) {
        return Configuration.CPU_COMPONENT_COUNT[tier(stack)];
    }

    @Override
    public Class<? extends Architecture> architecture(ItemStack stack) {
        return API.machine.architectures().stream().findFirst().orElse(null);
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        return COMPATIBLE_ITEMS.contains(stack.getItem());
    }

    @Override
    public String slot(ItemStack stack) {
        return SlotType.CPU;
    }

    @Override
    public int tier(ItemStack stack) {
        if (stack.getItem() instanceof CPU cpu) {
            return cpu.tier();
        }
        return Tier.ONE;
    }

    @Override
    public CompoundTag dataTag(ItemStack stack) {
        return super.dataTag(stack);
    }
}
