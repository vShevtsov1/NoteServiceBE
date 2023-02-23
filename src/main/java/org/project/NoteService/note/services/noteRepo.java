package org.project.NoteService.note.services;

import org.project.NoteService.note.data.note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface noteRepo extends MongoRepository<note,String> {

    List<note> findAllByOrderByTimestampDesc();

}
