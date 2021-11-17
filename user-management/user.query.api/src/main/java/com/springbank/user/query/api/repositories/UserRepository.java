package com.springbank.user.query.api.repositories;

import com.springbank.user.core.models.User;
import com.springbank.user.query.api.dto.UserResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    @Query("{'$or':[{'firstname':{$regex:?0,$options:'1'}},{'lastname':{$regex:?0,$options:'1'}},{'emailAddress':{$regex:?0:$options:'1'}}]}")
    List<User> findUserByFilter(String filter);
}
