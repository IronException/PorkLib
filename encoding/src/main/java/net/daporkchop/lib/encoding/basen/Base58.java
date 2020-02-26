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

package net.daporkchop.lib.encoding.basen;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Data encoding format used by Bitcoin for wallet addresses
 * <p>
 * Base58 is better than Base64 for one main reason: no non-alphanumeric characters!
 * Because all characters are alphanumeric, the data will be picked up as a single word by pretty much every system.
 * That means that there won't be any line breaks, you can double-click to select the whole thing, etc.
 *
 * @author DaPorkchop_
 */
@UtilityClass
public class Base58 {
    public  String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private BaseN  INSTANCE = BaseN.of(ALPHABET);

    public static String encodeBase58(@NonNull byte[] data) {
        return INSTANCE.encode(data);
    }

    public static byte[] decodeBase58(@NonNull String data) {
        return INSTANCE.decode(data);
    }
}
