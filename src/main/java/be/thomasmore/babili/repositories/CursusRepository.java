package be.thomasmore.babili.repositories;

import be.thomasmore.babili.model.Cursus;
import be.thomasmore.babili.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CursusRepository extends CrudRepository<Cursus, Integer> {
}
