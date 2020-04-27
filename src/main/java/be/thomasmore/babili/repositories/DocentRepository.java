package be.thomasmore.babili.repositories;

import be.thomasmore.babili.model.Docent;
import be.thomasmore.babili.model.User;
import org.springframework.data.repository.CrudRepository;

public interface DocentRepository extends CrudRepository<Docent, Integer> {
}
