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

package net.daporkchop.lib.http.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.daporkchop.lib.http.entity.content.type.ContentType;
import net.daporkchop.lib.http.entity.transfer.ByteBufTransferSession;
import net.daporkchop.lib.http.entity.transfer.TransferSession;

import java.nio.ByteBuffer;

/**
 * A simple implementation of {@link HttpEntity} that stores data in a {@code byte[]}.
 *
 * @author DaPorkchop_
 */
@Getter
@Accessors(fluent = true)
public final class ByteArrayHttpEntity extends ByteBufTransferSession implements HttpEntity {
    protected final ContentType type;
    protected final byte[]      data;

    public ByteArrayHttpEntity(@NonNull ContentType type, @NonNull byte[] data) {
        super(Unpooled.wrappedBuffer(data));

        this.type = type;
        this.data = data;
    }

    @Override
    public TransferSession newSession() throws Exception {
        return this;
    }

    @Override
    public ByteBuf getByteBuf() throws Exception {
        return this.buf.slice(); //no need to retain, see comment below
    }

    @Override
    public void retain() {
        //no need to retain, see comment below
    }

    @Override
    public boolean release() {
        //no-op, unpooled heap bytebuf doesn't provide any benefit from being released manually
        return false;
    }
}
