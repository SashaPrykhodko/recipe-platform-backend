package com.oprykhodko.recipeplatformbackend.repository;

import com.oprykhodko.recipeplatformbackend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    @Query("SELECT r FROM Recipe r JOIN FETCH r.user WHERE r.id = :id")
    Optional<Recipe> findByIdWithUser(@Param("id") Long id);

    @Query("SELECT r FROM Recipe r JOIN FETCH r.user")
    List<Recipe> findAllWithUsers();
}
