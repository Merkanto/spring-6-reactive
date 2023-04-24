package merkanto.spring6reactive.controllers;

import merkanto.spring6reactive.model.PhoneDTO;
import merkanto.spring6reactive.repositories.PhoneRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class PhoneControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(999)
    void testDeletePhone() {
        webTestClient.delete()
                .uri(PhoneController.PHONE_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @Order(3)
    void testUpdatePhone() {
        webTestClient.put()
                .uri(PhoneController.PHONE_PATH_ID, 1)
                .body(Mono.just(PhoneRepositoryTest.getTestPhone()), PhoneDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testCreatePhone() {
        webTestClient.post().uri(PhoneController.PHONE_PATH)
                .body(Mono.just(PhoneRepositoryTest.getTestPhone()), PhoneDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/phone/2");
    }

    @Test
    @Order(1)
    void testGetById() {
        webTestClient.get().uri(PhoneController.PHONE_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(PhoneDTO.class);
    }

    @Test
    @Order(2)
    void testListPhones() {
        webTestClient.get().uri(PhoneController.PHONE_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(1);
    }
}