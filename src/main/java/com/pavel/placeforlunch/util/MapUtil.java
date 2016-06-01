package com.pavel.placeforlunch.util;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {

    /**
     * Sort map using comparator. Sorting result is returned as {@link LinkedHashMap}.
     * Input map is not modified.
     *
     * @param map Input map
     * @param cmp Comparator for {@link Map.Entry}
     * @param <K> Key type
     * @param <V> Value type
     * @return Sorted copy of input map as {@link LinkedHashMap}
     */
    public static <K, V> Map<K, V> sortMap(Map<K, V> map, Comparator<Map.Entry<K, V>> cmp) {
        Map<K, V> result = new LinkedHashMap<>(map.size());
        map.entrySet().stream().sorted(cmp).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

}
