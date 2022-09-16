package com.purple.security;

public interface Hasher {
    String computeHash(String clearTextPassword, byte[] salt);

    byte[] generateRandomSalt();
}
