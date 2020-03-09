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

package net.daporkchop.lib.concurrent.executor;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.daporkchop.lib.common.util.PValidation;
import net.daporkchop.lib.concurrent.PFuture;
import net.daporkchop.lib.concurrent.future.DefaultPFuture;
import net.daporkchop.lib.concurrent.future.runnable.RunnableCallablePFuture;
import net.daporkchop.lib.concurrent.future.runnable.RunnablePFuture;
import net.daporkchop.lib.concurrent.future.runnable.RunnableWithResultPFuture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Wraps a Java {@link Executor} into a Netty {@link EventExecutor} (as well as realistically possible).
 *
 * @author DaPorkchop_
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class JavaExecutorAsEventExecutor<E extends Executor> implements EventExecutor {
    @NonNull
    protected final E delegate;

    @Override
    public boolean isShuttingDown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Future<?> shutdownGracefully() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Future<?> terminationFuture() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EventExecutor next() {
        return this;
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EventExecutorGroup parent() {
        return this;
    }

    @Override
    public boolean inEventLoop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inEventLoop(Thread thread) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> Promise<V> newPromise() {
        return new DefaultPFuture<>(this);
    }

    @Override
    public <V> ProgressivePromise<V> newProgressivePromise() {
        return null;
    }

    @Override
    public <V> PFuture<V> newSucceededFuture(V result) {
        return null;
    }

    @Override
    public <V> PFuture<V> newFailedFuture(Throwable cause) {
        return null;
    }

    @Override
    public PFuture<?> submit(Runnable task) {
        RunnablePFuture future = new RunnablePFuture(this, task);
        this.delegate.execute(future);
        return future;
    }

    @Override
    public <T> PFuture<T> submit(Runnable task, T result) {
        RunnableWithResultPFuture<T> future = new RunnableWithResultPFuture<>(this, task, result);
        this.delegate.execute(future);
        return future;
    }

    @Override
    public <T> PFuture<T> submit(Callable<T> task) {
        RunnableCallablePFuture<T> future = new RunnableCallablePFuture<>(this, task);
        this.delegate.execute(future);
        return future;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isShutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTerminated() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        List<java.util.concurrent.Future<T>> list = new ArrayList<>(tasks.size());
        for (Callable<T> task : tasks) {
            list.add(this.submit(task));
        }
        return list;
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        int size = PValidation.ensurePositive(tasks.size());
        List<java.util.concurrent.Future<T>> list = new ArrayList<>(size);
        ExecutorCompletionService<T> ecs = new ExecutorCompletionService<>(this);

        try {
            ExecutionException ee = null;
            Iterator<? extends Callable<T>> it = tasks.iterator();

            list.add(ecs.submit(it.next()));
            size--;
            int active = 1;

            for (; ; ) {
                java.util.concurrent.Future<T> f = ecs.poll();
                if (f == null) {
                    if (size > 0) {
                        size--;
                        list.add(ecs.submit(it.next()));
                        active++;
                    } else if (active != 0) {
                        f = ecs.take();
                    } else {
                        break;
                    }
                }

                if (f != null) {
                    active--;
                    try {
                        return f.get();
                    } catch (ExecutionException eex) {
                        ee = eex;
                    } catch (RuntimeException rex) {
                        ee = new ExecutionException(rex);
                    }
                }
            }

            if (ee == null) {
                ee = new ExecutionException(new IllegalStateException("nothing happened?!?"));
            }
            throw ee;
        } finally {
            for (java.util.concurrent.Future<T> future : list) {
                future.cancel(true);
            }
        }
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void execute(Runnable command) {
        this.delegate.execute(command);
    }
}