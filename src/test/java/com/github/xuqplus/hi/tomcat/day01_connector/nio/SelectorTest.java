package com.github.xuqplus.hi.tomcat.day01_connector.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class SelectorTest {

    @Test
    void test() throws Exception {
        new Thread(() -> {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 8080));
                Selector selector = Selector.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    int ready = selector.selectNow();
                    if (0 >= ready) {
                        continue;
                    }
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            log.info("1");
                        }
                        if (key.isConnectable()) {
                            log.info("2");
                        }
                        if (key.isReadable()) {
                            log.info("3");
                        }
                        if (key.isWritable()) {
                            log.info("4");
                        }
                        if (key.isValid()) {
                            log.info("5");
                        }
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                // noop
            }
        }).start();

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        FileChannel fileChannel = FileChannel.open(Path.of("src/test/resources/a.txt"), StandardOpenOption.READ);
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        Thread.sleep(3000L);
    }
}
