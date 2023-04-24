package merkanto.spring6reactive.services;

import merkanto.spring6reactive.model.PhoneDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PhoneService {

    Flux<PhoneDTO> listPhones();

    Mono<PhoneDTO> getPhoneById(Integer phoneId);

    Mono<PhoneDTO> saveNewPhone(PhoneDTO phoneDTO);

    Mono<PhoneDTO> updatePhone(Integer phoneId, PhoneDTO phoneDTO);

    Mono<PhoneDTO> patchPhone(Integer phoneId, PhoneDTO phoneDTO);

    Mono<Void> deletePhoneById(Integer phoneId);
}
