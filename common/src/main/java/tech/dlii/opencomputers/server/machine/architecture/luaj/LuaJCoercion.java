package tech.dlii.opencomputers.server.machine.architecture.luaj;

import li.cil.repack.org.luaj.vm2.LuaString;
import li.cil.repack.org.luaj.vm2.LuaTable;
import li.cil.repack.org.luaj.vm2.LuaValue;
import li.cil.repack.org.luaj.vm2.Varargs;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.common.config.Configuration;

import java.util.*;

public final class LuaJCoercion {
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
        if (value instanceof String s) {
            return LuaValue.valueOf(s);
        }
        if (value instanceof byte[] bytes) {
            return LuaValue.valueOf(bytes);
        }
        // Other arrays → convert to Lua list
        if (value.getClass().isArray()) {
            return toList(java.util.Arrays.asList((Object[]) value));
        }
        // Maps → convert to Lua table
        if (value instanceof java.util.Map<?, ?> map) {
            return toTable(map);
        }
        // Collections → convert to Lua list
        if (value instanceof java.util.Collection<?> coll) {
            return toList(coll);
        }
        // Userdata (if allowed)
        if (Configuration.ALLOW_USER_DATA) {
            return LuaValue.userdataOf(value);
        }
        // Unsupported type
        OpenComputers.LOGGER.warn("Tried to push an unsupported value of type to Lua: " + value.getClass().getName() + ".");
        return LuaValue.NIL;
    }

    public static LuaTable toList(Iterable<?> value) {
        List<LuaValue> out = new ArrayList<>();
        for (Object obj : value) {
            out.add(toValue(obj));
        }
        return LuaValue.listOf(out.toArray(new LuaValue[0]));
    }

    public static LuaTable toTable(Map<?, ?> value) {
        LuaValue[] flat = new LuaValue[value.size() * 2];
        int i = 0;
        for (Map.Entry<?, ?> e : value.entrySet()) {
            flat[i++] = toValue(e.getKey());
            flat[i++] = toValue(e.getValue());
        }
        return LuaValue.tableOf(flat);
    }

    public static Object toSimpleJavaObject(LuaValue value) {
        return switch (value.type()) {
            case LuaValue.TBOOLEAN -> value.toboolean();
            case LuaValue.TNUMBER -> value.todouble();
            case LuaValue.TSTRING -> {
                // Prefer raw bytes if possible (to match the Scala slice behavior),
                // otherwise fall back to Java String.
                if (value instanceof LuaString) {
                    LuaString s = (LuaString) value;
                    try {
                        // LuaJ exposes these fields in many versions; if not, the catch will handle it.
                        byte[] copy = new byte[s.m_length];
                        System.arraycopy(s.m_bytes, s.m_offset, copy, 0, s.m_length);
                        yield copy;
                    } catch (Throwable ignored) {}
                }
                yield value.tojstring();
            }
            case LuaValue.TTABLE -> Arrays.stream(value.checktable().keys()).collect(
                        LinkedHashMap::new,
                        (m, key) -> m.put(
                                toSimpleJavaObject(key),
                                toSimpleJavaObject(value.checktable().get(key))
                        ),
                        Map::putAll
                );
            case LuaValue.TUSERDATA -> value.touserdata();
            default -> null;
        };
    }

    public static Object[] toSimpleObjects(Varargs args) {
        return toSimpleObjects(args, 1);
    }

    public static Object[] toSimpleObjects(Varargs args, int start) {
        Object[] result = new Object[args.narg() - start];
        for (int i = 0, argIndex = start; argIndex < args.narg(); i++, argIndex++) {
            result[i] = toSimpleJavaObject(args.arg(argIndex));
        }
        return result;
    }

    private LuaJCoercion() {}
}
