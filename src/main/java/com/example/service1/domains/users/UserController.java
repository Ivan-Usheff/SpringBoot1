package com.example.service1.domains.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service1.domains.users.dto.ActivateDto;
import com.example.service1.domains.users.dto.LoginDto;
import com.example.service1.domains.users.entities.UserEntity;
import com.example.service1.lib.response.CustomResponse;

@RestController
@RequestMapping(value = "/Users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Object> getAllController() throws Exception{
        return userService.getAllUsersService();
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Object> createNewUserController(@RequestBody()UserEntity newUser) throws Exception {
        return userService.createUserService(newUser);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> loginController(@RequestBody()LoginDto data) throws Exception {
        return userService.loginService(data.email, data.password);
    }


    @PutMapping(value = "/activate")
    public ResponseEntity<Object> activateController(@RequestBody()ActivateDto data) throws Exception {
        return userService.activateService(data.email);
    }

    @GetMapping(value = "/my-acount/{email}")
    public ResponseEntity<Object> getAcountInfoControler(
        @PathVariable("email")
        String email,
        @RequestHeader(value = "auth")
        String token
    ) throws Exception {
        boolean auth = validateToken(token);
        if(auth){
            return userService.getAcountInfoService(email);
        }
        return CustomResponse.generateResponse("Parece que tu sesion expiro", HttpStatus.UNAUTHORIZED, email);
    }

    @DeleteMapping(value = "/close-acount")
    public ResponseEntity<Object> closeControler(
        @RequestBody()ActivateDto data,
        @RequestHeader(value = "auth")
        String token
        ) throws Exception {
        boolean auth = validateToken(token);
        if(auth){
            return userService.deleteAcount(data.email);
        }
        return CustomResponse.generateResponse("Parece que tu sesion expiro", HttpStatus.UNAUTHORIZED, data.email);
    }


    private Boolean validateToken(String token){
        // averiguar token de auth!!!
        if(!token.equals("supertokenmegasifradoparaautentificar")){
            return false;
        }
        return true;
    }
}
