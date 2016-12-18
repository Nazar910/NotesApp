package app.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

/**
 * Created by pyvov on 17.12.2016.
 */
public interface UserRepository extends MongoRepository<User,BigInteger>{
    User findById(BigInteger id);
    User findByUsername(String username);
    User findByEmail(String email);
}
