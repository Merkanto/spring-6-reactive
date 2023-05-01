package merkanto.spring6reactive.mappers;

import merkanto.spring6reactive.domain.Beer;
import merkanto.spring6reactive.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
