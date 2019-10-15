package com.leien.utils;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/10/8 11:39
 * @Description:
 */
public class ByteUtil {
    public static long byteArrayToIntLen(byte[] b) {
        return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArrayLen(int a) {
        return new byte[] { (byte) (a & 0xFF), (byte) (a >> 8 & 0xFF), (byte) (a >> 16 & 0xFF),
                (byte) (a >> 24 & 0xFF) };
    }

    public static byte[] intToByteArrayCrc(int a) {
        return new byte[] { (byte) (a & 0xFF), (byte) (a >> 8 & 0xFF) };
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes
     *            要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte b){
        return Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
    }
}
