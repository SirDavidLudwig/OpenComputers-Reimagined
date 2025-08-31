package tech.dlii.opencomputers.integration;

public class Mod {

    private final String id;
    private final String version;

    public Mod(String id, String version) {
        this.id = id;
        this.version = version;
    }

    public void initialize() {

    }

    public String id() {
        return this.id;
    }

    public String version() {
        return this.version;
    }

    boolean isAvailable() {
        // @TODO
        return true;
    }
}
