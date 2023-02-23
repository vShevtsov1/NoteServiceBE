package org.project.NoteService.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.NoteService.user.help.role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class user {
    @Id
    private String id;
    private String name;
    private String surname;
    @Indexed(unique = true)
    private String email;
    private role role;
    private String password;
    private Boolean active;
    private Boolean adminConfirmation;

    public user(String name, String surname, String email, role role, String password, Boolean active, Boolean adminConfirmation) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.password = password;
        this.active = active;
        this.adminConfirmation = adminConfirmation;
    }
}

