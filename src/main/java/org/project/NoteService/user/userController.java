package org.project.NoteService.user;

import org.modelmapper.ModelMapper;
import org.project.NoteService.user.data.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.project.NoteService.user.services.userService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class userController {

    private final ModelMapper modelMapper;

    private final userService userService;

    public userController(ModelMapper modelMapper, userService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping()
    public List<userDTO> getAll(){
        return userService.getAll().stream().map(user -> modelMapper.map(user, userDTO.class)).collect(Collectors.toList());
    }
    @PostMapping("/save")
    public ResponseEntity saveNewUser(@RequestBody saveUserDTO saveUserDTO){
      try{
          return ResponseEntity.ok(modelMapper.map(userService.save(saveUserDTO),userDTO.class));
      }
      catch (Exception e){
          return ResponseEntity.badRequest().build();
      }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id){
        try{
            return ResponseEntity.ok(modelMapper.map(userService.delete(id),userDTO.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody updateUserDTO updateUserDTO){
        try{
            return ResponseEntity.ok(modelMapper.map(userService.updateUser(updateUserDTO),userDTO.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity updateUser(@RequestBody LoginDTO loginDTO){
        try{
            return ResponseEntity.ok(userService.loginUser(loginDTO));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/admin-confirmation")
    public ResponseEntity adminConfirmation(@RequestParam String userId){
        try{
            return ResponseEntity.ok(modelMapper.map(userService.adminConfirmation(userId),userDTO.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/confirmation")
    public ResponseEntity userConfirmation(@RequestParam String token){
        try{
            return ResponseEntity.ok(modelMapper.map(userService.userConfirmation(token),userDTO.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/reset")
    public ResponseEntity sendEmailToReset(@RequestParam String email){
        try{
            return ResponseEntity.ok(modelMapper.map(userService.sendEmailToReset(email),userDTO.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/new-password")
    public ResponseEntity sendEmailToReset(@RequestBody ResetPasswordDTO resetPasswordDTO,@RequestParam String token){
        try{
            return ResponseEntity.ok(modelMapper.map(userService.setNewPassword(resetPasswordDTO,token),userDTO.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable("id") String id){
        try{
            return ResponseEntity.ok(modelMapper.map(userService.getById(id),userDTO.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
