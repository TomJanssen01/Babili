package be.thomasmore.babili.repositories;

import be.thomasmore.babili.model.Opdracht;
import be.thomasmore.babili.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Iterable<User> findByRole(String role);
    Iterable<User> findByCursus_Id(Integer id);
}
