package merkanto.spring6reactive.services;

import lombok.RequiredArgsConstructor;
import merkanto.spring6reactive.mappers.PhoneMapper;
import merkanto.spring6reactive.model.PhoneDTO;
import merkanto.spring6reactive.repositories.PhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final PhoneMapper phoneMapper;

    @Override
    public Flux<PhoneDTO> listPhones() {
        return phoneRepository.findAll()
                .map(phoneMapper::phoneToPhoneDto);
    }

    @Override
    public Mono<PhoneDTO> getPhoneById(Integer phoneId) {
        return phoneRepository.findById(phoneId)
                .map(phoneMapper::phoneToPhoneDto);
    }

    @Override
    public Mono<PhoneDTO> saveNewPhone(PhoneDTO phoneDTO) {
        return phoneRepository.save(phoneMapper.phoneDtoToPhone(phoneDTO))
                .map(phoneMapper::phoneToPhoneDto );
    }

    @Override
    public Mono<PhoneDTO> updatePhone(Integer phoneId, PhoneDTO phoneDTO) {

        return phoneRepository.findById(phoneId)
                .map(foundPhone -> {
                    foundPhone.setPhoneName(phoneDTO.getPhoneName());
                    foundPhone.setPhoneStyle(phoneDTO.getPhoneStyle());
                    foundPhone.setPrice(phoneDTO.getPrice());
                    foundPhone.setImei(phoneDTO.getImei());
                    foundPhone.setQuantityOnHand(phoneDTO.getQuantityOnHand());

                    return foundPhone;
                }).flatMap(phoneRepository::save)
                .map(phoneMapper::phoneToPhoneDto);
    }

    @Override
    public Mono<PhoneDTO> patchPhone(Integer phoneId, PhoneDTO phoneDTO) {
        return phoneRepository.findById(phoneId)
                .map(foundPhone -> {
                    if (StringUtils.hasText(phoneDTO.getPhoneName())) {
                        foundPhone.setPhoneName(phoneDTO.getPhoneName());
                    }
                    if(StringUtils.hasText(phoneDTO.getPhoneStyle())){
                        foundPhone.setPhoneStyle(phoneDTO.getPhoneStyle());
                    }

                    if(phoneDTO.getPrice() != null){
                        foundPhone.setPrice(phoneDTO.getPrice());
                    }

                    if(StringUtils.hasText(phoneDTO.getImei())){
                        foundPhone.setImei(phoneDTO.getImei());
                    }

                    if(phoneDTO.getQuantityOnHand() != null){
                        foundPhone.setQuantityOnHand(phoneDTO.getQuantityOnHand());
                    }
                    return foundPhone;
                }).flatMap(phoneRepository::save)
                .map(phoneMapper::phoneToPhoneDto);
    }

    @Override
    public Mono<Void> deletePhoneById(Integer phoneId) {
        return phoneRepository.deleteById(phoneId);
    }
}
