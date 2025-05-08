package com.example.personnel_management_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;

@Configuration
public class KeyConfig {

    @Bean
    public RSAPublicKey publicKey() throws Exception {
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/keys/jwt.pub"))) {
            byte[] bytes = input.readAllBytes();
            String key = new String(bytes, StandardCharsets.UTF_8)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", ""); // Remove all whitespace/newlines

            byte[] decoded = java.util.Base64.getDecoder().decode(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
        }
    }

    @Bean
    public RSAPrivateKey privateKey() throws Exception {
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/keys/jwt.key"))) {
            byte[] bytes = input.readAllBytes();
            String key = new String(bytes, StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", ""); // Remove all whitespace/newlines

            byte[] decoded = java.util.Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
        }
    }
}
