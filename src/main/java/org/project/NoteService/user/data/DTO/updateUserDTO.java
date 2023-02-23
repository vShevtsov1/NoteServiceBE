package org.project.NoteService.user.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.NoteService.user.help.role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class updateUserDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private role role;
}
