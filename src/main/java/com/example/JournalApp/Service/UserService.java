package com.example.JournalApp.Service;

import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public List<User> fetchAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> fetchUserById(Long userId){
        return userRepository.findById(userId);
    }

    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
