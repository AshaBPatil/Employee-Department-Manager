package com.example.personnel_management_service;

import java.io.FileOutputStream;
import java.security.*;

public class KeyGeneratorUtil {
    public static void main(String[] args) throws Exception {
        // Generate RSA key pair
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // Get encoded bytes
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();

        // Write to files
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/keys/jwt.key")) {
            fos.write(privateKeyBytes);
        }
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/keys/jwt.pub")) {
            fos.write(publicKeyBytes);
        }

        System.out.println("✅ RSA key pair generated.");
    }
}
