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

package net.daporkchop.lib.network.tcp.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.daporkchop.lib.common.reference.InstancePool;
import net.daporkchop.lib.network.pipeline.Pipeline;
import net.daporkchop.lib.network.pipeline.util.PipelineListener;
import net.daporkchop.lib.network.protocol.DataProtocol;
import net.daporkchop.lib.network.protocol.HandlingProtocol;
import net.daporkchop.lib.network.session.AbstractUserSession;
import net.daporkchop.lib.network.tcp.WrapperNioSocketChannel;
import net.daporkchop.lib.network.tcp.endpoint.TCPEndpoint;
import net.daporkchop.lib.network.tcp.pipeline.Framer;
import net.daporkchop.lib.network.tcp.pipeline.TCPDataCodec;

import java.util.function.Consumer;

/**
 * @author DaPorkchop_
 */
@RequiredArgsConstructor
@Getter
public class TCPChannelInitializer<E extends TCPEndpoint<?, S, ?>, S extends AbstractUserSession<S>> extends ChannelInitializer<WrapperNioSocketChannel<S>> {
    @NonNull
    protected final E endpoint;
    @NonNull
    protected final Consumer<WrapperNioSocketChannel<S>> addedCallback;
    @NonNull
    protected final Consumer<WrapperNioSocketChannel<S>> removedCallback;

    public TCPChannelInitializer(@NonNull E endpoint) {
        this(endpoint, ch -> {}, ch -> {});
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initChannel(@NonNull WrapperNioSocketChannel<S> channel) throws Exception {
        channel.pipeline()
                .addLast("write", new TCPWriter<>(channel))
                .addLast("handle", new TCPHandler<>(channel));

        Pipeline<S> pipeline = channel.dataPipeline();

        pipeline
                .addLast("tcp_framer", (PipelineListener<S>) InstancePool.getInstance(Framer.DefaultFramer.class));

        if (this.endpoint.protocol() instanceof DataProtocol)   {
            pipeline
                    .addLast("protocol", new TCPDataCodec<>((DataProtocol<S>) this.endpoint.protocol(), channel.alloc()));
        }

        this.endpoint.protocol().pipelineInitializer().initPipeline(pipeline, channel.userSession());

        this.addedCallback.accept(channel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        this.removedCallback.accept((WrapperNioSocketChannel) ctx.channel());
    }
}
