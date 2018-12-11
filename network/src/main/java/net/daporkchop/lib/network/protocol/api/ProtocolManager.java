/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2018 DaPorkchop_ and contributors
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

package net.daporkchop.lib.network.protocol.api;

import net.daporkchop.lib.crypto.CryptographySettings;
import net.daporkchop.lib.encoding.compression.CompressionHelper;

/**
 * Implements a network protocol
 *
 * @author DaPorkchop_
 */
public interface ProtocolManager {
    /**
     * Create and get a new {@link EndpointManager.ServerEndpointManager}
     *
     * @return a new server manager
     */
    EndpointManager.ServerEndpointManager createServerManager();

    /**
     * Create and get a new {@link EndpointManager.ClientEndpointManager}
     *
     * @return a new client manager
     */
    EndpointManager.ClientEndpointManager createClientManager();

    //TODO: use an enum for the following methods:

    /**
     * Checks if this protocol manager respects the encryption settings as defined in
     * {@link net.daporkchop.lib.network.endpoint.builder.ServerBuilder#setCryptographySettings(CryptographySettings)}
     * <p>
     * (i.e. if it supports encryption)
     *
     * @return whether or not this protocol supports encryption
     */
    boolean areEncryptionSettingsRespected();

    /**
     * Checks if this protocol manager respects the compression settings as defined in
     * {@link net.daporkchop.lib.network.endpoint.builder.ServerBuilder#setCompression(CompressionHelper)}
     * <p>
     * (i.e. if it supports compression)
     *
     * @return whether or not this protocol supports compression
     */
    boolean areCompressionSettingsRespected();
}