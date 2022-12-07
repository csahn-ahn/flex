package me.univ.flex.common.crypto;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.config.ApplicationContextServe;
import org.springframework.context.ApplicationContext;

@Slf4j
public class AES256Crypto {

  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding"; //aes-256-cbc
  private static final int KEY_LENGTH = 256;
  private static final int ITERATION_COUNT = 1000;
  private static final String KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";
  private static String SECRET_KEY;

  static {
    ApplicationContext applicationContext = ApplicationContextServe.getApplicationContext();
    // Test 코드 실행시에만 context 가 null 이므로 테스트용(1회 메모리용) 암호키를 셋팅.
    if (applicationContext == null) {
      SECRET_KEY = "cRBEboDiI6BWJElDVzswipbBJNJBivSD";
    }
    // 정상인 경우
    else {
      SECRET_KEY = applicationContext.getEnvironment()
          .getProperty("security.key.aes256");
    }
  }

  public static String encrypt(String plainString) {
    if (plainString == null || plainString.isEmpty()) {
      return plainString;
    }

    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.ENCRYPT_MODE, getKeyFromUid(SECRET_KEY), getIvFromUid(SECRET_KEY));
      byte[] encrypted = cipher.doFinal(plainString.getBytes(StandardCharsets.UTF_8));
      return new String(Base64.getEncoder().encode(encrypted));
    } catch (Exception e) {
      return plainString;
    }
  }

  public static String decrypt(String encryptedString) {
    if (encryptedString == null || encryptedString.isEmpty()) {
      return encryptedString;
    }

    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.DECRYPT_MODE, getKeyFromUid(SECRET_KEY), getIvFromUid(SECRET_KEY));
      byte[] decrypted = Base64.getDecoder()
          .decode(encryptedString.getBytes(StandardCharsets.UTF_8));
      return new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
    } catch (Exception e) {
      return encryptedString;
      //throw new RuntimeException(e);
    }
  }

  public static SecretKey getKeyFromUid(String uid)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
    KeySpec spec = new PBEKeySpec(uid.toCharArray(),
        uid.substring(4, 12).getBytes(StandardCharsets.UTF_8),
        ITERATION_COUNT,
        KEY_LENGTH
    );
    return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
  }

  public static IvParameterSpec getIvFromUid(String uid) {
    return new IvParameterSpec(uid.substring(14, 30).getBytes(StandardCharsets.UTF_8));
  }
}
