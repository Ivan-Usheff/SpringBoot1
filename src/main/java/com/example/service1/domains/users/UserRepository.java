package com.example.service1.domains.users;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.service1.domains.users.constants.UserRole;
import com.example.service1.domains.users.constants.UserStatus;
import com.example.service1.domains.users.entities.UserEntity;
import com.example.service1.domains.users.errors.UsersErrors;
import com.example.service1.domains.users.interfaces.UserRepositoryInterface;
import com.example.service1.lib.repository.BaseRepository;

@Repository
public class UserRepository implements BaseRepository<UserEntity> {

    @Autowired
    private UserRepositoryInterface userRespository;
    private Logger logger = LogManager.getLogger(UserRepository.class);

    @Override
    public List<UserEntity> getAll() throws Exception {
        logger.info("getAll() start! ...");
        try{
            List<UserEntity> usersList = userRespository.findAll();
            return usersList;
        } catch(Exception e) {
            logger.error("getAll() fail reason: ", e);
            throw new Exception(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    @Override
    public UserEntity create(UserEntity data) throws Exception {
        logger.info("create() start! ...");
        try{
            data.setRole(UserRole.USER);
            data.setStatus(UserStatus.CREATED);
            data.setCreateAt(new Date());
            data.setUpdateAt(new Date());
            UserEntity newUser = userRespository.save(data);
            return newUser;
        } catch(Exception e) {
            logger.error("create() fail reason: ", e);
            throw new Exception(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    public List<UserEntity> findByEmail(String email) throws Exception {
        logger.info("findByEmail() start! ...");
        try{
            List<UserEntity> usersList = userRespository.findByEmail(email);
            return usersList;
        } catch(Exception e) {
            logger.error("findByEmail() fail reason: ", e);
            throw new Exception(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws Exception {
        logger.info("findById() start! ...");
        try {
            Optional<UserEntity> user = userRespository.findById(id);
            return user;
        } catch(Exception e) {
            logger.error("findById() fail reason: ", e);
            throw new Exception(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    @Override
    public UserEntity findByIdAndUpdate(Long id, UserEntity data) throws Exception {
        logger.info("findByIdAndUpdate() start! ...");
        try{
            Optional<UserEntity> existedUser = userRespository.findById(id);
            if(existedUser.isEmpty())
                throw new Exception("El usuario no existe");

            UserEntity updatedUser = userRespository.save(data);
            return updatedUser;
        } catch(Exception e) {
            logger.error("findByIdAndUpdate() fail reason: ", e);
            throw new Exception(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

    @Override
    public Boolean delete(Long id) throws Exception {
        logger.info("delete() start! ...");
        try{
            Optional<UserEntity> existedUser = userRespository.findById(id);
            if(existedUser.isEmpty())
                throw new Exception("El usuario no existe");

            UserEntity updatedDeleted = existedUser.get();
            updatedDeleted.setStatus(UserStatus.DELETED);
            userRespository.save(updatedDeleted);
            return true;
        } catch(Exception e) {
            logger.error("delete() fail reason: ", e);
            throw new Exception(UsersErrors.ERROR_DEFAULT_ERROR);
        }
    }

}
