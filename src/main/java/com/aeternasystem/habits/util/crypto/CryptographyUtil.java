package com.aeternasystem.habits.util.crypto;

import com.aeternasystem.habits.exception.CryptographyException;
import com.aeternasystem.habits.util.crypto.enums.EncodingType;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpStatus;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class CryptographyUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String SHA256 = "SHA-256";

    private CryptographyUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static byte[] getHmacSha256Bytes(String dataCheckString, byte[] secretKey) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, HMAC_SHA256);
            mac.init(secretKeySpec);

            return mac.doFinal(dataCheckString.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographyException("HMAC SHA256 algorithm is not available", e, HttpStatus.NOT_FOUND);
        } catch (InvalidKeyException e) {
            throw new CryptographyException("Invalid cryptographic key", e, HttpStatus.BAD_REQUEST);
        }
    }

    public static String calculateHash(String dataCheckString, byte[] secretKey, EncodingType encodingType) {
        byte[] hmacResult = getHmacSha256Bytes(dataCheckString, secretKey);
        return encodingType == EncodingType.HEX ? Hex.encodeHexString(hmacResult) : Base64.getEncoder().encodeToString(hmacResult);
    }

    public static byte[] getHmacSha256Bytes(String data, String secretKey) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKeySpec);
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographyException("HMAC SHA256 algorithm is not available", e, HttpStatus.NOT_FOUND);
        } catch (InvalidKeyException e) {
            throw new CryptographyException("Invalid cryptographic key", e, HttpStatus.BAD_REQUEST);
        }
    }

    public static byte[] getSha256Bytes(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
            return messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographyException("SHA-256 algorithm is not available", e, HttpStatus.NOT_FOUND);
        }
    }
}