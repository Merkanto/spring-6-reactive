package merkanto.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import merkanto.spring6reactive.config.DatabaseConfig;
import merkanto.spring6reactive.domain.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class PhoneRepositoryTest {
    @Autowired
    PhoneRepository phoneRepository;

    @Test
    void testCreateJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(getTestPhone()));
    }

    @Test
    void saveNewPhone() {
        phoneRepository.save(getTestPhone())
                .subscribe(phone -> {
                    System.out.println(phone.toString());
                });
    }

    public static Phone getTestPhone() {
        return Phone.builder()
                .phoneName("Space Dust")
                .phoneStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(12)
                .imei("123213")
                .build();
    }
}