package com.utils.rsa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RSAEncrypt {

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> getKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        HashMap<String, String> keyPairStr = new HashMap<>();
        keyPairStr.put("publicKeyString", publicKeyString);
        keyPairStr.put("privateKeyString", privateKeyString);
        return keyPairStr;
    }

    /**
     * RSA公钥加密
     *
     * @param msg
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static String rsaEncrypt(String msg, String publicKeyStr) throws Exception {
        //base64编码的公钥
        byte[] publicKeyStrBytes = Base64.decodeBase64(publicKeyStr);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyStrBytes));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param msg
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String rsaDecrypt(String msg, String privateKeyStr) throws Exception {
        //64位解码加密后的字符串
        byte[] msgBytes = Base64.decodeBase64(msg.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] privateKeyStrBytes = Base64.decodeBase64(privateKeyStr);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyStrBytes));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        String outStr = new String(cipher.doFinal(msgBytes));
        return outStr;
    }

    /**
     * 通过项目下密钥文件拿到相应密钥
     *
     * @param publicKeyBoolean
     * @param privateKeyBoolean
     * @param filename
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public Object getKey(boolean publicKeyBoolean, boolean privateKeyBoolean, String filename) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(filename);
        DataInputStream dis = new DataInputStream(resourceAsStream);
        try {
            byte[] keyBytes = new byte[]{};
            if (resourceAsStream != null &&
                    resourceAsStream.available() != 0) {
                keyBytes = new byte[resourceAsStream.available()];
                dis.readFully(keyBytes);
            }
            if (publicKeyBoolean) {
                PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyBytes));
                return publicKey;
            } else if (privateKeyBoolean) {
                PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
                return privateKey;
            }
            return null;
        } finally {
            try {
                if (null != dis) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * BCryptPasswordEncoder 类
     */
    private void bCryptPasswordEncoder() {
        String message = "asdfghjkl";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        String enPassword = bCryptPasswordEncoder.encode(message);
        boolean matches = bCryptPasswordEncoder.matches(message, enPassword);
        System.out.println(matches);
    }

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        Map<String, String> keyPair = getKeyPair();
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyPair.get("publicKeyString"));
        System.out.println("随机生成的私钥为:" + keyPair.get("privateKeyString"));
        String messageEn = rsaEncrypt(message, keyPair.get("publicKeyString"));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = rsaDecrypt(messageEn, keyPair.get("privateKeyString"));
        System.out.println("还原后的字符串为:" + messageDe);


    }
}
