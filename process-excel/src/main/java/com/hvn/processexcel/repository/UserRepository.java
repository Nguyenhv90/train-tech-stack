package com.hvn.processexcel.repository;

import com.hvn.processexcel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "Select user_id, created_date, " +
            "email, full_name, is_enabled, password, role_id FROM USERS u limit 1000000;",
    nativeQuery = true)
    List<User> findAllUsers4();

}
