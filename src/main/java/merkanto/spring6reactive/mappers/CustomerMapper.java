package merkanto.spring6reactive.mappers;

import merkanto.spring6reactive.domain.Customer;
import merkanto.spring6reactive.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
