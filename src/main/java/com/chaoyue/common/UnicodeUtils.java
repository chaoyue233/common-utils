package com.chaoyue.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnicodeUtils {
    /**
     * unicode 编码
     *
     * @param source 需要编码的字符串
     * @return 编码完成后的字符串
     */
    public static String unicode(String source) {
        StringBuilder sb = new StringBuilder();
        char[] source_char = source.toCharArray();
        String unicode;
        for (char aSource_char : source_char) {
            unicode = Integer.toHexString(aSource_char);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("\\u").append(unicode);
        }
        log.info("unicode encode source : " + source + " result : " + sb.toString());
        return sb.toString();
    }

    /**
     * unicode 解码
     *
     * @param unicode 需要解码的字符串
     * @return 解码完成后的字符串
     */
    public static String decodeUnicode(String unicode) {
        StringBuilder sb = new StringBuilder();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            sb.append((char) data);
        }
        return sb.toString();
    }
}
