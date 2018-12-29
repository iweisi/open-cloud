package com.github.lyd.common.utils;

import java.util.Map;

/**
 * @author liuyadu
 */
public class BeanUtils extends org.springframework.beans.BeanUtils{
    /**
     * 
    * @Title: mapToObject
    * @Description: map转换为bean
    * @return T    返回类型
    * @param map
    * @param beanClass
    * @return
    * @throws Exception
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass){
        try {
            if (map == null) {
                return null;
            }
            T obj = beanClass.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);

            return obj;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 
    * @Title: objectToMap
    * @Description: bean转换为Map
    * @return Map<?,?>    返回类型
    * @param obj
    * @return
     */
    public static Map<?, ?> beanToMap(Object obj) {
        if(obj == null) {
             return null;   
        }
        return new org.apache.commons.beanutils.BeanMap(obj);  
    } 
}
