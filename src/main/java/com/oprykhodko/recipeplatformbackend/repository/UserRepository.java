package com.oprykhodko.recipeplatformbackend.repository;

import com.oprykhodko.recipeplatformbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.recipes WHERE u.id = :id")
    Optional<User> findUserWithRecipes(Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.recipes")
    List<User> findAllUsersWithRecipes();
}
