package com.hvn.processexcel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String fullName;
    private boolean isEnabled;
    private Integer roleId;
    private LocalDateTime createdDate;
}
