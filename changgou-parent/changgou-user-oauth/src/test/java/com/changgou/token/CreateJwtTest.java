package com.changgou.token;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:42
 * @Description: com.changgou.token
 *      创建JWT令牌，使用私钥加密
 ****/
public class CreateJwtTest {

    /***
     * 创建令牌测试
     */
    @Test
    public void testCreateToken(){
        //证书文件路径
        java.lang.String key_location="changgou.jks";
        //秘钥库密码
        java.lang.String key_password="changgou";
        //秘钥密码
        java.lang.String keypwd = "changgou";
        //秘钥别名
        String alias = "changgou";

        //访问证书路径 读取jks的文件
        ClassPathResource resource = new ClassPathResource(key_location);

        //创建秘钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource,key_password.toCharArray());

        //读取秘钥对(公钥、私钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias,keypwd.toCharArray());

        //获取私钥
        RSAPrivateKey rsaPrivate = (RSAPrivateKey) keyPair.getPrivate();

        //创建令牌,需要私钥加盐[RSA算法]
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("nikename", "tomcat");
        tokenMap.put("address", "sz");
        tokenMap.put("authorities", new String[]{"admin","oauth"});

        //生成Jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivate));


        //取出令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }

    @Test
    public void testParseToken() throws IOException {

        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoic3oiLCJuaWtlbmFtZSI6InRvbWNhdCIsImF1dGhvcml0aWVzIjpbImFkbWluIiwib2F1dGgiXX0.XiyjJKs7w8vcpx791h3cpBsO_gOkE-h7OByAraLN_HpVOavch3ip-euLcx6v27Kzo5tapkY5JgShroZc1zBWUlTrRwRuZACHDMhdpWZbblka_wRYIES_IrUxjBy6_TM1BMB8cGv5cWI5xwzSWkAn5ujEpqTdGMo_qZ-n-gs2s_SthZH5sI_G2K-ifpugRd6PQYM8ZS5vZWyeF1wJ5aJUg1fXj36U6dkWhjICBUaicX4y1tYKLSud81XA0GEUSlI6RSUuC9yxFckDCd8GvCcQIJMABBGuE5WNzjHl62NRnckfkCzm_1X3sXq_e76At_TH5C870mh1wg5IOLqgCQhfkg";

        String publickey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlNkwDP0GwOJe1zFbDfFci+pl72TB1DKEXJAWvbEnnJNJ++4puNKqRlTLRmhbbEx59jpwsUizYQsq5CfXbF3L1Rx2al8i2zrADRJWelxfbX+YsQD365ik5ARWGO1PC0Oa1yQsbwLtS5LdOtRTznootsklejOXtnmt5o+Sbt9NquDgdBgNe7VGMPWaBgHiUvI9yLKgTEWHSEe1zLFpBOtLVRlNWwK3NYLI7SrwYULyk3pFddKW1nimahLEjEuA6gKuZrUb5XVfXqM2D9pUqH5in+JAAMq6zWqC7Lw9hXVooucbSjbmTuAhx0YCwqsed9h8JT2CTDTzlXBASicz1o5sYwIDAQAB-----END PUBLIC KEY-----";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }


}
