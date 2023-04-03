package com.hvn.processexcel.service;

import com.hvn.processexcel.model.User;
import com.hvn.processexcel.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ExportExcel exportExcel;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void addData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i<1000000; i++) {
            User user = new User();
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

    public void exportExcel(HttpServletResponse response) throws IOException {
        exportExcel.export(response, this.listAll());
    }

    @Transactional
    public void importExcel(MultipartFile files) throws IOException {
        List<User> users = exportExcel.readLargeExcel(files);
        System.out.println("save thanhf coong");
        userRepository.saveAll(users);
    }
}
