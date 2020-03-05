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

package net.daporkchop.lib.concurrent;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.daporkchop.lib.concurrent.executor.JavaExecutorAsEventExecutorGroup;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

/**
 * Helper class for dealing with
 *
 * @author DaPorkchop_
 */
@UtilityClass
public class PExecutors {
    private final Map<Executor, EventExecutorGroup> GLOBAL_EXECUTORS_TO_GROUPS = Collections.synchronizedMap(new WeakHashMap<>());

    private final Function<Executor, EventExecutorGroup> GLOBAL_EXECUTOR_CALCULATOR = executor -> {
        if (executor instanceof EventExecutorGroup) {
            return (EventExecutorGroup) executor;
        } else if (executor instanceof ScheduledExecutorService) {
            //TODO: something
            throw new UnsupportedOperationException();
        } else if (executor instanceof ExecutorService) {
            //TODO: something
            throw new UnsupportedOperationException();
        } else {
            return new JavaExecutorAsEventExecutorGroup(executor);
        }
    };

    public static EventExecutorGroup toNettyExecutorGroup(@NonNull Executor executor) {
        return GLOBAL_EXECUTORS_TO_GROUPS.computeIfAbsent(executor, GLOBAL_EXECUTOR_CALCULATOR);
    }

    public static EventExecutor toNettyExecutor(@NonNull Executor executor) {
        EventExecutorGroup group =
    }
}
