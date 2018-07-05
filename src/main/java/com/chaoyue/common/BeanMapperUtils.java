package com.chaoyue.common;

import com.github.pagehelper.PageInfo;
import org.dozer.DozerBeanMapper;

import java.util.*;

public class BeanMapperUtils {
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    // 基于Dozer转换对象的类型
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source != null) {
            return dozer.map(source, destinationClass);
        }
        return null;
    }

    // 转换并将参数名下划线转成驼峰
    @SuppressWarnings("unchecked")
    public static <T> T mapToCamel(Object source, Class<T> destinationClass) {
        if (source != null) {
            Map<String, Object> dataMapWithObjectId = map(source, HashMap.class);
            Map<String, Object> dataMapWithList = JsonUtils.jsonToMap(JsonUtils.objectToJSON(source));
            for (Map.Entry<String, Object> entry : dataMapWithObjectId.entrySet()) {
                if (entry.getValue() != null) {
                    dataMapWithList.put(entry.getKey(), entry.getValue());
                }
            }
            return map(CollectionUtils.convertKeyToCamel(dataMapWithList), destinationClass);
        }
        return null;
    }

    /**
     * 转换Collection类型内容
     *
     * @param sourceList       待转换的collection类型
     * @param destinationClass 类型信息
     * @return 转换后的collection 类型
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 转换Page类型内容
     *
     * @param sourcePage       待转换的page类型
     * @param destinationClass 类型信息
     * @return 转换后的page类型
     */
    @SuppressWarnings("unchecked")
    public static <T> PageInfo<T> mapPage(PageInfo sourcePage, Class<T> destinationClass) {
        List destinationList = new ArrayList();
        for (Object sourceObject : sourcePage.getList()) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        sourcePage.setList(destinationList);
        return sourcePage;
    }

    // 基于Dozer将对象A的值拷贝到对象B中
    public static <T> T copy(Object source, T destinationObject) {
        if (source != null) {
            dozer.map(source, destinationObject);
            return destinationObject;
        }
        return null;
    }
}
