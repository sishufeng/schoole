package com.leien.utils;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/8 11:53
 * @Description:
 */
public class AnalysisUtil {
    public static String analysis(byte[] frame, FrameType frametype) {
        String result = null;

        switch (frametype) {
            case setDeviceOn:
                result = AnalysisUtil.analysisSetDeviceOn(frame);
                break;
            case setDeviceOff:
                result = AnalysisUtil.analysisSetDeviceOff(frame);
                break;
            case getDeviceState:
                result = AnalysisUtil.analysisGetDeviceState(frame);
                break;
            case setBroadcastStart:
                result = AnalysisUtil.analysisSetBroadcastStart(frame);
                break;
            case setBroadcastStop:
                result = AnalysisUtil.analysisSetBroadcastStop(frame);
                break;
            default:
                break;
        }

        return result;
    }

    public static String analysisGetDeviceState(byte[] frame) {
        if (frame == null) {
            return null;
        }
        if (frame.length < 15) {
            return null;
        }
        if (frame[1] == 0x03 && frame[2] == 10) {
            byte[] data = new byte[13];
            System.arraycopy(frame, 0, data, 0, 13);
            byte[] crc = ModbusCrcUtil.getCRC(data);
            if (crc[0] != frame[13] || crc[1] != frame[14]) {
                return null;
            }
            return (int) (frame[4]) + "";
        }
        return null;
    }

    public static String analysisSetDeviceOn(byte[] frame) {
        if (frame.length < 8) {
            return null;
        }
        if (frame[1] == 0x10 && frame[2] == 0x0c && frame[3] == 0x6d && frame[4] == 0x00
                && frame[5] == 0x01) {
            byte[] data = new byte[6];
            System.arraycopy(frame, 0, data, 0, 6);
            byte[] crc = ModbusCrcUtil.getCRC(data);
            if (crc[0] != frame[6] || crc[1] != frame[7]) {
                return null;
            }
            return "success";
        }
        return null;
    }

    public static String analysisSetDeviceOff(byte[] frame) {
        if (frame.length < 8) {
            return null;
        }
        if (frame[1] == 0x10 && frame[2] == 0x0c && frame[3] == 0x6d && frame[4] == 0x00
                && frame[5] == 0x01) {
            byte[] data = new byte[6];
            System.arraycopy(frame, 0, data, 0, 6);
            byte[] crc = ModbusCrcUtil.getCRC(data);
            if (crc[0] != frame[6] || crc[1] != frame[7]) {
                return null;
            }
            return "success";
        }
        return null;
    }

    private static String analysisSetBroadcastStart(byte[] frame) {
        if (frame.length < 8) {
            return null;
        }
        if (frame[1] == 0x10 && frame[2] == 0x0c && frame[3] == 0x5b && frame[4] == 0x00
                && frame[5] == 0x01) {
            byte[] data = new byte[6];
            System.arraycopy(frame, 0, data, 0, 6);
            byte[] crc = ModbusCrcUtil.getCRC(data);
            if (crc[0] != frame[6] || crc[1] != frame[7]) {
                return null;
            }
            return "success";
        }
        return null;
    }

    private static String analysisSetBroadcastStop(byte[] frame) {
        if (frame.length < 8) {
            return null;
        }
        if (frame[1] == 0x10 && frame[2] == 0x0c && frame[3] == 0x5f && frame[4] == 0x00
                && frame[5] == 0x01) {
            byte[] data = new byte[6];
            System.arraycopy(frame, 0, data, 0, 6);
            byte[] crc = ModbusCrcUtil.getCRC(data);
            if (crc[0] != frame[6] || crc[1] != frame[7]) {
                return null;
            }
            return "success";
        }
        return null;
    }
}
