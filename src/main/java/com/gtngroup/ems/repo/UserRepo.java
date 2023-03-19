package com.gtngroup.ems.repo;

import com.gtngroup.ems.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}

/**
        This code defines a UserRepo interface that extends the JpaRepository        interface provided by Spring Data JPA.
        The JpaRepository interface is
        a high-level JPA abstraction that provides a
        set of generic CRUD methods for performing common database
        operations such as saving, updating, and deleting entities.
        By extending this interface, the UserRepo interface inherits
        these methods and can use them to interact with the database.
        The UserRepo interface is parameterized with
        the User entity class and an Integer primary key type,
        which specifies the entity class and the type of the primary key
        field in the User entity.
        By default, Spring Data JPA provides
        implementations of the JpaRepository methods that use
        JPA to perform the underlying database operations. However,
        you can also define custom methods in the UserRepo interface that
        provide additional functionality specific to your application.
        These custom methods can use method names that follow a specific
        naming convention, or they can be defined using the @Query annotation
        to provide custom SQL or JPQL queries.
 **/