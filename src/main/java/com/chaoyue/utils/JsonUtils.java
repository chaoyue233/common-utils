package com.chaoyue.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json 相关操作工具类
 */

@Slf4j
public class JsonUtils {
    /**
     * 对象转json
     *
     * @param obj 被转换的json对象
     * @return json串
     */
    public static String objectToJSON(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("object to json error ", e);
        }
        return json;
    }

    /**
     * json串转换为Map<K,V>
     *
     * @param json 需要转换的json串
     * @return 转换所获得的Map
     */
    public static <K, V> Map<K, V> jsonToMap(String json) {
        return jsonToMap(json, false);
    }

    /**
     * json串转换为Map<K,V>
     *
     * @param json           需要转换的json串
     * @param isUsingDecimal 是否使用Decimal来转换
     * @return 转换所获得的Map
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <K, V> Map<K, V> jsonToMap(String json, boolean isUsingDecimal) {
        Map<K, V> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(json)) {
            return resultMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        if (isUsingDecimal) {
            //在财务等关键数字上，避免反系列化时默认使用的double精度问题
            mapper.enable(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS);
        }
        try {
            resultMap = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            log.error("json to map error ", e);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * json对象转换为List
     *
     * @param json 待转换的json对象
     * @param <T>  类型
     * @return 转换所获得的List
     */
    public static <T> List<T> jsonToList(String json) {
        List<T> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(json)) {
            return resultList;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            //noinspection unchecked
            resultList = mapper.readValue(json, List.class);
        } catch (IOException e) {
            log.error("json to map error ", e);
            return resultList;
        }
        return resultList;
    }

    /**
     * 将json转换为对象
     *
     * @param json       需要转换的json串
     * @param objectType 对象类型
     * @return 转换后所得的对象
     */
    public static <T> T toObject(String json, Class<T> objectType) {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return mapper.readValue(json, objectType);
        } catch (IOException e) {
            log.error("json to object error ", e);
        }
        return null;
    }
}
