package com.oprykhodko.recipeplatformbackend.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecipeEntityTest {

    private static Validator validator;
    private static User testUser;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        testUser = new User("testuser", "test@example.com", "hashedPassword123", "Test User");
    }

    @Test
    void shouldFailValidationWhenTitleIsBlank() {
        Recipe recipe = new Recipe("", "Description", 30, 45, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("title");
    }

    @Test
    void shouldFailValidationWhenTitleIsTooLong() {
        String longTitle = "a".repeat(201);
        Recipe recipe = new Recipe(longTitle, "Description", 30, 45, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("title");
    }

    @Test
    void shouldFailValidationWhenPrepTimeIsNegative() {
        Recipe recipe = new Recipe("Valid Title", "Description", -1, 45, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("prepTimeMinutes");
    }

    @Test
    void shouldFailValidationWhenCookTimeIsNegative() {
        Recipe recipe = new Recipe("Valid Title", "Description", 30, -1, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("cookTimeMinutes");
    }

    @Test
    void shouldFailValidationWhenServingsIsZero() {
        Recipe recipe = new Recipe("Valid Title", "Description", 30, 45, 0, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("servings");
    }

    @Test
    void shouldPassValidationWithValidData() {
        Recipe recipe = new Recipe("Delicious Pasta", "A tasty pasta recipe", 15, 30, 4, Difficulty.MEDIUM, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenConstructorCalledWithNullDifficulty() {
        assertThatThrownBy(() -> new Recipe("Valid Title", "Description", 30, 45, 4, null, testUser))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldFailValidationWhenDescriptionIsBlank() {
        Recipe recipe = new Recipe("Valid Title", "", 30, 45, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("description");
    }

    @Test
    void shouldFailValidationWhenDescriptionIsTooLong() {
        String longDescription = "a".repeat(1001);
        Recipe recipe = new Recipe("Valid Title", longDescription, 30, 45, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("description");
    }

    @Test
    void shouldFailValidationWhenServingsIsNegative() {
        Recipe recipe = new Recipe("Valid Title", "Description", 30, 45, -1, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("servings");
    }

    @Test
    void shouldThrowExceptionWhenConstructorCalledWithNullUser() {
        assertThatThrownBy(() -> new Recipe("Valid Title", "Description", 30, 45, 4, Difficulty.EASY, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("User cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConstructorCalledWithNullTitle() {
        assertThatThrownBy(() -> new Recipe(null, "Description", 30, 45, 4, Difficulty.EASY, testUser))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Title cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenConstructorCalledWithNullDescription() {
        assertThatThrownBy(() -> new Recipe("Valid Title", null, 30, 45, 4, Difficulty.EASY, testUser))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Description cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenSetterCalledWithNullTitle() {
        Recipe recipe = new Recipe("Valid Title", "Description", 30, 45, 4, Difficulty.EASY, testUser);
        
        assertThatThrownBy(() -> recipe.setTitle(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Title cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenSetterCalledWithNullDescription() {
        Recipe recipe = new Recipe("Valid Title", "Description", 30, 45, 4, Difficulty.EASY, testUser);
        
        assertThatThrownBy(() -> recipe.setDescription(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Description cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenSetterCalledWithNullUser() {
        Recipe recipe = new Recipe("Valid Title", "Description", 30, 45, 4, Difficulty.EASY, testUser);
        
        assertThatThrownBy(() -> recipe.setUser(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("User cannot be null");
    }

    @Test
    void shouldPassValidationWithMinimumValidValues() {
        Recipe recipe = new Recipe("A", "B", 0, 0, 1, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithMaximumValidTitleLength() {
        String maxTitle = "a".repeat(200);
        Recipe recipe = new Recipe(maxTitle, "Description", 30, 45, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithMaximumValidDescriptionLength() {
        String maxDescription = "a".repeat(1000);
        Recipe recipe = new Recipe("Valid Title", maxDescription, 30, 45, 4, Difficulty.EASY, testUser);

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithAllDifficultyLevels() {
        Recipe easyRecipe = new Recipe("Easy Recipe", "Description", 10, 15, 2, Difficulty.EASY, testUser);
        Recipe mediumRecipe = new Recipe("Medium Recipe", "Description", 20, 30, 4, Difficulty.MEDIUM, testUser);
        Recipe hardRecipe = new Recipe("Hard Recipe", "Description", 45, 90, 6, Difficulty.HARD, testUser);

        assertThat(validator.validate(easyRecipe)).isEmpty();
        assertThat(validator.validate(mediumRecipe)).isEmpty();
        assertThat(validator.validate(hardRecipe)).isEmpty();
    }
}