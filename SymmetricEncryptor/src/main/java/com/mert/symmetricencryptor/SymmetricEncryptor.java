package com.mert.symmetricencryptor;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricEncryptor {

    private static SymmetricEncryptor instance;

    private String saltKey;

    public static SymmetricEncryptor sharedInstance() {
        if (instance == null) {
            instance = new SymmetricEncryptor();
        }
        return instance;
    }

    public void init(String saltKey) {
        this.saltKey = saltKey;
    }

    public String encryptData(String clearData, String password) {
        try {
            byte[] clearBytes = clearData.getBytes(Constants.kEncryptionEncoding);
            byte[][] keyAndIV = generateDerivedKeyAndIV(password);
            return  Base64.encodeToString(doFinal(Cipher.ENCRYPT_MODE, clearBytes, keyAndIV[0], keyAndIV[1]), Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptData(String encryptedData, String password) {
        try {
            byte[] clearBytes = Base64.decode(encryptedData.getBytes(), Base64.DEFAULT);
            byte[][] keyAndIV = generateDerivedKeyAndIV(password);
            return new String(doFinal(Cipher.DECRYPT_MODE, clearBytes, keyAndIV[0], keyAndIV[1]), Constants.kEncryptionEncoding);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[][] generateDerivedKeyAndIV(String password) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] salt = this.saltKey.getBytes("UTF-8");
        SecretKeyFactory factory = SecretKeyFactory.getInstance(Constants.kEncryptionAlgorithm);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, (int)Math.pow(10,3), 3 * (int)Math.pow(2,7));
        Key secretKey = factory.generateSecret(pbeKeySpec);
        byte[] key = new byte[32];
        byte[] iv = new byte[16];
        System.arraycopy(secretKey.getEncoded(), 0, key, 0, 32);
        System.arraycopy(secretKey.getEncoded(), 32, iv, 0, 16);
        return new byte[][]{key, iv};
    }

    private static byte[] doFinal(int cipherMode, byte[] clearBytes, byte[] key, byte[] IV) {
        try {
            SecretKeySpec secret = new SecretKeySpec(key, Constants.kSecretKeySpecAlgorithm);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV);
            Cipher cipher = Cipher.getInstance(Constants.kTransformation);
            cipher.init(cipherMode, secret, ivSpec);
            return cipher.doFinal(clearBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
