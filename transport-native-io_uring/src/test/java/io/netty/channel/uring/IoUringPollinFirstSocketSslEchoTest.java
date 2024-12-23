/*
 * Copyright 2024 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.uring;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.testsuite.transport.TestsuitePermutation;
import io.netty.testsuite.transport.socket.SocketSslEchoTest;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class IoUringPollinFirstSocketSslEchoTest extends SocketSslEchoTest {

    @BeforeAll
    public static void loadJNI() {
        assumeTrue(IoUring.isAvailable());
    }

    @Override
    protected List<TestsuitePermutation.BootstrapComboFactory<ServerBootstrap, Bootstrap>> newFactories() {
        return IoUringSocketTestPermutation.INSTANCE.socket();
    }

    @Override
    protected void configure(ServerBootstrap sb, Bootstrap cb, ByteBufAllocator allocator) {
        super.configure(sb, cb, allocator);
        sb.option(IoUringChannelOption.POLLIN_FIRST, true);
        sb.childOption(IoUringChannelOption.POLLIN_FIRST, true);
        cb.option(IoUringChannelOption.POLLIN_FIRST, true);
    }
}
