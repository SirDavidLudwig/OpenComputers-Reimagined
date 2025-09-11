package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.LuaValue;
import tech.dlii.opencomputers.OpenComputers;

public final class LuaJCeorcion {
    public static LuaValue toValue(Object value) {
        if (value == null) {
            return LuaValue.NIL;
        }

        // Boxed primitives
        if (value instanceof Boolean b) {
            return LuaValue.valueOf(b);
        }
        if (value instanceof Byte b) {
            return LuaValue.valueOf(b);
        }
        if (value instanceof Character c) {
            return LuaValue.valueOf(String.valueOf(c));
        }
        if (value instanceof Short s) {
            return LuaValue.valueOf(s);
        }
        if (value instanceof Integer i) {
            return LuaValue.valueOf(i);
        }
        if (value instanceof Long l) {
            return LuaValue.valueOf(l);
        }
        if (value instanceof Float f) {
            return LuaValue.valueOf(f);
        }
        if (value instanceof Double d) {
            return LuaValue.valueOf(d);
        }

        // Strings
        if (value instanceof String s) {
            return LuaValue.valueOf(s);
        }

        // Byte arrays
        if (value instanceof byte[] bytes) {
            return LuaValue.valueOf(bytes);
        }

        // Other arrays → convert to Lua list
//        if (value.getClass().isArray()) {
//            return toLuaList(java.util.Arrays.asList((Object[]) value));
//        }
//
//        // Maps → convert to Lua table
//        if (value instanceof java.util.Map<?, ?> map) {
//            return toLuaTable(map);
//        }
//
//        // Collections → convert to Lua list
//        if (value instanceof java.util.Collection<?> coll) {
//            return toLuaList(coll);
//        }

        // Userdata (if allowed)
//        if (Settings.get().allowUserdata()) {
//            return LuaValue.userdataOf(value);
//        }

        // Unsupported type
        OpenComputers.LOGGER.warn("Tried to push an unsupported value of type to Lua: " + value.getClass().getName() + ".");
        return LuaValue.NIL;
    }

    private LuaJCeorcion() {}
}
