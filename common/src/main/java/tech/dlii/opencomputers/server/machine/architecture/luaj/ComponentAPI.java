package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.LuaTable;
import li.cil.repack.org.luaj.vm2.LuaValue;
import li.cil.repack.org.luaj.vm2.Varargs;
import li.cil.repack.org.luaj.vm2.lib.VarArgFunction;
import tech.dlii.opencomputers.OpenComputers;

import java.util.Map;
import java.util.Optional;

public class ComponentAPI extends LuaJAPI {
    public ComponentAPI(LuaJLuaArchitecture owner) {
        super(owner);
    }

    @Override
    public void initialize() {
        LuaValue component = LuaValue.tableOf();

        component.set("list", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                synchronized (components()) {
                    java.util.Optional<String> filter = args.isstring(1)
                            ? java.util.Optional.of(args.tojstring(1)) : java.util.Optional.empty();
                    boolean exact = args.optboolean(2, false);

                    LuaTable table = LuaValue.tableOf(0, components().size());
                    for (java.util.Map.Entry<String,String> e : components().entrySet()) {
                        String address = e.getKey();
                        String name    = e.getValue();
                        boolean match = !filter.isPresent()
                                || (exact ? name.equals(filter.get()) : name.contains(filter.get()));
                        if (match) {
                            table.set(LuaValue.valueOf(address), LuaValue.valueOf(name));
                        }
                    }
                    return table;
                }
            }
        });

        component.set("invoke", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                String address = args.checkjstring(1);
                String method = args.checkjstring(2);
                Object[] params = LuaJCoercion.toSimpleObjects(args, 3);
                return owner().invoke(() -> machine().invoke(address, method, params));
            }
        });

        lua().set("component", component);
    }
}
