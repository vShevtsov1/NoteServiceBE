package org.project.NoteService.user.services;

import org.modelmapper.ModelMapper;
import org.project.NoteService.services.TokenServices;
import org.project.NoteService.user.data.DTO.*;
import org.project.NoteService.user.data.user;
import org.project.NoteService.user.help.role;
import org.project.NoteService.user.help.status;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import org.project.NoteService.services.emailController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class userService {

    private final userRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenServices tokenServices;
    private  emailController emailController;

    public userService(userRepo userRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder, TokenServices tokenServices, emailController emailController) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenServices = tokenServices;
        this.emailController = emailController;
    }

    public List<user> getAll(){
        return userRepo.findAll();
    }
    public user save(saveUserDTO saveUserDTO){
            user savedUser = modelMapper.map(saveUserDTO,user.class);
            savedUser.setActive(false);
            savedUser.setAdminConfirmation(false);
            savedUser.setRole(role.USER);
            savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
            userRepo.save(savedUser);
            emailController.SendConfirmationMail(tokenServices.generateTokenActivation(savedUser), savedUser);
            return savedUser;
    }
    public user delete(String id){
        user deletedUser = userRepo.findById(id).get();
        userRepo.deleteById(id);
        return deletedUser;
    }
    public user updateUser(updateUserDTO updateUserDTO){
        user existsData = userRepo.findById(updateUserDTO.getId()).get();
        user newData = modelMapper.map(updateUserDTO,user.class);
        newData.setActive(existsData.getActive());
        newData.setAdminConfirmation(existsData.getAdminConfirmation());
        newData.setPassword(existsData.getPassword());
        userRepo.save(newData);
        return newData;
    }
    public responseLogin loginUser(LoginDTO loginDTO){
        user loginUser = userRepo.findUserByEmail(loginDTO.getEmail());
        if(loginUser.getActive() && loginUser.getAdminConfirmation()){
            if(passwordEncoder.matches(loginDTO.getPassword(),loginUser.getPassword())){
                return new responseLogin(status.OK,tokenServices.generateTokenUser(loginUser));
            }
        }
        return new responseLogin(status.FAILED,"");
    }

    public user adminConfirmation(String userId) {
        user confirmationUser = userRepo.findById(userId).get();
        confirmationUser.setAdminConfirmation(true);
        userRepo.save(confirmationUser);
        return confirmationUser;
    }

    public user userConfirmation(String token) {
        user confirmationUser = userRepo.findUserByEmail(tokenServices.getMailActivation(token));
        confirmationUser.setActive(true);
        userRepo.save(confirmationUser);
        return confirmationUser;
    }
    public user sendEmailToReset(String mail){
        user mailReset = userRepo.findUserByEmail(mail);
        emailController.SendResetPasswordMail(tokenServices.generateTokenActivation(mailReset),mailReset);
        return mailReset;
    }
    public user setNewPassword(ResetPasswordDTO resetPasswordDTO,String token){
        user newPassUser = userRepo.findUserByEmail(tokenServices.getMailActivation(token));
        newPassUser.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userRepo.save(newPassUser);
        return newPassUser;
    }
    public user getById(String id){
        return userRepo.findById(id).get();
    }
}
