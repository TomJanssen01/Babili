package be.thomasmore.babili.repositories;

import be.thomasmore.babili.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
