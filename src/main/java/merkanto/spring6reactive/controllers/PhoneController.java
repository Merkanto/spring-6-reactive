package merkanto.spring6reactive.controllers;

import lombok.RequiredArgsConstructor;
import merkanto.spring6reactive.model.PhoneDTO;
import merkanto.spring6reactive.services.PhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PhoneController {

    public static final String PHONE_PATH = "/api/v2/phone";
    public static final String PHONE_PATH_ID = PHONE_PATH + "/{phoneId}";

    private final PhoneService phoneService;

    @DeleteMapping(PHONE_PATH_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("phoneId") Integer phoneId) {
        return phoneService.getPhoneById(phoneId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(phoneDTO -> phoneService.deletePhoneById(phoneDTO.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(PHONE_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingPhone(@PathVariable("phoneId") Integer phoneId,
                                                  @Validated @RequestBody PhoneDTO phoneDTO) {
        return phoneService.patchPhone(phoneId, phoneDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(updateDto -> ResponseEntity.ok().build());
    }

    @PutMapping(PHONE_PATH_ID)
    Mono<ResponseEntity<Void>> updateExistingPhone(@PathVariable("phoneId") Integer phoneId,
                                             @Validated @RequestBody PhoneDTO phoneDTO) {
        return phoneService.updatePhone(phoneId, phoneDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedDto -> ResponseEntity.noContent().build());

//        phoneService.updatePhone(phoneId, phoneDTO).subscribe();
//        return ResponseEntity.noContent().build();
    }

    @PostMapping(PHONE_PATH)
    Mono<ResponseEntity<Void>> createNewPhone(@Validated @RequestBody PhoneDTO phoneDTO){
        return phoneService.saveNewPhone(phoneDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/" + PHONE_PATH
                                + "/" + savedDto.getId())
                        .build().toUri())
                        .build());
    }

    @GetMapping(PHONE_PATH_ID)
    Mono<PhoneDTO> getPhoneById(@PathVariable("phoneId") Integer phoneId) {
        return phoneService.getPhoneById(phoneId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping(PHONE_PATH)
    Flux<PhoneDTO> listPhones() {
        return phoneService.listPhones();
    }
}
