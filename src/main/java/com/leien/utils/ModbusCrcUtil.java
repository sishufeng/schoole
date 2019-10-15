package com.leien.utils;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/10/8 11:41
 * @Description:
 */
public class ModbusCrcUtil {
    public static byte[] getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        byte[] crcbytes = ByteUtil.intToByteArrayCrc(CRC);

        return crcbytes;
    }

//	public static void main(String[] args) {
//		byte[] b = {0x33, 0x03, 0x02, 0x61, 0x00, 0x01};
//		byte[] d = ModbusCrcUtil.getCRC(b);
//		System.out.println(Integer.toHexString(d[0] & 0xFF)+ Integer.toHexString(d[1] & 0xFF));
//	}
}
