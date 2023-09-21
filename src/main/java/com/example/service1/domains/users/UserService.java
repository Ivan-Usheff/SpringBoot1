package com.example.service1.domains.users;


import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.service1.domains.users.constants.UserStatus;
import com.example.service1.domains.users.dto.TokenDto;
import com.example.service1.domains.users.entities.UserEntity;
import com.example.service1.domains.users.errors.UsersErrors;
import com.example.service1.domains.users.response.AcountInfoResponse;
import com.example.service1.lib.response.CustomResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LogManager.getLogger(UserService.class);

    public ResponseEntity<Object> createUserService(UserEntity user) throws Exception {
        logger.info("createUserService() start! ...");
        try{
            UserEntity userData = user;
            List<UserEntity> userExist = userRepository.findByEmail(userData.getEmail());
            if(userExist.isEmpty()){
                int strength = 10;
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
                String encodedPassword = bCryptPasswordEncoder.encode(userData.getPassword());
                userData.setPassword(encodedPassword);
                user.setCreateAt(new Date());
                UserEntity newUser = userRepository.create(userData);
                String userEmail = newUser.getEmail();
                return CustomResponse.generateResponse("Usuario creado exitosamente", HttpStatus.CREATED, userEmail);
            }
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_ALREDY_EXISTS, userData.getEmail());
        } catch(Exception e) {
            logger.error("createUserService() fail! [".concat(e.getMessage()).concat("]"));
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DEFAULT_ERROR);
        }

    }

    public ResponseEntity<Object> loginService(String email, String password) throws Exception {
        logger.info("loginService() start! ...");
        try{
            List<UserEntity> userExist = userRepository.findByEmail(email);
            if(userExist.isEmpty()){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DONT_FOUND);
            }
            
            UserEntity user = userExist.get(0);
            if(user.getStatus().equals(UserStatus.CREATED)){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_WITHOUT_ACTIVATE);
            }
            if(user.getStatus().equals(UserStatus.BANED)){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_WITHOUT_ACTIVATE);
            }
            if(user.getStatus().equals(UserStatus.DELETED)){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_ACOUNT_CLOSE);
            }
            
            int strength = 10;
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
            boolean mach = bCryptPasswordEncoder.matches(password, user.getPassword());
            if(mach){
                String token = "supertokenmegasifradoparaautentificar";
                return CustomResponse.generateResponse("Bienvenido", HttpStatus.ACCEPTED, new TokenDto(token));
            }
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_BAD_REQUEST);
        } catch(Exception e) {
            logger.error("loginService() fail! [".concat(e.getMessage()).concat("]"));
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    public ResponseEntity<Object> activateService(String email) throws Exception {
        logger.info("activateService() start! ...");
        try{
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

            if(user.getStatus().equals(UserStatus.BANED)){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_WITHOUT_ACTIVATE);
            }
            if(user.getStatus().equals(UserStatus.DELETED)){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_ACOUNT_CLOSE);
            }

            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_UNEXPECTED_ERROR);
        } catch(Exception e) {
            logger.error("activateService() fail! [".concat(e.getMessage()).concat("]"));
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    public ResponseEntity<Object> getAcountInfoService(String email) throws Exception {
        logger.info("getAcountInfoService() start! ...");
        try{
            List<UserEntity> userExist = userRepository.findByEmail(email);
            if(userExist.isEmpty()){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DONT_FOUND, email);
            }
            UserEntity acount = userExist.get(0);
            if(Boolean.TRUE.equals(validateUserStatusActive(acount.getStatus()))){
                AcountInfoResponse acountResponse = new AcountInfoResponse(acount);
                return CustomResponse.generateResponse("Aqui estan los datos de su cuenta", HttpStatus.OK, acountResponse);
            }

            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_UNEXPECTED_ERROR, acount.getStatus());
        } catch(Exception e) {
            logger.error("getAcountInfoService() fail! [".concat(e.getMessage()).concat("]"));
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    public ResponseEntity<Object> deleteAcount(String email) throws Exception {
        logger.info("deleteAcount() start! ...");
        try{
            List<UserEntity> userExist = userRepository.findByEmail(email);
            if(userExist.isEmpty()){
                return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DONT_FOUND, email);
            }
            UserEntity acount = userExist.get(0);
            acount.setStatus(UserStatus.DELETED);
            userRepository.findByIdAndUpdate(acount.getId(), acount);
            return CustomResponse.generateResponse("Su cuenta ha sido cerrada exitosamente", HttpStatus.OK, email);
        } catch(Exception e) {
            logger.error("deleteAcount() fail! [".concat(e.getMessage()).concat("]"));
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    public ResponseEntity<Object> getAllUsersService() throws Exception{
        logger.info("getAllUsersService() start! ...");
        try{
            List<UserEntity> listUsers = userRepository.getAll();
            return CustomResponse.generateResponse("Prticion exitosa", HttpStatus.OK, listUsers);
        } catch(Exception e) {
            logger.error("getAllUsersService() fail! [".concat(e.getMessage()).concat("]"));
            return CustomResponse.generateErrorResponse(UsersErrors.ERROR_DEFAULT_ERROR);
        }
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
