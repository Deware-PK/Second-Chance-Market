package com.github.dewarepk.model;

import java.security.SecureRandom;

public class RandomKeyGenerator {

    /**
     * Characters used in Base58 encoding (exclude 0, O, I, l to avoid confusion)
     */
    private static final String BASE58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    /**
     * SecureRandom provides a cryptographically strong random number generator
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Method to generate random Base58 string
     *
     * @param length
     * @return
     */
    public static String generateKey(int length) {
        StringBuilder result = new StringBuilder(length);

        // Generate random characters from Base58 alphabet
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(BASE58_ALPHABET.length());
            result.append(BASE58_ALPHABET.charAt(randomIndex));
        }

        return result.toString();
    }
}
