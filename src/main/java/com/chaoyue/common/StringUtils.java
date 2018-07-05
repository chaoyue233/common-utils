package com.chaoyue.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chaoyue on 2017/6/20
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str 判断字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("null") || str.equals("{}") || str.equals("[]")
                || "".equals(str) || "".equals(str.trim()) || str.length() == 0;
    }

    /**
     * json key 下划线转驼峰
     *
     * @param json json数据
     */
    public static String underlineToCamelJson(String json) {
        if (!StringUtils.isEmpty(json)) {
            Map<String, Object> resultMap = new HashMap<>();
            Map<String, Object> jsonMap = JsonUtils.jsonToMap(json);
            Set<String> keySet = jsonMap.keySet();
            for (String key : keySet) {
                resultMap.put(underlineToCamelWord(key, Boolean.TRUE), jsonMap.get(key));
            }
            return JsonUtils.objectToJSON(resultMap);
        }
        return null;
    }

    /**
     * json key 驼峰转下划线
     *
     * @param json json数据
     */
    public static String camelToUnderlineJson(String json) {
        if (!StringUtils.isEmpty(json)) {
            Map<String, Object> resultMap = new HashMap<>();
            Map<String, Object> jsonMap = JsonUtils.jsonToMap(json);
            Set<String> keySet = jsonMap.keySet();
            for (String key : keySet) {
                resultMap.put(camelToUnderlineWord(key, Boolean.TRUE), jsonMap.get(key));
            }
            return JsonUtils.objectToJSON(resultMap);
        }
        return null;
    }

    /**
     * 下划线转驼峰法
     *
     * @param line      源字符串
     * @param smallCase 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underlineToCamelWord(String line, boolean smallCase) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCase && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderlineWord(String line, Boolean smallCase) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            if (smallCase) {
                sb.append(word.toLowerCase());
            } else {
                sb.append(word.toUpperCase());
            }
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    public static String getSubString(String strIn, int len) {
        if (strIn == null)
            return null;
        if (strIn.length() > len)
            return strIn.substring(0, len - 1);
        else
            return strIn;
    }

    /**
     * 判断字符串是否为手机号
     */
    public static boolean isPhoneNumber(String phone) {
        if (!isEmpty(phone)) {
            Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
            Matcher matcher = pattern.matcher(phone);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 判断字符串是否为邮箱
     */
    public static boolean isMailAddress(String mailAddress) {
        if (!isEmpty(mailAddress)) {
            Pattern pattern = Pattern.compile("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
            Matcher matcher = pattern.matcher(mailAddress);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 判断字符串是否为身份证号码
     */
    public static boolean isIdNumber(String idNumber) {
        if (!isEmpty(idNumber)) {
            // 15位身份证
            Pattern pattern15 = Pattern.compile("^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$");
            Matcher matcher15 = pattern15.matcher(idNumber);
            // 18位身份证
            Pattern pattern18 = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
            Matcher matcher18 = pattern18.matcher(idNumber);
            return (matcher15.matches() || matcher18.matches());
        }
        return false;
    }

    /**
     * 判断字符串是否为网址
     */
    public static boolean isHttpAddress(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern
                .compile("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
