package br.senac.projeto_pombo.auth;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.security.crypto.password.PasswordEncoder;

public class RSAPasswordEncoder implements PasswordEncoder {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public RSAPasswordEncoder(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public String encode(CharSequence rawText) {
        try {
            byte[] passwordBytes = rawText.toString().getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(passwordBytes);

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar com RSA", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawText, String encodedText) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encodedText);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            String decryptedPassword = new String(decryptedBytes, StandardCharsets.UTF_8);
            return rawText.toString().equals(decryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao decifrar com RSA", e);
        }
    }

}
