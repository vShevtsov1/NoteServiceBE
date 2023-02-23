package org.project.NoteService.note;

import org.project.NoteService.note.data.DTO.noteSaveDTO;
import org.project.NoteService.note.data.note;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.project.NoteService.note.services.noteService;

import java.util.List;

@RestController
@RequestMapping(path = "/notes")
public class noteController {
    private final noteService noteService;

    public noteController(noteService noteService) {
        this.noteService = noteService;
    }
    @GetMapping()
    public ResponseEntity getALl(@RequestParam(value = "sorting",required = true) String sorting){
        List<note> result = noteService.getAll(sorting);
       if(result == null){
           return ResponseEntity.badRequest().build();
       }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/save")
    public note save(@RequestBody noteSaveDTO noteSaveDTO){
        return noteService.save(noteSaveDTO);
    }

    @PostMapping("/likes")
    public note addRemoveLikes(@RequestParam String note,@RequestParam String user){
       return noteService.addRemoveLike(note,user);
    }
}
