package com.example.tracking.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtil {

    private static final String SALT = "qrtrack2026"; // Salt fixo simples

    public static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String salted = value + SALT;
            byte[] hash = digest.digest(salted.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String s = Integer.toHexString(0xff & b);
                if (s.length() == 1) {
                    hex.append('0');
                }
                hex.append(s);
            }

            return hex.toString();

        } catch (java.security.NoSuchAlgorithmException | java.lang.RuntimeException e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }
}
