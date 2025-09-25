package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.LuaTable;
import li.cil.repack.org.luaj.vm2.LuaValue;
import li.cil.repack.org.luaj.vm2.Varargs;
import li.cil.repack.org.luaj.vm2.lib.VarArgFunction;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.machine.architecture.Callback;
import tech.dlii.opencomputers.api.machine.architecture.Value;

import java.util.Map;

public class UserdataAPI extends LuaJAPI {

    public UserdataAPI(LuaJLuaArchitecture owner) {
        super(owner);
    }

    @Override
    public void initialize() {
        final LuaTable userdata = LuaValue.tableOf();

        userdata.set("apply", new VarArgFunction() {
            @Override public Varargs invoke(Varargs args) {
                Value value = (Value) args.checkuserdata(1, Value.class);
                Object[] params = LuaJCoercion.toSimpleObjects(args, 2);
                // Registry.convert(...) should return a LuaValue/Varargs; wrap single result in an array like Scala did.
                return owner().invoke(() ->
                        API.driver.convert(new Object[] { value.apply(machine(), new LuaJArguments(params)) })
                );
            }
        });

        userdata.set("unapply", new VarArgFunction() {
            @Override public Varargs invoke(Varargs args) {
                Value value = (Value) args.checkuserdata(1, Value.class);
                Object[] params = LuaJCoercion.toSimpleObjects(args, 2);
                // If owner.invoke expects null to mean "no return", keep this behavior.
                return owner().invoke(() -> {
                    value.unapply(machine(), new LuaJArguments(params));
                    return null;
                });
            }
        });

        userdata.set("call", new VarArgFunction() {
            @Override public Varargs invoke(Varargs args) {
                Value value = (Value) args.checkuserdata(1, Value.class);
                Object[] params = LuaJCoercion.toSimpleObjects(args, 2);
                return owner().invoke(() ->
                        API.driver.convert(value.call(machine(), new LuaJArguments(params)))
                );
            }
        });

        userdata.set("dispose", new VarArgFunction() {
            @Override public Varargs invoke(Varargs args) {
                Value value = (Value) args.checkuserdata(1, Value.class);
                try {
                    value.dispose(machine());
                } catch (Throwable t) {
                    OpenComputers.LOGGER.warn(
                            "Error in dispose method of userdata of type " + value.getClass().getName(), t);
                }
                return LuaValue.NIL;
            }
        });

        userdata.set("methods", new VarArgFunction() {
            @Override public Varargs invoke(Varargs args) {
                Value value = (Value) args.checkuserdata(1, Value.class);
                Map<String, Callback> methods = machine().methods(value);
                LuaValue[] flat = new LuaValue[methods.size() * 2];
                int i = 0;
                for (Map.Entry<String, Callback> e : methods.entrySet()) {
                    flat[i++] = LuaValue.valueOf(e.getKey());
                    flat[i++] = LuaValue.valueOf(e.getValue().async());
                }
                return LuaValue.tableOf(flat);
            }
        });

        userdata.set("invoke", new VarArgFunction() {
            @Override public Varargs invoke(Varargs args) {
                Value value  = (Value) args.checkuserdata(1, Value.class);
                String method = args.checkjstring(2);
                Object[] params = LuaJCoercion.toSimpleObjects(args, 3);
                return owner().invoke(() -> machine().invoke(value, method, params));
            }
        });

        userdata.set("doc", new VarArgFunction() {
            @Override public Varargs invoke(Varargs args) {
                Value value  = (Value) args.checkuserdata(1, Value.class);
                String method = args.checkjstring(2);
                return owner().documentation(() -> {
                    Map<String, Callback> m = machine().methods(value);
                    Callback a = m.get(method);
                    return (a != null) ? a.doc() : null;
                });
            }
        });

        lua().set("userdata", userdata);
    }
}
