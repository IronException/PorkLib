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

package net.daporkchop.lib.http.header;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.daporkchop.lib.common.misc.string.FastCaseInsensitiveStringComparator;

import java.util.Map;
import java.util.TreeMap;

/**
 * Helpers for {@link HeaderMap}.
 *
 * @author DaPorkchop_
 */
@UtilityClass
public class HeaderMaps {
    private final MutableHeaderMap EMPTY = new EmptyHeaderMap();

    private final Map<String, Object> RESERVED_KEYS = new TreeMap<>(FastCaseInsensitiveStringComparator.INSTANCE);

    static {
        RESERVED_KEYS.put("accept-encoding", null);
        RESERVED_KEYS.put("authorization", null);
        RESERVED_KEYS.put("content-encoding", null);
        RESERVED_KEYS.put("content-length", null);
        RESERVED_KEYS.put("content-type", null);
        RESERVED_KEYS.put("transfer-encoding", null);
    }

    public MutableHeaderMap empty() {
        return EMPTY;
    }

    public HeaderMap singleton(@NonNull String key, @NonNull String value) {
        return new SingletonHeaderMap(key, value);
    }

    public boolean isReserved(@NonNull String key) {
        return RESERVED_KEYS.containsKey(key);
    }
}
