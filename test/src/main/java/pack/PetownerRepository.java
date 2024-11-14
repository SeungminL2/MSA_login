package pack;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetownerRepository extends JpaRepository<Petowner, Long> {
	Optional<Petowner> findByEmail(String email);
}