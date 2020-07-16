package li.cil.oc.api;

/**
 * Allows access to the creative tab used by OpenComputers.
 */
public final class ItemGroup {
    /**
     * The creative tab used by OpenComputers.
     * <p/>
     * Changed to the actual tab if OC is present. Preferably you do
     * <em>not</em> try to access this anyway when OpenComputers isn't
     * present (don't ship the API in your mod), so don't rely on this!
     */
    public static net.minecraft.item.ItemGroup instance = net.minecraft.item.ItemGroup.REDSTONE;

    private ItemGroup() {
    }
}
