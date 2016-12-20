package app.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Created by pyvov on 17.12.2016.
 */
public interface UserRepository extends MongoRepository<User, BigInteger> {
    Optional<User> findById(BigInteger id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
