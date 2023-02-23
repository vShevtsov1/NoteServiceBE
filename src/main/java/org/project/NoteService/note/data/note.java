package org.project.NoteService.note.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("note")
public class note {
    @Id
    private String id;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestamp;
    private String creator;
    private List<String> likes;

    public note(String content, Date timestamp, String creator, List<String> likes) {
        this.content = content;
        this.timestamp = timestamp;
        this.creator = creator;
        this.likes = likes;
    }
}
