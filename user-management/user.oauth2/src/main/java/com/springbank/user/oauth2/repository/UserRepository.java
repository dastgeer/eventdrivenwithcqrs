package com.springbank.user.oauth2.repository;

import com.springbank.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    @Query("{'account.username': ?0}")
    Optional<User> findByUsername(String username);
}
