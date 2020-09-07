package com.dataint.cloud.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JSONUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json --> JavaBean
     * @param jsonStr
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> valueType) {
        if (StringUtils.isEmpty(jsonStr))
            return null;

        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (JsonMappingException e) {
            log.error("jsonToBean() occurred JsonMappingException: ", e);
        } catch (JsonProcessingException e) {
            log.error("jsonToBean() occurred JsonProcessingException: ", e);
        }

        return null;
    }

    /**
     * json --> array
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T jsonToArray(String jsonStr, TypeReference<T> valueTypeRef){
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (JsonMappingException e) {
            log.error("jsonToArray() occurred JsonMappingException: ", e);
        } catch (JsonProcessingException e) {
            log.error("jsonToArray() occurred JsonProcessingException: ", e);
        }

        return null;
    }

    /**
     * JavaBean --> json
     *
     * @param object
     * @return
     */
    public static String toJSon(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJSon() occurred JsonProcessingException: ", e);
        }

        return null;
    }
}
