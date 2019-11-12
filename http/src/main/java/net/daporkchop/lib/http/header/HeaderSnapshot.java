/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2019 DaPorkchop_ and contributors
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

package net.daporkchop.lib.http.header;

import lombok.NonNull;

/**
 * An immutable {@link HeaderMap}
 *
 * @author DaPorkchop_
 */
public final class HeaderSnapshot implements HeaderMap {
    protected final Header[] value;

    public HeaderSnapshot(@NonNull HeaderMap source)    {
        int size = source.size();
        this.value = new Header[size];
        for (int i = 0; i < size; i++)  {
            this.value[i] = new HeaderImpl(source.get(i));
        }
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public Header get(int index) throws IndexOutOfBoundsException {
        return this.value[index];
    }

    @Override
    public Header get(@NonNull String key) {
        for (Header header : this.value)    {
            if (key.equals(header.key()))   {
                return header;
            }
        }
        return null;
    }

    @Override
    public HeaderMap snapshot() {
        return this;
    }
}
