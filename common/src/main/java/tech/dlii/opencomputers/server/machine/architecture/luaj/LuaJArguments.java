package tech.dlii.opencomputers.server.machine.architecture.luaj;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

// This interfaces with LuaJ Varargs
public class LuaJArguments implements tech.dlii.opencomputers.api.machine.architecture.Arguments {

    private Object[] args;

    public LuaJArguments(Object[] args) {
        this.args = args;
    }

    @Override
    public int count() {
        return args.length;
    }

    @Override
    public Object checkAny(int index) {
        assertValidIndex(index, "value");
        return switch (args[index]) {
            // check unit
            default -> args[index];
        };
    }

    @Override
    public boolean checkBoolean(int index) {
        assertValidIndex(index, "boolean");
        return switch (args[index]) {
            case Boolean value -> value;
            default -> throw typeError(index, args[index], "boolean");
        };
    }

    @Override
    public int checkInteger(int index) {
        assertValidIndex(index, "number");
        return switch (args[index]) {
            // TODO: The below is correct behaviour, but breaks existing OC1 code (f.e. file:read(math.huge))
          /* case value: java.lang.Double =>
            if (!java.lang.Double.isFinite(value) || value < java.lang.Integer.MIN_VALUE || value > java.lang.Integer.MAX_VALUE) {
              throw intError(index, value)
            } else {
              value.intValue
            }
          case value: java.lang.Float =>
            if (!java.lang.Float.isFinite(value) || value < java.lang.Integer.MIN_VALUE || value > java.lang.Integer.MAX_VALUE) {
              throw intError(index, value)
            } else {
              value.intValue
            }
          case value: java.lang.Long =>
            if (value < java.lang.Integer.MIN_VALUE || value > java.lang.Integer.MAX_VALUE) {
              throw intError(index, value)
            } else {
              value.intValue
            }
          case value: java.lang.Number => value.intValue
          */
            case Double value -> {
                if (value.isNaN()) {
                    throw intError(index, value);
                }
                if (value > Integer.MAX_VALUE) {
                    yield Integer.MAX_VALUE;
                }
                if (value < Integer.MIN_VALUE) {
                    yield Integer.MIN_VALUE;
                }
                yield value.intValue();
            }
            case Float value -> {
                if (value.isNaN()) {
                    throw intError(index, value);
                }
                if (value > Integer.MAX_VALUE) {
                    yield Integer.MAX_VALUE;
                }
                if (value < Integer.MIN_VALUE) {
                    yield Integer.MIN_VALUE;
                }
                yield value.intValue();
            }
            case Number value -> value.intValue();
            default -> throw typeError(index, args[index], "number");
        };
    }

    @Override
    public long checkLong(int index) {
        assertValidIndex(index, "number");
        return switch (args[index]) {
            // TODO: The below is correct behaviour, but breaks existing OC1 code (f.e. file:read(math.huge))
          /* case value: java.lang.Double =>
            if (!java.lang.Double.isFinite(value) || value < java.lang.Long.MIN_VALUE || value > java.lang.Long.MAX_VALUE) {
              throw intError(index, value)
            } else {
              value.longValue
            }
          case value: java.lang.Float =>
            if (!java.lang.Float.isFinite(value) || value < java.lang.Long.MIN_VALUE || value > java.lang.Long.MAX_VALUE) {
              throw intError(index, value)
            } else {
              value.longValue
            }
          case value: java.lang.Number => value.longValue
          */
            case Double value -> {
                if (value.isNaN()) {
                    throw intError(index, value);
                }
                if (value > Long.MAX_VALUE) {
                    yield Long.MAX_VALUE;
                }
                if (value < Long.MIN_VALUE) {
                    yield Long.MIN_VALUE;
                }
                yield value.longValue();
            }
            case Float value -> {
                if (value.isNaN()) {
                    throw intError(index, value);
                }
                if (value > Long.MAX_VALUE) {
                    yield Long.MAX_VALUE;
                }
                if (value < Long.MIN_VALUE) {
                    yield Long.MIN_VALUE;
                }
                yield value.longValue();
            }
            case Number value -> value.longValue();
            default -> throw typeError(index, args[index], "number");
        };
    }

    @Override
    public double checkDouble(int index) {
        assertValidIndex(index, "number");
        return switch (args[index]) {
            case Number value -> value.doubleValue();
            default -> throw typeError(index, args[index], "number");
        };
    }

    @Override
    public String checkString(int index) {
        assertValidIndex(index, "string");
        return switch (args[index]) {
            case String value -> value;
            case byte[] value -> new String(value, StandardCharsets.UTF_8);
            default -> throw typeError(index, args[index], "string");
        };
    }

    @Override
    public byte[] checkByteArray(int index) {
        assertValidIndex(index, "string");
        return switch (args[index]) {
            case String value -> value.getBytes(StandardCharsets.UTF_8);
            case byte[] value -> value;
            default -> throw typeError(index, args[index], "string");
        };
    }

    @Override
    public Map checkTable(int index) {
        assertValidIndex(index, "table");
        return switch (args[index]) {
            case Map<?, ?> value -> value;
            default -> throw typeError(index, args[index], "table");
        };
    }

    @Override
    public ItemStack checkItemStack(int index) {
        Map<String, ?> map = checkTable(index);
        return switch (map.get("name")) {
            // @TODO logic needs to change due to item component changes.
            case String value -> ItemStack.EMPTY;
            default -> throw new IllegalArgumentException("Invalid stack item");
        };
    }

    @Override
    public Object optAny(int index, Object def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkAny(index);
    }

    @Override
    public boolean optBoolean(int index, boolean def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkBoolean(index);
    }

    @Override
    public int optInteger(int index, int def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkInteger(index);
    }

    @Override
    public long optLong(int index, long def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkLong(index);
    }

    @Override
    public double optDouble(int index, double def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkDouble(index);
    }

    @Override
    public String optString(int index, String def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkString(index);
    }

    @Override
    public byte[] optByteArray(int index, byte[] def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkByteArray(index);
    }

    @Override
    public Map optTable(int index, Map def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkTable(index);
    }

    @Override
    public ItemStack optItemStack(int index, ItemStack def) {
        if (!isDefined(index)) {
            return def;
        }
        return checkItemStack(index);
    }

    @Override
    public boolean isBoolean(int index) {
        return switch (args[index]) {
            case Boolean value -> true;
            default -> false;
        };
    }

    @Override
    public boolean isInteger(int index) {
        return switch (args[index]) {
//             TODO: The below is correct behaviour, but may break existing OC1 code
//            case Double value -> value.isFinite() && value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE;
//            case Float value -> value.isFinite() && value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE;
//            case Long value -> value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE;
            case Double value -> !value.isNaN();
            case Float value -> !value.isNaN();
            case Number value -> true;
            default -> false;
        };
    }

    @Override
    public boolean isLong(int index) {
        return switch (args[index]) {
//             TODO: The below is correct behaviour, but may break existing OC1 code
//            case Double value -> value.isFinite() && value >= Long.MIN_VALUE && value <= Long.MAX_VALUE;
//            case Float value -> value.isFinite() && value >= Long.MIN_VALUE && value <= Long.MAX_VALUE;
            case Double value -> !value.isNaN();
            case Float value -> !value.isNaN();
            case Number value -> true;
            default -> false;
        };
    }

    @Override
    public boolean isDouble(int index) {
        return switch (args[index]) {
            case Number value -> true;
            default -> false;
        };
    }

    @Override
    public boolean isString(int index) {
        return switch (args[index]) {
            case String value -> true;
            default -> false;
        };
    }

    @Override
    public boolean isByteArray(int index) {
        return switch (args[index]) {
            case String value -> true;
            case byte[] value -> true;
            default -> false;
        };
    }

    @Override
    public boolean isTable(int index) {
        return switch (args[index]) {
            case Map<?, ?> value -> true;
            default -> false;
        };
    }

    @Override
    public boolean isItemStack(int index) {
        // @TODO This seems like a really weak check...
        if (!isTable(index)) {
            return false;
        }
        return switch (checkTable(index).get("name")) {
            case String value -> true;
            case byte[] value -> true;
            default -> false;
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(args)
                .map(obj -> {
                    if (obj instanceof byte[] objByteArray) {
                        return new String(objByteArray, StandardCharsets.UTF_8);
                    }
                    if (obj instanceof Byte[] objBoxedByteArray) { // optional: also handle boxed bytes
                        byte[] unboxed = new byte[objBoxedByteArray.length];
                        for (int i = 0; i < objBoxedByteArray.length; i++) unboxed[i] = objBoxedByteArray[i];
                        return new String(unboxed, StandardCharsets.UTF_8);
                    }
                    return obj;
                })
                .toArray(Object[]::new);
    }

    @Override
    public @NotNull Iterator<Object> iterator() {
        return Arrays.stream(args).iterator();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private boolean isDefined(int index) {
        return index >= 0 && index < args.length && args[index] != null;
    }

    private void assertValidIndex(int index, String name) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index >= args.length) {
            throw new IllegalArgumentException("Bad arguments #" + (index + 1) + " (" + name + " expected, got no value)");
        }
    }

    private IllegalArgumentException intError(int index, Object have) {
        return new IllegalArgumentException("Bad argument #" + (index + 1) + " (" + have + " has no integer representation)");
    }

    private IllegalArgumentException typeError(int index, Object have, String want) {
        return new IllegalArgumentException("Bad argument #" + (index + 1) + " (" + want + " expected, got " + have + ")");
    }

    private String typeName(Object obj) {
        return switch (obj) {
            case null -> "nil";
            case Boolean value -> "boolean";
            case Byte value -> "integer";
            case Short value -> "integer";
            case Integer value -> "integer";
            case Long value -> "integer";
            case Float value -> "number";
            case Double value -> "number";
            case String value -> "string";
            case byte[] value -> "string";
            case Map<?, ?> value -> "table";
            default -> obj.getClass().getSimpleName();
        };
    }
}
