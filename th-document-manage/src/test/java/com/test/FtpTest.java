package com.test;

import com.document.manage.utils.FtpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.OutputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootConfiguration
public class FtpTest {

    @Value("${vsftpd.host}")
    private String host;
    @Value("${vsftpd.port}")
    private int port;
    @Value("${vsftpd.username}")
    private String username;
    @Value("${vsftpd.password}")
    private String password;
    @Value("${vsftpd.basePath}")
    private String basePath;
    @Value("${vsftpd.filepath}")
    private String filepath;

    @Test
    public void test(){
        OutputStream outputStream = FtpUtil.downloadPort(host, port, username, password, basePath, "1576673191870911.docx");
        System.out.println(outputStream.hashCode());
    }

}
