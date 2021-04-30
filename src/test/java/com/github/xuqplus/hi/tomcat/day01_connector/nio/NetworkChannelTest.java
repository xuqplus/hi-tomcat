package com.github.xuqplus.hi.tomcat.day01_connector.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @Author: Qunqun Xu
 * @MailTo: <link>mailto:qunqun.xu@autodesk.com</link>
 * @CreatedAt: 2021-04-30 14:03
 */
@Slf4j
public class NetworkChannelTest {

    void test() throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        FileChannel fileChannel = FileChannel.open(Path.of("src/test/resources/a.txt"), StandardOpenOption.READ);
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        Thread.sleep(3000L);
    }

    @Test
    void server() throws Exception {
        new Thread(() -> {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
                SocketChannel socketChannel = serverSocketChannel.accept();
                ByteBuffer bbuf = ByteBuffer.allocate(2048);
                while (-1 != socketChannel.read(bbuf)) {
                    final String s = new String(bbuf.array());
                    System.out.println(String.format("-->%s", s));
                    bbuf.flip();
                    bbuf.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        test();
    }
}
