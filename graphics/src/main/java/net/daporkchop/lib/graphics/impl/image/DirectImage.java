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

package net.daporkchop.lib.graphics.impl.image;

import net.daporkchop.lib.common.util.PUnsafe;
import net.daporkchop.lib.graphics.PImage;
import net.daporkchop.lib.graphics.impl.icon.DirectIcon;

/**
 * @author DaPorkchop_
 */
public class DirectImage extends DirectIcon implements PImage {
    public DirectImage(int width, int height, boolean bw) {
        super(width, height, bw);
    }

    @Override
    public void setARGB(int x, int y, int argb) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height)  {
            throw new ArrayIndexOutOfBoundsException(String.format("(%d,%d) w=%d,h=%d", x, y, this.width, this.height));
        } else if (this.bw)    {
            PUnsafe.putByte(this.pos + (long) x * (long) this.height + (long) y, (byte) argb);
        } else {
            PUnsafe.putInt(this.pos + (((long) x * (long) this.height + (long) y) << 2L), argb);
        }
    }
}
