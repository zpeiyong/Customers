package com.dataint.cloud.common.utils;

import com.dataint.cloud.common.model.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @description: 基于Jackson的jwt工具包
 * @author: Jiangxc
 * @create: 2020-06-28
 **/
@Slf4j
public class JWTUtil {

    public static ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * 生成key
     * @return
     */
    private static SecretKey generalKey() {
        String stringKey = Constants.JWT_KEY;
        byte[] encodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 生成jwt
     * @param claims
     * @param subject
     * @return
     */
    public static String generateToken(Map<String, Object> claims, String subject){
        SecretKey key = generalKey();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRE_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

//    public static String refreshToken(String token) {
//        String refreshedToken;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            claims.put(CLAIM_KEY_CREATED, new Date());
//            refreshedToken = generateToken(claims);
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }

    /**
     *
     * @param token
     * @param detailName
     * @return
     */
    public static Boolean validateToken(String token, String detailName) {
        final String username = getUserName(token);
        final Date expiredTime = getExpiredTime(token);
        return (
                username.equals(detailName)
                        && !isTokenExpired(expiredTime));
//                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    /**
     *
     * @param expiredTime
     * @return
     */
    public static Boolean isTokenExpired(Date expiredTime) {
        return expiredTime.before(new Date());
    }

    /**
     * token --> username,userId
     * @param token
     * @return
     */
    public static Map<String, Object> getUserIdAndName(String token) {
        Map<String, Object> mapTypes = parseJWTPublic(token);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("username", mapTypes.get("sub"));
        resultMap.put("userId", mapTypes.get("userId"));

        return resultMap;
    }

    /**
     * token --> username
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        Map<String, Object> mapTypes = parseJWTPublic(token);
        return (String) mapTypes.get("sub");
    }

    /**
     * token --> userId
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        Map<String, Object> mapTypes = parseJWTPublic(token);
        return Long.valueOf((Integer) mapTypes.get("userId"));
    }

    /**
     * token --> systemType
     * @param token
     * @return
     */
    public static String getSystemType(String token) {
        Map<String, Object> mapTypes = parseJWTPublic(token);
        return (String) mapTypes.get("systemType");
    }

    /**
     * token --> expired time
     * @param token
     * @return
     */
    public static Date getExpiredTime(String token) {
        Map<String, Object> mapTypes = parseJWTPublic(token);
        Integer timeInt = (Integer) mapTypes.get("exp");
        return new Date(Long.valueOf(timeInt) * 1000);
    }

    /**
     * token --> resourceIds
     * @param token
     * @return
     */
    public static Set<String> getResourceIds(String token) {
        Map<String, Object> mapTypes = parseJWTPublic(token);
        List<String> audList = castList(mapTypes.get("aud"), String.class);
        Set<String> auds = new HashSet<>(audList);
        return auds;

    }

    /**
     * token --> roleSet
     * @param token
     * @return
     */
    public static Set<String> getRoles(String token) {
        Map<String, Object> mapTypes = parseJWTPublic(token);
        List<String> authorityList = castList(mapTypes.get("authorities"), String.class);
        Set<String> roles = new HashSet<>(authorityList);
        return roles;
    }

    private static Map<String, Object> parseJWTPublic(String token) {
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
        Map<String, Object> map = null;
        try {
            map = jsonMapper.readValue(payload, Map.class);
        } catch (JsonProcessingException e) {
            log.error("jwt解析错误!");
            e.printStackTrace();
        }

        return map;
    }

    private static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
