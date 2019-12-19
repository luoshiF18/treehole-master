package com.treehole.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {

    //创建jwt令牌
    @Test
    public void testCreateJwt(){
        //密钥库文件
        String keystore = "th.keystore";
        //密钥库的密码
        String keystore_password = "treeholekeystore";

        //密钥库文件路径
        ClassPathResource classPathResource = new ClassPathResource(keystore);
        //密钥别名
        String alias  = "thkey";
        //密钥的访问密码
        String key_password = "treehole";
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,keystore_password.toCharArray());
        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, key_password.toCharArray());
        //获取私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        //jwt令牌的内容
        Map<String,String> body = new HashMap<>();
        body.put("name","qbl");
        String bodyString = JSON.toJSONString(body);
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(bodyString, new RsaSigner(aPrivate));
        //生成jwt令牌编码
        String encoded = jwt.getEncoded();
        System.out.println(encoded);

    }

    //校验jwt令牌
    @Test
    public void testVerify(){
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh/hjv9+POhQnf8CkWoX1OGDSPGRPs1JXjXXebVbMf+mKQa0wKeakqUkwdztYF/cMZjHGAbQACdG3qlu5Ic4PoHdwhY8rVFi1a61Qnr4jV0ij2dPaqeOJ5iJ3zcvw3ONyCi32U/K/MuwIvyexdEUhxYTmNg3EmP1Xc/gqToNXQAEVPR5eSlxijKKGnWJ7T6+aSubNkEOeyNLk/BccHAlkDZh3DKLivrfOdiZnhwHYuZgzeVqg/5S+ez5rgFJQ6YTeku/C+JtZZOh723fQytaDFiwf9DDzO8Tj55XmLuKNDpNOQ+miYv1ro08QJKyg5KIh0Ntbb2ZOwv7byduPqpVEvwIDAQAB-----END PUBLIC KEY-----";
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoicWJsIn0.b9gFFOIbG3O-88zLGYPD47nKQkh9EikcEJ_BTgOEK37NVtrS8vRIPWuy6l3aqWAY0816_0guHsOkmD2AK1WtZbZM3k0YBUXCrsB5Vor-Bl7w0VapC6pioIAqYulKlxoI2wdqyxQbD2zzidf0Xa_yNjOjEEzNYEqx2JjIBiVRMDih6RtN2r7LRUZbFl4E-kzuB_6sGny0nDGzYaDqDThZsX52WHdyTh2gl1LwHte3b02khITTQTxO4gRX2ZfdxTDhHyV1D8vg_neDye3k3phZtM2WiYXI6O50ipjcwNH8x95-AQgUXKYnGiMrCuz_OVImvNCgr5v1f87huBTrsZAyJA";
        //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
