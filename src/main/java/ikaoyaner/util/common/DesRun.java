package ikaoyaner.util.common;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 
 * <pre>
 * 功能描述：用户密码（加密、解密）
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-10-11
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
public class DesRun {

    public String enpsw = ""; // 加密
    public String depsw = ""; // 解密

    public DesRun(String usercode, String psw) {// 加密
        enpsw = encrypt(psw);
    }

    public DesRun(String psw) {// 解密
        depsw = decrypt(psw);
    }

    private String encrypt(String strPassword) {
        String pw = "";
        String rawKeyStr = "{k*g!r~d`1r]{x)(1%s^$@a^c&*";
        byte[] rawKeyData = rawKeyStr.getBytes();
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(rawKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            byte[] encryptedClassData = cipher.doFinal(strPassword.getBytes());
            pw = byte2hex(encryptedClassData);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return pw;
    }

    private String decrypt(String strPassword) {
        if (null == strPassword || ("".equals(strPassword))) {
            return "";
        }
        String pw = "";
        String rawKeyStr = "{k*g!r~d`1r]{x)(1%s^$@a^c&*";
        byte[] encryptedData = string2byte(strPassword);
        byte[] rawKeyData = rawKeyStr.getBytes();
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(rawKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            byte[] decryptedData = cipher.doFinal(encryptedData);
            pw = new String(decryptedData);

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return pw;
    }

    private byte[] string2byte(String strPassword) {
        String[] aHex = strPassword.split(":");
        byte[] b = new byte[aHex.length];
        for (int i = 0; i < aHex.length; i++) {
            try {
                b[i] = Integer.valueOf(aHex[i], 16).byteValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                b[i] = 0;
            }
        }
        return b;
    }

    private String byte2hex(byte[] b) {// 二行制转字符串

        String hs = "";
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }

        return hs.toUpperCase();
    }

    public static void main(String[] args) {
        DesRun desRun1 = new DesRun("39:19:61:7A:CE:8C:2F:A4:FA:82:6B:6D:CE:34:8D:2C");
        // String a = new DesRun("15fdsafdsa", "admin" + "123" + "nyz526").enpsw;
        // System.out.println(a);
        // DesRun desRun = new DesRun(a);
        DesRun desRun = new DesRun("", "888888");
        System.out.println(desRun.enpsw);
        System.out.println(desRun1.depsw);
    }
}
