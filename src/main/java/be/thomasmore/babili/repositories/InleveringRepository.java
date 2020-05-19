package be.thomasmore.babili.repositories;

import be.thomasmore.babili.model.Inlevering;
import be.thomasmore.babili.model.Opdracht;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InleveringRepository extends CrudRepository<Inlevering, Integer> {
    Optional<Inlevering> findByUser_IdAndOpdracht(int userId, Opdracht Opdracht);
}
