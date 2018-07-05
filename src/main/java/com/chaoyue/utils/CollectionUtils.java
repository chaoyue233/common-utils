package com.chaoyue.utils;


import java.util.*;

public class CollectionUtils {

    private static final int DEFAULT_MAX_LENGTH = 30;
    private static final String REPLACE_SUFFIX = "...";

    /**
     * 将逗号分隔字符串转换为自定类型List
     *
     * @param str   待转换的字符串
     * @param clazz 目标类型
     * @return 转换后的List
     */
    public static <T> List strToList(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList();
        }
        List<String> strList = Arrays.asList(str.split(","));
        List resultList = new ArrayList<>();
        for (String s : strList) {
            if (clazz.equals(Long.class)) {
                //noinspection unchecked
                resultList.add(Long.parseLong(s));
            } else if (clazz.equals(Integer.class)) {
                //noinspection unchecked
                resultList.add(Integer.parseInt(s));
            } else {
                //noinspection unchecked
                resultList.add(s);
            }
        }
        return resultList;
    }

    /**
     * List 内容类型转换
     *
     * @param list  待转换的List
     * @param clazz 目标类型
     * @return 转换后的list
     */
    public static <T, V> List<T> listChangeType(List<V> list, Class<T> clazz) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        List<T> resultList = new ArrayList<>();
        for (V item : list) {
            if (clazz.equals(String.class)) {
                //noinspection unchecked
                resultList.add((T) item.toString());
            } else if (clazz.equals(Integer.class)) {
                //noinspection unchecked
                resultList.add((T) new Integer(item.toString()));
            } else if (clazz.equals(Long.class)) {
                //noinspection unchecked
                resultList.add((T) new Long(item.toString()));
            } else {
                resultList.add(clazz.cast(item));
            }
        }
        return resultList;
    }

    /**
     * List转成逗号分隔字符串
     *
     * @param list 待转换的List
     * @return 转换后的字符串
     */
    public static String listToStr(List list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Object data : list) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(data.toString());
        }
        return sb.toString();
    }

    /**
     * List 转成逗号分隔字符串 并限定最大长度，超过后使用...替换
     *
     * @param list      待转换的List
     * @param maxLength 最大长度
     * @return 转换后的字符串
     */
    public static String listToStr(List list, Integer maxLength) {
        String str = listToStr(list);
        maxLength = maxLength == null ? DEFAULT_MAX_LENGTH : maxLength;
        if (str.length() > maxLength) {
            return str.substring(0, maxLength - 3) + REPLACE_SUFFIX;
        }
        return str;
    }

    /**
     * 添加BList 到 AList
     * 判断BList是否为空 如果非空 则添加
     *
     * @param sourceList 源List
     * @param appendList 添加的List
     */
    @SuppressWarnings({"unchecked"})
    static void appendList(List sourceList, List appendList) {
        if (appendList != null && appendList.size() > 0) {
            sourceList.addAll(appendList);
        }
    }

    /**
     * 添加BMap 到 AMap
     * 判断BMap是否为空 如果非空 则添加
     */
    @SuppressWarnings({"unchecked"})
    static void appendMap(Map sourceMap, Map appendMap) {
        if (appendMap != null && appendMap.size() > 0) {
            sourceMap.putAll(appendMap);
        }
    }

    /**
     * 判断List(Map(name:key,value:value)) 中key的个数
     */
    public static int getListMapKeyCount(List<Map<String, Object>> dataList, String key) {
        int count = 0;
        for (Map<String, Object> dataMap : dataList) {
            if (dataMap.get("name").equals(key)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 根据kye 获取 List(Map(key,value)) 中的Map
     */
    public static List<Map<String, Object>> getListMapByKey(List<Map<String, Object>> dataList, String key) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> dataMap : dataList) {
            if (dataMap.get("name").equals(key)) {
                resultList.add(dataMap);
            }
        }
        return resultList;
    }

    /**
     * 判断List 是否为空
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断Map 是否为空
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * map key下划线转驼峰
     *
     * @param dataMap 需要转换的结果集
     * @return 转换后的结果集
     */
    public static Map<String, Object> convertKeyToCamel(Map<String, Object> dataMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (!isEmpty(dataMap)) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                String key = StringUtils.underlineToCamelWord(entry.getKey(), true);
                resultMap.put(key, entry.getValue());
            }
        }
        return resultMap;
    }

    /**
     * map key驼峰转下划线
     *
     * @param dataMap 需要转换的结果集
     * @return 转换后的结果集
     */
    public static Map<String, Object> coverKeytoUnderLine(Map<String, Object> dataMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (!isEmpty(dataMap)) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                String key = StringUtils.camelToUnderlineWord(entry.getKey(), true);
                resultMap.put(key, entry.getValue());
            }
        }
        return resultMap;
    }
}
