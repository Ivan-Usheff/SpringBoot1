package com.example.service1.domains.users.entities;

import com.example.service1.domains.users.constants.UserRole;
import com.example.service1.domains.users.constants.UserStatus;
import com.example.service1.lib.entities.BaseEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity()
@Table(name = "tb_users")
@Data()
public class UserEntity extends BaseEntities { //

    @Column(unique = true, name = "user_name")
    private String user;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private UserStatus status = UserStatus.CREATED;

    @Column
    private UserRole role = UserRole.USER;

    // @OneToOne(fetch = FetchType.LAZY)
    private String perfil;

}
