package com.hvn.processexcel.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue (strategy= GenerationType.SEQUENCE, generator="empSeqGen")
    @SequenceGenerator(name = "empSeqGen", sequenceName = "EMP_SEQ_GEN")
    @Column(name = "user_id")
    private Integer userId;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    private Integer roleId;
    private LocalDateTime createdDate;
}
