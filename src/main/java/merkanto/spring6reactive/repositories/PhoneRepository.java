package merkanto.spring6reactive.repositories;

import merkanto.spring6reactive.domain.Phone;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PhoneRepository extends ReactiveCrudRepository<Phone, Integer> {
}
