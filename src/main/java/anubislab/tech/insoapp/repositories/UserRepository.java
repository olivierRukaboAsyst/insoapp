package anubislab.tech.insoapp.repositories;

import anubislab.tech.insoapp.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
