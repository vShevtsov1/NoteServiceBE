package org.project.NoteService.user.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class saveUserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
}
