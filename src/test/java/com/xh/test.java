package com.xh;

import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class test {
    @SneakyThrows
    @Test
    public void test1() throws NoSuchPaddingException, NoSuchAlgorithmException {
        DESKeySpec dks = new DESKeySpec("12345678".getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] res = cipher.doFinal("a12345678".getBytes());
        String s = byteArrayToHexStr(res);
        System.out.println(s);
    }

    @SneakyThrows
    @Test
    public void testDES() throws Exception {
        // 明文
        String plainText = "0000";
        System.out.println("明文：" + plainText);
        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        String s = byteArrayToHexStr(bytes);
        System.out.println(bytes);
        System.out.println(s);
//        [B@184fb68d
//        EEBD0A919A1E35F8
        // 提供原始秘钥:长度64位,8字节
        String originKey = "12345678";
        // 根据给定的字节数组构建一个秘钥
        SecretKeySpec key = new SecretKeySpec(originKey.getBytes(), "DES");

        // 加密
        // 1.获取加密算法工具类
        Cipher cipher = Cipher.getInstance("DES");
        // 2.对工具类对象进行初始化,
        // mode:加密/解密模式
        // key:对原始秘钥处理之后的秘钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 3.用加密工具类对象对明文进行加密
        byte[] encipherByte = cipher.doFinal(plainText.getBytes());
        // 防止乱码，使用Base64编码
        String encode= byteArrayToHexStr(encipherByte);
        System.out.println("加密：" + encode);

        // 解密
        // 2.对工具类对象进行初始化
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 3.用加密工具类对象对密文进行解密
        byte[] decipherByte = cipher.doFinal(encipherByte);
        String decipherText = new String(decipherByte);
        System.out.println("解密：" + decipherText);
    }


    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
