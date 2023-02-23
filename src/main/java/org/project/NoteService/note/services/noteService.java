package org.project.NoteService.note.services;

import org.modelmapper.ModelMapper;
import org.project.NoteService.note.data.DTO.noteSaveDTO;
import org.project.NoteService.note.data.note;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class noteService {
    private final noteRepo noteRepo;
    private final ModelMapper modelMapper;

    public noteService(org.project.NoteService.note.services.noteRepo noteRepo, ModelMapper modelMapper) {
        this.noteRepo = noteRepo;
        this.modelMapper = modelMapper;
    }

    public List<note> getAll(String param){
        if(param.equals("DESC")){
            return noteRepo.findAllByOrderByTimestampDesc();
        }
        else if (param.equals("ASC")){
            return noteRepo.findAll();
        }
        return null;
    }
    public note save(noteSaveDTO noteSaveDTO){
        note newNote = modelMapper.map(noteSaveDTO,note.class);
        newNote.setTimestamp(new Date());
        newNote.setLikes(new ArrayList<>());
        noteRepo.save(newNote);
        return newNote;
    }

    public note addRemoveLike(String noteId,String userId){
        note changedNote = noteRepo.findById(noteId).get();
        if(changedNote.getLikes().contains(userId)){
           changedNote.getLikes().remove(userId);
        }
        else {
            changedNote.getLikes().add(userId);
        }
        noteRepo.save(changedNote);
        return changedNote;
    }

}
