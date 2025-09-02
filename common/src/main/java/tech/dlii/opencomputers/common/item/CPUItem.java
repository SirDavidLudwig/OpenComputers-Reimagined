package tech.dlii.opencomputers.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.driver.item.DriverItem;
import tech.dlii.opencomputers.api.driver.item.MutableControlProcessingUnit;
import tech.dlii.opencomputers.api.driver.item.SlotType;
import tech.dlii.opencomputers.api.machine.Architecture;
import tech.dlii.opencomputers.common.component.ArchitectureComponent;
import tech.dlii.opencomputers.common.component.DataComponents;

import java.util.List;

public class CPUItem extends ComputerComponentItem {
    public CPUItem(int tier, Properties properties) {
        super(SlotType.CPU, tier, properties.component(DataComponents.ARCHITECTURE.get(), ArchitectureComponent.fromArchitecture(API.machine.getDefaultArchitecture())));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!player.isCrouching() || level.isClientSide()) {
            return InteractionResult.PASS;
        }
        DriverItem driver = API.driver.driverFor(player.getItemInHand(interactionHand));
        if (!(driver instanceof MutableControlProcessingUnit cpuDriver)) {
            return InteractionResult.PASS;
        }
        List<Class<? extends Architecture>> architectures = cpuDriver.allArchitectures().stream().toList();
        int currentIndex = architectures.indexOf(cpuDriver.architecture(itemStack));
        Class<? extends Architecture> architectureClass = architectures.get((currentIndex + 1) % architectures.size());
        String architectureName = API.machine.getArchitectureName(architectureClass);
        cpuDriver.setArchitecture(itemStack, architectureClass);
        player.displayClientMessage(Component.translatable("tooltip.cpu.architecture." + architectureName), true);
        return InteractionResult.SUCCESS;
    }
}
