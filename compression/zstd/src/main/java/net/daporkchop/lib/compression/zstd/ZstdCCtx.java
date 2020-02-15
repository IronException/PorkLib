/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2020 DaPorkchop_ and contributors
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.lib.compression.zstd;

import io.netty.buffer.ByteBuf;
import lombok.NonNull;
import net.daporkchop.lib.compression.CCtx;
import net.daporkchop.lib.compression.util.exception.DictionaryNotAllowedException;
import net.daporkchop.lib.natives.util.exception.InvalidBufferTypeException;

/**
 * Compression context for {@link Zstd}.
 * <p>
 * Not thread-safe.
 *
 * @author DaPorkchop_
 */
public interface ZstdCCtx extends CCtx {
    @Override
    ZstdProvider provider();

    /**
     * Compresses the given source data into the given destination buffer at the configured Zstd level.
     *
     * @see CCtx#compress(ByteBuf, ByteBuf)
     */
    @Override
    default boolean compress(@NonNull ByteBuf src, @NonNull ByteBuf dst) throws InvalidBufferTypeException {
        return this.compress(src, dst, this.level());
    }

    /**
     * Compresses the given source data into a single Zstd frame into the given destination buffer at the given Zstd level.
     * <p>
     * This is possible because Zstd allows using the same context for any compression level without having to reallocate it.
     *
     * @see #compress(ByteBuf, ByteBuf)
     */
    boolean compress(@NonNull ByteBuf src, @NonNull ByteBuf dst, int compressionLevel) throws InvalidBufferTypeException;

    /**
     * Compresses the given source data into the given destination buffer at the configured Zstd level using the given dictionary.
     *
     * @see CCtx#compress(ByteBuf, ByteBuf, ByteBuf)
     */
    @Override
    default boolean compress(@NonNull ByteBuf src, @NonNull ByteBuf dst, ByteBuf dict) throws InvalidBufferTypeException, DictionaryNotAllowedException {
        return this.compress(src, dst, dict, this.level());
    }

    /**
     * Compresses the given source data into a single Zstd frame into the given destination buffer at the given Zstd level using the given dictionary.
     * <p>
     * This is possible because Zstd allows using the same context for any compression level without having to reallocate it.
     *
     * @see #compress(ByteBuf, ByteBuf, int)
     * @see #compress(ByteBuf, ByteBuf, ByteBuf)
     */
    boolean compress(@NonNull ByteBuf src, @NonNull ByteBuf dst, ByteBuf dict, int compressionLevel) throws InvalidBufferTypeException;

    /**
     * Compresses the given source data into a single Zstd frame into the given destination buffer using the given dictionary.
     * <p>
     * As the dictionary has already been digested, this is far faster than the other dictionary compression methods.
     *
     * @param dictionary the dictionary to use
     * @see #compress(ByteBuf, ByteBuf)
     */
    boolean compress(@NonNull ByteBuf src, @NonNull ByteBuf dst, @NonNull ZstdCDict dictionary) throws InvalidBufferTypeException;

    @Override
    default boolean hasDict() {
        return true;
    }
}