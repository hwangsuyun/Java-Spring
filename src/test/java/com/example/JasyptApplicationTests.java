package com.example;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptApplicationTests {
    @Test
    void contextLoads() {}

    @Test
    void jasypt() {
        String url = "my_db_url";
        String username = "my_db_username";
        String password = "my_db_password";

        System.out.println("url : " + jasyptEncoding(url));
        System.out.println("username : " + jasyptEncoding(username));
        System.out.println("password : " + jasyptEncoding(password));
    }

    public String jasyptEncoding(String value) {
        String key = "my_jasypt_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}
