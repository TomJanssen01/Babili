package be.thomasmore.babili.repositories;

import be.thomasmore.babili.model.BabiliUser;
import org.springframework.data.repository.CrudRepository;

public interface BabiliUserRepository extends CrudRepository<BabiliUser, Integer> {

}
