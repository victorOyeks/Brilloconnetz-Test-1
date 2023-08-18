package org.example;

import java.util.*;
import java.util.regex.*;
import java.time.*;
import io.jsonwebtoken.*;

public class UserRegistrationApp {

    private static final String SECRET_KEY = "472B4B6250655368566D5971337336763979244226452948404D635166546A57\n";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Date of Birth (yyyy-MM-dd): ");
        String dobStr = scanner.nextLine();

        // Validate and collect errors
        Map<String, String> errors = new HashMap<>();

        if (!isValidUsername(username)) {
            errors.put("Username", "not empty, min 4 characters");
        }

        if (!isValidEmail(email)) {
            errors.put("Email", "not empty, valid email address");
        }

        if (!isValidPassword(password)) {
            errors.put("Password", "not a strong password");
        }

        if (!isValidDOB(dobStr)) {
            errors.put("Date of Birth", "not empty, should be 16 years or greater");
        }

        if (!errors.isEmpty()) {
            System.out.println("Validation failed:");
            errors.forEach((field, message) -> System.out.println(field + ": " + message));
        } else {
            System.out.println("All fields are valid.");
            String jwt = generateJWT(username);
            System.out.println("Generated JWT: " + jwt);

            String verificationStatus = verifyJWT(jwt) ? "verification pass" : "verification fails";
            System.out.println("Verification status: " + verificationStatus);
        }
    }

    // Validation methods
    private static boolean isValidUsername(String username) {
        return username != null && username.length() >= 4;
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    private static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*!]).{8,}$";
        return password != null && Pattern.matches(passwordRegex, password);
    }

    private static boolean isValidDOB(String dobStr) {
        LocalDate dob = LocalDate.parse(dobStr);
        LocalDate sixteenYearsAgo = LocalDate.now().minusYears(16);
        return dob.isBefore(sixteenYearsAgo);
    }

    // JWT methods
    static String generateJWT(String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    static boolean verifyJWT(String jwt) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
