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

import db.DBMapTest;
import db.DBSetTest;
import db.TestConstants;
import net.daporkchop.lib.binary.serialization.Serialization;
import net.daporkchop.lib.binary.serialization.impl.ByteArraySerializer;
import net.daporkchop.lib.binary.serialization.impl.StringSerializer;
import net.daporkchop.lib.dbextensions.leveldb.builder.LevelDBMapBuilder;
import net.daporkchop.lib.dbextensions.leveldb.builder.LevelDBSetBuilder;
import net.daporkchop.lib.hash.util.Digest;
import net.daporkchop.lib.hash.util.Digester;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author DaPorkchop_
 */
public class LevelDBTest implements TestConstants {
    static {
        TestConstants.init("LevelDB");
    }

    @Test
    public void map() throws IOException {
        new DBMapTest(() -> new LevelDBMapBuilder<>()
                .keyHasher((String obj) -> {
                    Digester digest = Digest.SHA3_256.start();
                    digest.appendStream().writeUTF(obj);
                    return digest.hash().getHash();
                })
                .valueSerializer(ByteArraySerializer.INSTANCE)
                .path(new File(ROOT_DIR, "map"))
                .serialization(Serialization.DEFAULT_REGISTRY)
                .build()
        ).test();
    }

    @Test
    public void set() throws IOException {
        new DBSetTest(() -> new LevelDBSetBuilder<>()
                .valueSerializer(StringSerializer.INSTANCE)
                .path(new File(ROOT_DIR, "set"))
                .build()
        ).test();
    }
}