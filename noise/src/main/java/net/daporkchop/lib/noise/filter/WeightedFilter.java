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

package net.daporkchop.lib.noise.filter;

import lombok.NonNull;
import net.daporkchop.lib.noise.NoiseSource;
import net.daporkchop.lib.noise.util.NoiseFactory;
import net.daporkchop.lib.random.PRandom;

import static net.daporkchop.lib.math.primitive.PMath.*;

/**
 * Weights values from a {@link NoiseSource} towards the outer bounds, providing far more valley and peaks that approach -1 and 1.
 *
 * @author DaPorkchop_
 */
public final class WeightedFilter extends FilterNoiseSource {
    private static double fade(double t) {
        //the values seem to occasionally go above and below due to floating point errors
        return clamp(t * t * (-t * 2.0d + 3.0d), 0.0d, 1.0d);
    }

    public WeightedFilter(@NonNull NoiseSource delegate) {
        super(delegate.toRange(0.0d, 1.0d));
    }

    public WeightedFilter(@NonNull NoiseFactory factory, @NonNull PRandom random) {
        this(factory.apply(random));
    }

    @Override
    public double min() {
        return 0.0d;
    }

    @Override
    public double max() {
        return 1.0d;
    }

    @Override
    public double get(double x) {
        return fade(this.delegate.get(x));
    }

    @Override
    public double get(double x, double y) {
        return fade(this.delegate.get(x, y));
    }

    @Override
    public double get(double x, double y, double z) {
        return fade(this.delegate.get(x, y, z));
    }

    @Override
    public String toString() {
        return String.format("Weighted(%s)", this.delegate);
    }
}