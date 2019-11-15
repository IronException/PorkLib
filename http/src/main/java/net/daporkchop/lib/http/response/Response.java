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

package net.daporkchop.lib.http.response;

import net.daporkchop.lib.http.StatusCode;
import net.daporkchop.lib.http.header.HeaderMap;

/**
 * The server's response to an HTTP request.
 *
 * @author DaPorkchop_
 */
public interface Response {
    /**
     * @return the {@link StatusCode} that the server responded with
     */
    StatusCode status();

    /**
     * @return a {@link HeaderMap} containing the headers that the server responded with
     */
    HeaderMap headers();

    /**
     * Gets the length of the response's body's content (in bytes).
     *
     * If the body's length is not known (e.g. Transfer-Encoding is "chunked"), this will return {@code -1L}.
     * @return the length of the response's body's content (in bytes)
     */
    default long contentLength() {
        String length = this.headers().getValue("content-length");
        return length == null ? -1L : Long.parseLong(length);
    }
}
