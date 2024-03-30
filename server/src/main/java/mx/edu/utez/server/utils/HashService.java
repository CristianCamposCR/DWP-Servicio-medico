package mx.edu.utez.server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class HashService {

    @Value("${hash.secret}")
    private String secretKey;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = new byte[16];

    private Cipher getCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKey key = generateSecretKeyFromString();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        cipher.init(mode, key, ivParams);
        return cipher;
    }

    private SecretKeySpec generateSecretKeyFromString() throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(Object data) {
        try {
            String plaintext = Methods.convertToString(data);
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public String decrypt(String encryptedText) {
        try {
            Validations.validateInput(encryptedText);
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }


    public Long decryptId(String encryptedId) {
        try {
            Validations.validateInput(encryptedId);
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedId));
            String idStr = new String(decryptedBytes, StandardCharsets.UTF_8);
            return Long.parseLong(idStr);
        } catch (Exception e) {
            return null;
        }
    }
}
