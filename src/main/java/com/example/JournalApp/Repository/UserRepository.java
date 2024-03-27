package com.example.JournalApp.Repository;

import com.example.JournalApp.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    User findByUserName(String username);
}
