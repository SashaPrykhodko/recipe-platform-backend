-- Initial database schema for Recipe Platform

-- Set the schema to public
SET search_path TO public;

-- Users table
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       display_name VARCHAR(100),
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Recipes table
CREATE TABLE recipes (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(200) UNIQUE NOT NULL,
                         description TEXT,
                         prep_time_minutes INTEGER,
                         cook_time_minutes INTEGER,
                         servings INTEGER,
                         difficulty VARCHAR(20) CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD')),
                         user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
