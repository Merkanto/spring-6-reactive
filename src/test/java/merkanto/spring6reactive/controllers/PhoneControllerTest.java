package merkanto.spring6reactive.controllers;

import merkanto.spring6reactive.domain.Phone;
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
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class PhoneControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testPatchIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(PhoneController.PHONE_PATH_ID, 999)
                .body(Mono.just(PhoneRepositoryTest.getTestPhone()), PhoneDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(PhoneController.PHONE_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(999)
    void testDeletePhone() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(PhoneController.PHONE_PATH_ID, 1)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @Order(4)
    void testUpdatePhoneBadRequest() {
        Phone testPhone = PhoneRepositoryTest.getTestPhone();
        testPhone.setPhoneStyle("");
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(PhoneController.PHONE_PATH_ID, 1)
                .body(Mono.just(testPhone), PhoneDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdatePhoneNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(PhoneController.PHONE_PATH_ID, 999)
                .body(Mono.just(PhoneRepositoryTest.getTestPhone()), PhoneDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testUpdatePhone() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(PhoneController.PHONE_PATH_ID, 1)
                .body(Mono.just(PhoneRepositoryTest.getTestPhone()), PhoneDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testCreatePhoneBadData() {
        Phone testPhone = PhoneRepositoryTest.getTestPhone();
        testPhone.setPhoneName("");
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(PhoneController.PHONE_PATH)
                .body(Mono.just(testPhone), PhoneDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testCreatePhone() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(PhoneController.PHONE_PATH)
                .body(Mono.just(PhoneRepositoryTest.getTestPhone()), PhoneDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/phone/4");
    }

    @Test
    void testGetByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(PhoneController.PHONE_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1)
    void testGetById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(PhoneController.PHONE_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(PhoneDTO.class);
    }

    @Test
    @Order(2)
    void testListPhones() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(PhoneController.PHONE_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }
}