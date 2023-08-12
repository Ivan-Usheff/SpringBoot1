package com.example.service1.domains.users.response;

import com.example.service1.domains.users.constants.UserRole;
import com.example.service1.domains.users.constants.UserStatus;
import com.example.service1.domains.users.entities.UserEntity;

public class AcountInfoResponse {
    public final Long id;
    public final String email;
    public final UserStatus  status;
    public final UserRole  role;
    public final String perfil;

    public AcountInfoResponse(UserEntity acount){
        id = acount.getId();
        email = acount.getEmail();
        status = acount.getStatus();
        role = acount.getRole();
        perfil = acount.getPerfil();
    }
}
