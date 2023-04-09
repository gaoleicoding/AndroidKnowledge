package com.example.knowledge.cryptography;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Util {


    public static String md5(String s) {

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {

            byte[] btInput = s.getBytes(StandardCharsets.UTF_8);

            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            mdInst.update(btInput);

            byte[] md = mdInst.digest();

            int j = md.length;

            char[] str = new char[j * 2];

            int k = 0;

            for (byte byte0 : md) {

                str[k++] = hexDigits[byte0 >>> 4 & 0xf];

                str[k++] = hexDigits[byte0 & 0xf];

            }

            return new String(str);

        } catch (Exception e) {

            return "";

        }

    }

    private static MappedByteBuffer[] mappedByteBuffers;
    private static int bufferCount;

    /**
     * 获取单个文件的MD5值！
     * 解决首位0被省略问题
     * 解决超大文件问题
     */

    public static String getFileMD5(File file) {

        StringBuilder stringbuffer = null;
        try {
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();

            long fileSize = ch.size();
            bufferCount = (int) Math.ceil((double) fileSize / (double) Integer.MAX_VALUE);
            mappedByteBuffers = new MappedByteBuffer[bufferCount];

            long preLength = 0;
            long regionSize = Integer.MAX_VALUE;
            for (int i = 0; i < bufferCount; i++) {
                if (fileSize - preLength < Integer.MAX_VALUE) {
                    regionSize = fileSize - preLength;
                }
                mappedByteBuffers[i] = ch.map(FileChannel.MapMode.READ_ONLY, preLength, regionSize);
                preLength += regionSize;
            }

            MessageDigest messagedigest = MessageDigest.getInstance("MD5");

            for (int i = 0; i < bufferCount; i++) {
                messagedigest.update(mappedByteBuffers[i]);
            }
            byte[] bytes = messagedigest.digest();
            int n = bytes.length;
            stringbuffer = new StringBuilder(2 * n);
            for (byte bt : bytes) {
                char c0 = hexDigits[(bt & 0xf0) >> 4];
                char c1 = hexDigits[bt & 0xf];
                stringbuffer.append(c0);
                stringbuffer.append(c1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert stringbuffer != null;
        return stringbuffer.toString();

    }
}