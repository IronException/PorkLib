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

package net.daporkchop.lib.network.session;

import io.netty.util.concurrent.Future;
import lombok.NonNull;
import net.daporkchop.lib.network.endpoint.PEndpoint;
import net.daporkchop.lib.network.transport.NetSession;
import net.daporkchop.lib.network.transport.TransportEngine;

/**
 * A user-level session implementation. End users will most likely only ever be interacting with this class (and
 * subclasses thereof).
 *
 * @author DaPorkchop_
 */
public interface UserSession extends PSession<UserSession> {
    @Override
    default <E extends PEndpoint<E>> E endpoint() {
        return this.internalSession().endpoint();
    }

    @Override
    default PChannel channel(int id) {
        return this.internalSession().channel(id);
    }

    @Override
    default PChannel openChannel(Reliability reliability) {
        return this.internalSession().openChannel(reliability);
    }

    @Override
    default UserSession send(@NonNull Object packet, Reliability reliability) {
        this.internalSession().send(packet, reliability);
        return this;
    }

    @Override
    default UserSession send(@NonNull Object packet, Reliability reliability, int channelId) {
        this.internalSession().send(packet, reliability, channelId);
        return this;
    }

    @Override
    default Future<Void> sendFuture(@NonNull Object packet, Reliability reliability) {
        return this.internalSession().sendFuture(packet, reliability);
    }

    @Override
    default Future<Void> sendFuture(@NonNull Object packet, Reliability reliability, int channelId) {
        return this.internalSession().sendFuture(packet, reliability, channelId);
    }

    @Override
    default void close() {
        this.internalSession().close();
    }

    @Override
    default Reliability fallbackReliability() {
        return this.internalSession().fallbackReliability();
    }

    @Override
    default UserSession fallbackReliability(@NonNull Reliability reliability) throws IllegalArgumentException {
        this.internalSession().fallbackReliability(reliability);
        return this;
    }

    @Override
    default boolean isClosed() {
        return this.internalSession().isClosed();
    }

    @Override
    default Future<Void> closeFuture() {
        return this.internalSession().closeFuture();
    }

    @Override
    default TransportEngine transportEngine() {
        return this.internalSession().transportEngine();
    }

    /**
     * Gets the internal transport-level session instance.
     *
     * @return the internal transport-level session instance
     */
    NetSession internalSession();
}
