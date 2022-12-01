package tolik.home.springboot.rickandmortyapp.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tolik.home.springboot.rickandmortyapp.model.Gender;
import tolik.home.springboot.rickandmortyapp.model.MovieCharacter;
import tolik.home.springboot.rickandmortyapp.model.Status;
import tolik.home.springboot.rickandmortyapp.service.MovieCharacterServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MovieCharacterControllerTest {
    @MockBean
    private MovieCharacterServiceImpl movieCharacterService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void getRandom_shouldShowRandomCharacter_Ok() {
        MovieCharacter albertEinstein = new MovieCharacter();
        albertEinstein.setId(11L);
        albertEinstein.setName("Albert Einstein");
        albertEinstein.setGender(Gender.MALE);
        albertEinstein.setStatus(Status.DEAD);
        Mockito.when(movieCharacterService.getRandomCharacters()).thenReturn(albertEinstein);
        RestAssuredMockMvc
                .when()
                .get("/movie-characters/random")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(11))
                .body("name", Matchers.equalTo("Albert Einstein"))
                .body("status", Matchers.equalTo("DEAD"))
                .body("gender", Matchers.equalTo("MALE"));
    }

    @Test
    public void findByName_shouldShowCharactersContainStringInName_OK() {
        MovieCharacter rickSanchez = new MovieCharacter();
        rickSanchez.setId(1L);
        rickSanchez.setExternalId(1L);
        rickSanchez.setName("Rick Sanchez");
        rickSanchez.setGender(Gender.MALE);
        rickSanchez.setStatus(Status.ALIVE);
        MovieCharacter mortySmith = new MovieCharacter();
        mortySmith.setId(2L);
        mortySmith.setExternalId(2L);
        mortySmith.setName("Morty Smith");
        mortySmith.setGender(Gender.MALE);
        mortySmith.setStatus(Status.ALIVE);
        MovieCharacter summerSmith = new MovieCharacter();
        summerSmith.setId(3L);
        summerSmith.setExternalId(3L);
        summerSmith.setName("Summer Smith");
        summerSmith.setGender(Gender.FEMALE);
        summerSmith.setStatus(Status.ALIVE);
        String namePart = "Smith";
        Mockito.when(movieCharacterService.findAllByNameContains(namePart))
                .thenReturn((List.of(mortySmith, summerSmith)));
        RestAssuredMockMvc
                .given()
                .queryParam("name", namePart)
                .when()
                .get("/movie-characters/by-name")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(2))
                .body("[0].name", Matchers.equalTo("Morty Smith"))
                .body("[0].status", Matchers.equalTo("ALIVE"))
                .body("[0].gender", Matchers.equalTo("MALE"))
                .body("[1].id", Matchers.equalTo(3))
                .body("[1].name", Matchers.equalTo("Summer Smith"))
                .body("[1].status", Matchers.equalTo("ALIVE"))
                .body("[1].gender", Matchers.equalTo("FEMALE"));
    }
}
