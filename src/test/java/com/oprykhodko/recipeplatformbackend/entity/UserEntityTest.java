package com.oprykhodko.recipeplatformbackend.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class UserEntityTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenUsernameIsTooShort() {
        User user = new User("ab", "test@example.com", "hashedPassword123", "Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("username");
    }

    @Test
    void shouldFailValidationWhenUsernameIsTooLong() {
        String longUsername = "a".repeat(51);
        User user = new User(longUsername, "test@example.com", "hashedPassword123", "Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("username");
    }

    @Test
    void shouldFailValidationWhenUsernameIsBlank() {
        User user = new User("", "test@example.com", "hashedPassword123", "Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(2);
    }

    @Test
    void shouldFailValidationWhenEmailIsInvalid() {
        User user = new User("testuser", "invalid-email", "hashedPassword123", "Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }

    // Referring to https://datatracker.ietf.org/doc/html/rfc5321#section-4.5.3.1 local part can't be more 64 octets
    // and domain name or number is not more 255 octets.
    @Test
    void shouldFailValidationWhenEmailIsTooLong() {
        String longEmail = "a".repeat(64) + "@" + "e".repeat(50) + ".com";
        User user = new User("testuser", longEmail, "hashedPassword123", "Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    void shouldFailValidationWhenPasswordHashIsBlank() {
        User user = new User("testuser", "test@example.com", "", "Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("passwordHash");
    }

    @Test
    void shouldPassValidationWithValidData() {
        User user = new User("testuser", "test@example.com", "validHashedPassword123", "Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }
}