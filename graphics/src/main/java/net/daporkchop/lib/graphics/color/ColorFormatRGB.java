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

package net.daporkchop.lib.graphics.color;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.daporkchop.lib.graphics.bitmap.PImage;
import net.daporkchop.lib.graphics.bitmap.image.DirectImageARGB;

/**
 * An implementation of the RGB color format.
 *
 * @author DaPorkchop_
 * @see ColorFormat#RGB
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class ColorFormatRGB implements ColorFormat {
    public static int toARGB(int rgb)   {
        return 0xFF000000 | rgb;
    }

    public static int fromARGB(int argb)   {
        return argb & 0x00FFFFFF;
    }

    @Override
    public int decode(long color) {
        return toARGB((int) color);
    }

    @Override
    public long encode(int argb) {
        return (long) fromARGB(argb);
    }

    @Override
    public int encodedBits() {
        return 24;
    }

    @Override
    public boolean alpha() {
        return false;
    }

    @Override
    public PImage createImage(int width, int height) {
        return new DirectImageARGB(width, height); //TODO
    }
}
