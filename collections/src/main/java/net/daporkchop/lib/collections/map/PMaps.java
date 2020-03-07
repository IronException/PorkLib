/*
 * Adapted from The MIT License (MIT)
 *
 * Copyright (c) 2018-2020 DaPorkchop_
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * Any persons and/or organizations using this software must include the above copyright notice and this permission notice,
 * provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.lib.collections.map;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.daporkchop.lib.collections.map.lock.AutoLockedMap;
import net.daporkchop.lib.collections.map.lock.DefaultLockedMap;
import net.daporkchop.lib.collections.map.lock.LockedMap;

import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * Helper methods for dealing with maps.
 *
 * @author DaPorkchop_
 */
@UtilityClass
public class PMaps {
    public static <K, V> LockedMap<K, V> locked(@NonNull Map<K, V> map, boolean lockAutomatically) {
        return lockAutomatically ? autoLocked(map) : locked(map);
    }

    public static <K, V> LockedMap<K, V> locked(@NonNull Map<K, V> map) {
        return new DefaultLockedMap<>(map);
    }

    public static <K, V> LockedMap<K, V> autoLocked(@NonNull Map<K, V> map) {
        return new AutoLockedMap<>(map);
    }

    public static <K, V> LockedMap<K, V> locked(@NonNull Map<K, V> map, @NonNull Lock lock, boolean lockAutomatically) {
        return lockAutomatically ? autoLocked(map, lock) : locked(map, lock);
    }

    public static <K, V> LockedMap<K, V> locked(@NonNull Map<K, V> map, @NonNull Lock lock) {
        return new DefaultLockedMap<>(map, lock);
    }

    public static <K, V> LockedMap<K, V> autoLocked(@NonNull Map<K, V> map, @NonNull Lock lock) {
        return new AutoLockedMap<>(map, lock);
    }
}
