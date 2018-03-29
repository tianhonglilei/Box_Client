package com.zhang.box.client.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Test {
    public static String strTo16(String s) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str.append(s4);
        }
        return str.toString().trim();
    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static String longTo16(long num, int length) {
        StringBuffer str = new StringBuffer();
        String result = Long.toHexString(num);
        str.append(result);
        for (int i = 0; i < length - result.length(); i++) {
            str.insert(0, "0");
        }
        return str.toString();
    }

    public static String strLength(String str, int length) {
        StringBuffer lengthStr = new StringBuffer();
        lengthStr.append(str);
        if (lengthStr.length() < length) {
            for (int i = 0; i <= length - lengthStr.length(); i++) {
                lengthStr.insert(0, "0");
            }
        }
        return lengthStr.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
