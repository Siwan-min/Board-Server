package com.fastcampus.boardserver.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptUtil {

    private static final String KEY = "0123456789abcdef0123456789abcdef"; // 32byte
    private static final String IV = "abcdef9876543210"; // 16byte

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }
}