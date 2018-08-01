package top.meem.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import com.icbc.crypto.utils.Base64;
import com.icbc.crypto.utils.SHA1;
import com.icbc.crypto.utils.TripleDesCryptVarKey2;

/**
 * 字符加解密
 * @author kfzx-xiebb
 **/
public class ThreeDESClass {

    //字符编码
    public final static String ENCODING = "GBK";

    /**
     * 字符串加密
     * @param plainText 明文
     * @return cipherText 密文
     * @throws UnsupportedEncodingException
     */
    public static byte[] encrypt(byte[] plainText, byte[] cipherKey) throws UnsupportedEncodingException {
        byte[] cipherText = new byte[plainText.length];

        byte[] asciiCipherKey = TripleDesCryptVarKey2.Ascii2Text(cipherKey).getBytes(ENCODING);
        int ret = TripleDesCryptVarKey2.TripleDesCFB0(plainText, plainText.length, cipherText, 0, asciiCipherKey);
        return cipherText;
    }

    /**
     * 字符串解密
     * @param cipherText 密文
     * @param cipherKey 密钥
     * @return plainText 明文
     * @throws UnsupportedEncodingException
     */
    public static byte[] decrypt(byte[] cipherText, byte[] cipherKey) throws UnsupportedEncodingException {
        byte[] out = new byte[cipherText.length];
        byte[] asciiCipherKey = TripleDesCryptVarKey2.Ascii2Text(cipherKey).getBytes(ENCODING);
        int ret = TripleDesCryptVarKey2.TripleDesCFB0(cipherText, cipherText.length, out, 1, asciiCipherKey);
        return out;
    }


    /**
     * 生成密钥
     * @param size 位数
     * @return bytes[] 密钥
     * @throws NoSuchAlgorithmException
     */
    public static byte[] createCipher(int size) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("3DES");
        kg.init(size);
        return kg.generateKey().getEncoded();
    }

    /**
     * 摘要处理
     * @param data 待摘要数据
     * @return String 摘要字符串
     */
    public static String shaHex(byte[] data) {
        SHA1 sha = new SHA1();
        return sha.getDigestOfString(data);
    }

    /**
     * 验证
     * @param data 待摘要数据
     * @param messageDigest 摘要字符串
     * @return  验证结果
     */
    public static boolean validata(byte[] data, String messageDigest) {
        SHA1 sha = new SHA1();
        return messageDigest.equals(sha.getDigestOfString(data));
    }
}
