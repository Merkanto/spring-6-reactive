package merkanto.spring6reactive.bootstrap;

import lombok.RequiredArgsConstructor;
import merkanto.spring6reactive.domain.Customer;
import merkanto.spring6reactive.domain.Phone;
import merkanto.spring6reactive.repositories.CustomerRepository;
import merkanto.spring6reactive.repositories.PhoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private final PhoneRepository phoneRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadPhoneData();
        loadCustomerData();
        phoneRepository.count().subscribe(count -> {
            System.out.println("Phone Count is: " + count);
        });

        customerRepository.count().subscribe(count -> {
            System.out.println("Customer Count is: " + count);
        });
    }

    private void loadCustomerData() {
        customerRepository.count().subscribe(count -> {
            if(count == 0){
                customerRepository.save(Customer.builder()
                                .customerName("Customer 1")
                                .build())
                        .subscribe();

                customerRepository.save(Customer.builder()
                                .customerName("Customer 2")
                                .build())
                        .subscribe();

                customerRepository.save(Customer.builder()
                                .customerName("Customer 3")
                                .build())
                        .subscribe();
            }
        });
    }

    private void loadPhoneData() {
        phoneRepository.count().subscribe(count -> {
            if (count == 0) {
                Phone phone1 = Phone.builder()
                        .phoneName("Galaxy Cat")
                        .phoneStyle("Pale Ale")
                        .imei("12356")
                        .price(new BigDecimal("12.99"))
                        .quantityOnHand(122)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Phone phone2 = Phone.builder()
                        .phoneName("Crank")
                        .phoneStyle("Pale Ale")
                        .imei("12356222")
                        .price(new BigDecimal("11.99"))
                        .quantityOnHand(392)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Phone phone3 = Phone.builder()
                        .phoneName("Sunshine City")
                        .phoneStyle("IPA")
                        .imei("12356")
                        .price(new BigDecimal("13.99"))
                        .quantityOnHand(144)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                phoneRepository.save(phone1).subscribe();
                phoneRepository.save(phone2).subscribe();
                phoneRepository.save(phone3).subscribe();
            }
        });
    }
}
