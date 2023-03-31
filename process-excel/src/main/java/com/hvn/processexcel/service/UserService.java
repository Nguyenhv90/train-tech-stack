package com.hvn.processexcel.service;

import com.hvn.processexcel.model.User;
import com.hvn.processexcel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listAll() {
        return (List<User>) userRepository.findAllUsers4();
    }

    @Transactional
    public void addData() {
        List<User> users = new ArrayList<>();
        User user = new User();
        for (int i = 0; i<100000; i++) {
            user.setUserId(i +1);
            user.setEmail("hndata" + i + "@gmail.com");
            user.setFullName("Hoang Van " + i);
            user.setPassword("123456");
            user.setEnabled(true);
            user.setRoleId(3);
            user.setCreatedDate(LocalDateTime.now());
            users.add(user);
        }

        userRepository.saveAll(users);
    }
}
