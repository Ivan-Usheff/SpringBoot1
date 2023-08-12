package com.example.service1.domains.users;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.service1.domains.users.constants.UserStatus;
import com.example.service1.domains.users.dto.TokenDto;
import com.example.service1.domains.users.entities.UserEntity;
import com.example.service1.domains.users.response.AcountInfoResponse;
import com.example.service1.lib.response.CustomResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> createUserService(UserEntity user) throws Exception {
        List<UserEntity> userExist = userRepository.findByEmail(user.getEmail());
        if(userExist.isEmpty()){
            user.setCreateAt(new Date());
            UserEntity newUser = userRepository.create(user);
            String userEmail = newUser.getEmail();
            return CustomResponse.generateResponse("Usuario creado exitosamente", HttpStatus.CREATED, userEmail);
        }
        return CustomResponse.generateResponse("El Email ya se encuentra registrado", HttpStatus.BAD_REQUEST, user.getEmail());
    }

    public ResponseEntity<Object> loginService(String email, String password) throws Exception {
        List<UserEntity> userExist = userRepository.findByEmail(email);
        if(userExist.isEmpty()){
            return CustomResponse.generateResponse("Los datos ingresados no son correctos", HttpStatus.BAD_REQUEST, email);
        }
        
        UserEntity user = userExist.get(0);
        if(user.getStatus().equals(UserStatus.CREATED)){
            return CustomResponse.generateResponse("Debes activar la cuenta", HttpStatus.UNAUTHORIZED, email);
        }
        if(user.getStatus().equals(UserStatus.BANED)){
            return CustomResponse.generateResponse("Debes activar la cuenta", HttpStatus.UNAUTHORIZED, email);
        }
        if(user.getStatus().equals(UserStatus.DELETED)){
            return CustomResponse.generateResponse("Su cuenat a sido cerrada", HttpStatus.UNAUTHORIZED, email);
        }
        if(user.getPassword().equals(password)){
            String token = "supertokenmegasifradoparaautentificar";
            return CustomResponse.generateResponse("Bienvenido", HttpStatus.ACCEPTED, new TokenDto(token));
        }
        return CustomResponse.generateResponse("Los datos ingresados no son correctos", HttpStatus.BAD_REQUEST, email);
    }

    public ResponseEntity<Object> activateService(String email) throws Exception {
        List<UserEntity> userExist = userRepository.findByEmail(email);
        if(userExist.isEmpty()){
            return CustomResponse.generateResponse("Los datos ingresados no son correctos", HttpStatus.BAD_REQUEST, email);
        }
        
        UserEntity user = userExist.get(0);
        if(user.getStatus().equals(UserStatus.CREATED)){
            user.setStatus(UserStatus.ACTIVE);
            userRepository.findByIdAndUpdate(user.getId(), user);
            return CustomResponse.generateResponse("Su cuenat fue activada exitosamente", HttpStatus.UNAUTHORIZED, email);
        }
        //No voy a validar todo otra vez!
        return CustomResponse.generateResponse("Ocurrio un problema con su cuenta", HttpStatus.I_AM_A_TEAPOT, email);
    }

    public ResponseEntity<Object> getAcountInfoService(String email) throws Exception {
        List<UserEntity> userExist = userRepository.findByEmail(email);
        if(userExist.isEmpty()){
            return CustomResponse.generateResponse("La cuenta no existe", HttpStatus.BAD_REQUEST, email);
        }
        UserEntity acount = userExist.get(0);
        if(Boolean.TRUE.equals(validateUserStatusActive(acount.getStatus()))){
            AcountInfoResponse acountResponse = new AcountInfoResponse(acount);
            return CustomResponse.generateResponse("Aqui estan los datos de su cuenta", HttpStatus.OK, acountResponse);
        }

        return CustomResponse.generateResponse("Ocurrio un problema con su cuenta", HttpStatus.BAD_REQUEST, acount.getStatus());
    }

    public ResponseEntity<Object> deleteAcount(String email) throws Exception {
        List<UserEntity> userExist = userRepository.findByEmail(email);
        if(userExist.isEmpty()){
            return CustomResponse.generateResponse("La cuenta no existe", HttpStatus.BAD_REQUEST, email);
        }
        UserEntity acount = userExist.get(0);
        acount.setStatus(UserStatus.DELETED);
        userRepository.findByIdAndUpdate(acount.getId(), acount);
        return CustomResponse.generateResponse("Ocurrio un problema con su cuenta", HttpStatus.OK, acount.getStatus());

    }


    private Boolean validateUserStatusActive(UserStatus status){
        if(status.equals(UserStatus.CREATED)){
            return false;
        }
        if(status.equals(UserStatus.BANED)){
            return false;
        }
        if(status.equals(UserStatus.DELETED)){
            return false;
        }
        return true;
    }
}
