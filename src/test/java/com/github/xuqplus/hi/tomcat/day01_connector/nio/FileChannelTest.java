package com.github.xuqplus.hi.tomcat.day01_connector.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Qunqun Xu
 * @MailTo: <link>mailto:qunqun.xu@autodesk.com</link>
 * @CreatedAt: 2021-04-30 14:03
 */
@Slf4j
public class FileChannelTest {
    @Test
    void test() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("src/test/resources/a.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println(">>>>>> Read " + bytesRead);
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
            System.out.println();
        }
        aFile.close();
    }
}
