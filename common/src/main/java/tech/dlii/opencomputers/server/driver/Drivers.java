package tech.dlii.opencomputers.server.driver;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.api.API;
import tech.dlii.opencomputers.api.driver.Converter;
import tech.dlii.opencomputers.api.driver.DriverAPI;
import tech.dlii.opencomputers.api.driver.item.DriverItem;
import tech.dlii.opencomputers.api.machine.architecture.Value;

import java.util.*;

public class Drivers implements DriverAPI {

    private boolean locked = false;
    private ArrayList<DriverItem> driverItems = new ArrayList<>();

    public static void initialize() {
        API.driver = new Drivers();
        API.driver.register(new CPUDriver());
        API.driver.register(new EEPROMDriver());
        API.driver.register(new GPUDriver());
        API.driver.register(new FileSystemDriver());
        API.driver.register(new MemoryDriver());
    }

    @Override
    public void register(DriverItem driver) {
        assertNotLocked();
        driverItems.add(driver);
    }

    @Override
    public @Nullable DriverItem driverFor(ItemStack stack) {
        return driverItems.stream().filter(item -> item.worksWith(stack)).findAny().orElse(null);
    }

    protected void assertNotLocked() {
        if (locked) {
            throw new IllegalStateException("All drivers must be registered in the 'init' phase.");
        }
    }

    // Converters ------------------------------------------------------------------------------------------------------
    // @TODO This stuff needs refactoring and should be moved to a more appropriate place.

    private static final String FLATTEN_KEY = "opencomputers:flatten";
    private final List<Converter> converters =new ArrayList<>();

    public void register(Converter converter) {
        assertNotLocked();
        converters.add(converter);
    }

    @Override
    public Object[] convert(Object[] value) {
        if (value == null) return null;
        Object[] out = new Object[value.length];
        for (int i = 0; i < value.length; i++) {
            out[i] = convertRecursively(value[i], new IdentityHashMap<>(), false);
        }
        return out;
    }

    /** Convenience overload with force=false. */
    public Object convertRecursively(Object value, IdentityHashMap<Object, Object> memo) {
        return convertRecursively(value, memo, false);
    }

    /**
     * Scala: def convertRecursively(value: Any, memo: IdentityHashMap[Any,AnyRef], force:Boolean=false): AnyRef
     */
    @SuppressWarnings("unchecked")
    public Object convertRecursively(Object value, IdentityHashMap<Object, Object> memo, boolean force) {
        // In Scala version, ScalaNumber.underlying, Unit/None cases existed. We drop Scala-specifics.
        final Object ref = value; // no ScalaNumber/Option/Unit in Java version

        if (!force && ref != null && memo.containsKey(ref)) {
            return memo.get(ref);
        }

        // null and special cases first
        if (ref == null) {
            return null;
        }

        // Pass through common boxed primitives and String
        if (ref instanceof Boolean
                || ref instanceof Byte
                || ref instanceof Character
                || ref instanceof Short
                || ref instanceof Integer
                || ref instanceof Long
                || ref instanceof Float
                || ref instanceof Double
                || ref instanceof String) {
            return ref;
        }

        // Any other Number → Double (matches original "case Number => Double.box(...)")
        if (ref instanceof Number) {
            return ((Number) ref).doubleValue();
        }

        // Preserve primitive arrays as-is
        if (ref instanceof boolean[] || ref instanceof byte[] || ref instanceof char[]
                || ref instanceof short[] || ref instanceof int[] || ref instanceof long[]
                || ref instanceof float[] || ref instanceof double[]) {
            return ref;
        }

        // Preserve common boxed/String arrays as-is
        if (ref instanceof Boolean[] || ref instanceof Byte[] || ref instanceof Character[]
                || ref instanceof Short[] || ref instanceof Integer[] || ref instanceof Long[]
                || ref instanceof Float[] || ref instanceof Double[] || ref instanceof String[]) {
            return ref;
        }

        // If you have a special interface you want to pass through untouched (like the Scala `Value`)
        // just add it here. Replace MyValue with your actual type, or remove this if not needed.
        if (ref instanceof Value) { // <-- replace or remove
            return ref;
        }

        // Object[] (generic reference array) → convert each element
        if (ref instanceof Object[]) {
            return convertList(ref, Arrays.asList((Object[]) ref).iterator(), memo);
        }

        // Java Map → convert keys & values
        if (ref instanceof Map<?, ?>) {
            return convertMap(ref, (Map<?, ?>) ref, memo);
        }

        // Java Iterable → convert each element
        if (ref instanceof Iterable<?>) {
            return convertList(ref, ((Iterable<?>) ref).iterator(), memo);
        }

        // Fallback: ask plugin converters to populate a map with structured data
        Map<Object, Object> converted = new LinkedHashMap<>();
        memo.put(ref, converted); // memoize early for cycles

        for (Converter c : converters) {
            try {
                c.convert(ref, converted);
            } catch (Throwable t) {
                OpenComputers.LOGGER.warn("Type converter threw an exception for " + ref.getClass().getName() + ": " + t);
            }
        }

        if (converted.isEmpty()) {
            String s = String.valueOf(ref);
            memo.put(ref, s);
            return s;
        } else {
            // We must convert the entries of `converted` recursively.
            // Memoize the map to itself so recursive references resolve to this same instance.
            memo.put(converted, converted);
            convertRecursively(converted, memo, true); // converts the entries into canonical Java types
            memo.remove(converted);

            // Handle the "oc:flatten" sentinel: if exactly one entry with that key, return the value.
            if (converted.size() == 1 && converted.containsKey(FLATTEN_KEY)) {
                Object flattened = converted.get(FLATTEN_KEY);
                memo.put(ref, flattened); // update memo for subsequent hits
                return flattened;
            }
            return converted;
        }
    }

    /**
     * Scala: def convertList(obj: Any, list: Iterator[(Any,Int)], memo: IdentityHashMap): Array[AnyRef]
     * We memoize `obj -> ArrayList` during conversion to support cyclic graphs, then return a new Object[].
     */
    private Object[] convertList(Object obj, Iterator<?> it, IdentityHashMap<Object, Object> memo) {
        List<Object> out = new ArrayList<>();
        memo.put(obj, out); // memoize early (the list will be populated incrementally)
        while (it.hasNext()) {
            Object v = it.next();
            out.add(convertRecursively(v, memo));
        }
        return out.toArray(new Object[0]);
    }

    /**
     * Scala: def convertMap(obj: Any, map: Map[K,V], memo: IdentityHashMap): AnyRef
     * Ensures we reuse the same map instance from the memo (identity), but rebuild its contents
     * from a snapshot to avoid concurrent modification when obj == map.
     */
    @SuppressWarnings("unchecked")
    private Object convertMap(Object obj, Map<?, ?> map, IdentityHashMap<Object, Object> memo) {
        Map<Object, Object> converted;
        Object cached = memo.get(obj);
        if (cached instanceof Map) {
            converted = (Map<Object, Object>) cached;
        } else {
            converted = new LinkedHashMap<>();
            memo.put(obj, converted);
        }

        // Snapshot entries before mutating the target map
        List<Map.Entry<?, ?>> entries = new ArrayList<>(map.entrySet());

        // Build a temporary map of converted entries
        Map<Object, Object> tmp = new LinkedHashMap<>(entries.size());
        for (Map.Entry<?, ?> e : entries) {
            Object ck = convertRecursively(e.getKey(), memo);
            Object cv = convertRecursively(e.getValue(), memo);
            tmp.put(ck, cv);
        }

        // Replace contents atomically-ish
        converted.clear();
        converted.putAll(tmp);

        return memo.get(obj); // return the identity-memoized map
    }
}
