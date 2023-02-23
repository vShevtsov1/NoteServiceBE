package org.project.NoteService.user.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.NoteService.user.help.status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class responseLogin {
    public status status;
    public String token;
}
