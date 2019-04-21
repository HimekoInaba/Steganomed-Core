package kz.stegano.med.repository;

import kz.stegano.med.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    void saveAndFlush(User user);
}
