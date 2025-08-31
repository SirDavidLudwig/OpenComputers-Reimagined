package tech.dlii.opencomputers.integration.opencomputers;

import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.integration.Mod;

public class ModOpenComputers extends Mod {

    public static final String ID = "opencomputers";

    public ModOpenComputers() {
        super(ID, "0.0");
    }

    @Override
    public void initialize() {
        API.driver.add(new DriverCPU());
    }
}
