package com.leien.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/16 16:40
 * @Description:
 * @ClassName: HexUtils
 */
public class HexUtils {
    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/

    public static byte[] toByteArray(String hexString) {
        String substring = hexString.substring(8, 38);
        hexString = substring.replaceAll(" ", "");
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
        for (int i = 0; i < byteArray.length; i++) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static Object[] getData(byte[] bytes){
        List list = new ArrayList();
        Object[] arr = new Object[10];
        for (int i =0;i<bytes.length;i++){
             list.add(bytes[i]);
        }
        for(int i =0;i<list.size();i++){
            arr[i] =  list.get(i);
        }
        return arr;
    }

    public static void main(String[] args) {
        String str = "33 03 0A 00 00 00 29 00 29 00 29 00 29 97 16 ";
        Map<String,Object> map = new HashMap<>();
        byte[] bytes = toByteArray(str);
        Object[] data = getData(bytes);
        map.put("阀门状态",data[1]);
        map.put("进水温度",data[3]);
        map.put("回水温度",data[5]);
        map.put("备一温度",data[7]);
        map.put("备二温度",data[9]);
    }
}
