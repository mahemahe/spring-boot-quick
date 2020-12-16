package quick.others;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Encoder;

/**
 * <p> TODO description </p>
 *
 * @author mahe <mahe@maihaoche.com>
 * @date 2020/12/4 周五 9:53 上午
 */
@Slf4j
public class RSAUtil {
    private static final int KEY_LENGTH = 1024;
    private static final String ALGORITHM = "RSA";
    private static final BASE64Encoder BASE64_ENCODER = new BASE64Encoder();
    public static final String CH168_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC29UVQvmi1BinConNSyiPjAlHGyffsaAFdCage\n" +
            "beVmz/b20glDl+7vPMsSNHfI46qMMc6sIFY9GitaYrSP5K7FOPwJc1IsGJ7msUqhJHt/dDUva2Yk\n" +
            "qqiZUhzgG24uhsKLpWleDU145WluX41KvNWPlxU6FbhdiIc/VkHTsKHzNwIDAQAB";

    public static final String CH168_PRIVATEKEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALb1RVC+aLUGKcKic1LKI+MCUcbJ\n" +
            "9+xoAV0JqB5t5WbP9vbSCUOX7u88yxI0d8jjqowxzqwgVj0aK1pitI/krsU4/AlzUiwYnuaxSqEk\n" +
            "e390NS9rZiSqqJlSHOAbbi6GwoulaV4NTXjlaW5fjUq81Y+XFToVuF2Ihz9WQdOwofM3AgMBAAEC\n" +
            "gYB0C13LuPnw19fk++TpvgzFD9OOosv9xnoW6OkZY9r9OysJom790IBcfdLP2Teg08Z45Z6QznHk\n" +
            "j5Sv36tnGUU//pkC/VQ7p3s4OHMh8x/40Q/bRD7aTDCAe0f6IEyfxcFaa/6M3gHHr5ODute9hhuC\n" +
            "eH5JCNgQOmG1Hlh5UEwuAQJBAPFWClJcSaNN8GSC2mjA/zJ/IogtsxpShgthfAiwTRVU0nFBrhSr\n" +
            "Tum4hu7xenu0xwtMgtS6sxmAzrkiDwtG9Q8CQQDCEyqLb1NokAcgkNOzovm/MbzAX94VfgDgM3mA\n" +
            "3WmCLEsnY3Hp9bBH/Q2VfVBdvy24HwZvGX72svtaoE/sdy9ZAkEA6emMLZHXqdnkE3ek+/18HFSP\n" +
            "gtBQlUXBOIyvLLi8rd0975pKgSwdW3mIB1a0ceyhM/lfoZKn/yMSuuwhCrwmzwJBAK+nETaqR9+3\n" +
            "m/vJmRJZS5nUtnWXTzY8mSZS8IHF5BzBYjQAObX7fNVrH8RRa0+uHP8sdXJQrS0zyXO47R2kseEC\n" +
            "QQCAG78JdCpMBuldSYDPNzSSk1ZZe71525XOgNJ5blAFVIgFCij88FrZs2ERN5LK/sVls37JXCF2\n" +
            "15FQFRWmpNPi";

    /**
     * 加密方法
     *
     * @param text         明文字符串
     * @param publicKeyStr 公钥字符串
     * @return 密文字符串
     */
    public static String encrypt(String text, String publicKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            KeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return encrypt(text, publicKey);
        } catch (Exception e) {
            log.error("公钥类型转换异常", e);
            return null;
        }
    }

    /**
     * 加密方法
     *
     * @param text      明文字符串
     * @param publicKey 公钥对象
     * @return 密文字符串
     */
    public static String encrypt(String text, PublicKey publicKey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] decryptedData = text.getBytes("UTF-8");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            int i = 0;
            // 对数据分段加密, 明文长度不能超过(密钥长度的字节数 - 11)
            while (decryptedData.length - offSet > 0) {
                byte[] cache;
                if ((decryptedData.length - offSet) > (KEY_LENGTH / 8 - 11)) {
                    cache = cipher.doFinal(decryptedData, offSet, KEY_LENGTH / 8 - 11);
                } else {
                    cache = cipher.doFinal(decryptedData, offSet, decryptedData.length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * (KEY_LENGTH / 8 - 11);
            }
            return BASE64_ENCODER.encode(out.toByteArray());
        } catch (Exception e) {
            log.error("加密发生异常", e);
            return null;
        }
    }


    /**
     * 解密方法
     *
     * @param text          密文字符串
     * @param keyStr 私/公钥字符串
     * @return 明文字符串
     */
    public static String decrypt(String text, String keyStr, Boolean isPrivateKey) {
        if (isPrivateKey) {
            return decryptByPrivate(text, keyStr);
        } else {
            return decryptByPublic(text, keyStr);
        }
    }

    /**
     * 解密方法
     *
     * @param text          密文字符串
     * @param privateKeyStr 私钥字符串
     * @return 明文字符串
     */
    public static String decryptByPrivate(String text, String privateKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return decrypt(text, privateKey);
        } catch (Exception e) {
            log.error("私钥类型转换发生异常", e);
            return null;
        }
    }

    /**
     * 解密方法
     *
     * @param text       密文字符串
     * @param privateKey 私钥对象
     * @return 明文字符串
     */
    public static String decrypt(String text, PrivateKey privateKey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding", "BC");
            //Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 此处获得字节数组的方式必须与加密处一致, 不然会引起解密失败
            byte[] encryptedData = Base64.decodeBase64(text);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            int i = 0;
            // 对数据分段解密
            while (encryptedData.length - offSet > 0) {
                byte[] cache;
                if ((encryptedData.length - offSet) > (KEY_LENGTH / 8)) {
                    cache = cipher.doFinal(encryptedData, offSet, KEY_LENGTH / 8);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, encryptedData.length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * (KEY_LENGTH / 8);
            }
            return out.toString("UTF-8");
        } catch (Exception e) {
            log.error("私钥解密发生异常", e);
            return null;
        }
    }


    /**
     * 解密方法
     *
     * @param text          密文字符串
     * @param publicKeyStr 私钥字符串
     * @return 明文字符串
     */
    public static String decryptByPublic(String text, String publicKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            KeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return decrypt(text, publicKey);
        } catch (Exception e) {
            log.error("公钥类型转换发生异常", e);
            return null;
        }
    }

    /**
     * 解密方法
     *
     * @param text       密文字符串
     * @param publicKey 私钥对象
     * @return 明文字符串
     */
    public static String decrypt(String text, PublicKey publicKey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding", "BC");
            //Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            // 此处获得字节数组的方式必须与加密处一致, 不然会引起解密失败
            byte[] encryptedData = Base64.decodeBase64(text);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            int i = 0;
            // 对数据分段解密
            while (encryptedData.length - offSet > 0) {
                byte[] cache;
                if ((encryptedData.length - offSet) > (KEY_LENGTH / 8)) {
                    cache = cipher.doFinal(encryptedData, offSet, KEY_LENGTH / 8);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, encryptedData.length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * (KEY_LENGTH / 8);
            }
            return out.toString("UTF-8");
        } catch (Exception e) {
            log.error("公钥解密发生异常", e);
            return null;
        }
    }
}
