package merkanto.spring6reactive.mappers;

import merkanto.spring6reactive.domain.Phone;
import merkanto.spring6reactive.model.PhoneDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PhoneMapper {

    Phone phoneDtoToPhone(PhoneDTO dto);

    PhoneDTO phoneToPhoneDto(Phone phone);
}
