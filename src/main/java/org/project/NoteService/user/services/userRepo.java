package org.project.NoteService.user.services;

import org.project.NoteService.user.data.user;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepo extends MongoRepository<user,String> {
    user findUserByEmail(String email);
}
