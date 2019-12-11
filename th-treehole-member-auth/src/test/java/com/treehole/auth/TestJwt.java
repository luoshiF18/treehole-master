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
        String publickey = "";
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoicWJsIn0.O3G_mGCbh5nh9rgEAREfPGYDKspBslrQYnpjyALGTtD9i0_Bi3-6xn8Aj-uMCON7afJF8D7y4wEFC3LkgMUPXLaqZlzsI12lpR-H2OXEDI64VRSML2UyBaEtYJT4S4aUd2zXPXhAQUQAsRzRuTrVPztnFeVi1k5WB8bToMBW1-iHBhH3vP8L-7d40HiPxYvCC4LGZGnHXp_4_DkWLLHcO5HjVXPAh-ImuKCsQ5PAX4jvZtvIlK0AxircuEiBbm-JXQKcrAvYAt0x1N1AqzXot7mbNHwRsJMfJAlIT70LnG1Vw28IZvVeWJD9pQxUXGhsuWd9oUp5WQh6GdIg9gZ_3A";
        //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
