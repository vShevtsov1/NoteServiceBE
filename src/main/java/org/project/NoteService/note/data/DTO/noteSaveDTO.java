package org.project.NoteService.note.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class noteSaveDTO {

    private String content;
    private String creator;
}
