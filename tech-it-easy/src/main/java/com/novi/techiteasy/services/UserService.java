package com.novi.techiteasy.services;


import com.novi.techiteasy.DTO.Input.UserInputDTO;
import com.novi.techiteasy.DTO.Output.UserOutputDTO;
import com.novi.techiteasy.exceptions.RecordNotFoundException;
import com.novi.techiteasy.models.Authority;
import com.novi.techiteasy.models.User;
import com.novi.techiteasy.repositories.UserRepository;
import com.novi.techiteasy.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserOutputDTO> getUsers() {
        List<UserOutputDTO> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserOutputDTO getUser(String username) {
        UserOutputDTO dto = new UserOutputDTO();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new RecordNotFoundException(username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserInputDTO userInputDTO) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDTO.setApikey(randomString);
        User newUser = userRepository.save(toUser(userInputDTO));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserInputDTO newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserOutputDTO userOutputDTO = fromUser(user);
        return userOutputDTO.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public static UserOutputDTO fromUser(User user){

        var dto = new UserOutputDTO();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(UserInputDTO userInputDTO) {

        var user = new User();

        user.setUsername(userInputDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userInputDTO.getPassword()));
        user.setEnabled(userInputDTO.getEnabled());
        user.setApikey(userInputDTO.getApikey());
        user.setEmail(userInputDTO.getEmail());

        return user;
    }

}