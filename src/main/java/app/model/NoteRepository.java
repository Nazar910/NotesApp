package app.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

/**
 * Created by pyvov on 15.12.2016.
 */
public interface NoteRepository extends MongoRepository<Note,Long> {
    Note findById(BigInteger id);
    Note findByTitle(String title);
    Note findByText(String title);
}
