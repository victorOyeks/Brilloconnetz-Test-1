package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegistrationAppTest {
    @Test
    public void testValidJWT() {
        String jwt = UserRegistrationApp.generateJWT("testuser");
        assertTrue(UserRegistrationApp.verifyJWT(jwt));
    }

    @Test
    public void testInvalidJWT() {
        assertFalse(UserRegistrationApp.verifyJWT("invalid.jwt.token"));
    }
}
