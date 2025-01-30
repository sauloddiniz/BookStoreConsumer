package br.com.bookstoreconsumer.adapters.input;

import br.com.bookstoreconsumer.adapters.configuration.JwtUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
        @ConfigureWireMock(
                name = "bookstore-api-mock",
                port = 8080
        )})
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @InjectWireMock("bookstore-api-mock")
    private WireMockServer mockUserService;

    @BeforeEach
    void setUp() {
        when(jwtUtil.validJwt(anyString()))
                .thenReturn(true);
        when(jwtUtil.getEmail(anyString()))
                .thenReturn("my_email");
    }

    @Test
    @DisplayName("Should return OK status and authors list when the request is valid")
    void shouldReturnOkStatusAndAuthorsList_WhenRequestIsValid() throws Exception {

        mockUserService.stubFor(WireMock.get(urlEqualTo("/bookstore-api/authors?books=false"))
                .willReturn(WireMock.okJson(getSampleAuthorsJson())));

        mockMvc.perform(get("/authors?books=false")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Viúva Negra"))
                .andExpect(jsonPath("$[1].nome").value("Thor"));
    }


    @Test
    @DisplayName("Should return OK status and authors list with books when the request is valid")
    void shouldReturnOkStatusAndAuthorsListWithBooks_WhenRequestIsValid() throws Exception {

        mockUserService.stubFor(WireMock.get(urlEqualTo("/bookstore-api/authors?books=true"))
                .willReturn(WireMock.okJson(getSampleAuthorsJson())));

        mockMvc.perform(get("/authors?books=true")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Viúva Negra"))
                .andExpect(jsonPath("$[0].livros.length()").value(3))
                .andExpect(jsonPath("$[0].livros[0].titulo").value("Segredos ocultos"))
                .andExpect(jsonPath("$[0].livros[1].titulo").value("Raízes da realeza"))
                .andExpect(jsonPath("$[0].livros[2].titulo").value("O guardião do tempo"))
                .andExpect(jsonPath("$[1].nome").value("Thor"));
    }

    @Test
    @DisplayName("Should return OK status and an author when the request is valid")
    void shouldReturnOkStatusAndAuthor_WhenRequestIsValid() throws Exception {

        mockUserService.stubFor(WireMock.get(urlEqualTo("/bookstore-api/authors/8"))
                .willReturn(WireMock.okJson("""
                        {
                          "id": 8,
                          "name": "Viúva Negra",
                          "books": [
                            {
                              "id": 8,
                              "title": "Segredos ocultos",
                              "description": "Uma jornada de espionagem e mistério",
                              "category": "POESIA"
                            }
                          ]
                        }
                        """)));

        mockMvc.perform(get("/authors/8")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.nome").value("Viúva Negra"))
                .andExpect(jsonPath("$.livros.length()").value(1))
                .andExpect(jsonPath("$.livros[0].titulo").value("Segredos ocultos"));
    }

    @Test
    @DisplayName("Should return Not Found status when the author does not exist")
    void shouldReturnNotFoundStatus_WhenAuthorDoesNotExist() throws Exception {

        mockUserService.stubFor(WireMock.get(urlEqualTo("/bookstore-api/authors/99"))
                .willReturn(WireMock.notFound()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "date": "2025-01-30T11:27:40.2649889",
                                     "path": "/bookstore-consumer-api/authors",
                                     "method": "POST",
                                     "error": "Author not found"
                                }
                                """)));


        mockMvc.perform(get("/authors/99")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return Created status when author is successfully created")
    void shouldReturnCreatedStatus_WhenAuthorIsSuccessfullyCreated() throws Exception {
        mockUserService.stubFor(WireMock.post(urlEqualTo("/bookstore-api/authors"))
                .willReturn(WireMock.created().withHeader("Location", "/authors/1")));

        String newAuthorJson = """
                {
                  "name": "Homem de Ferro"
                }
                """;

        mockMvc.perform(post("/authors")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAuthorJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return Bad Request status when author data is invalid")
    void shouldReturnBadRequestStatus_WhenAuthorDataIsInvalid() throws Exception {

        String invalidAuthorJson = """
                {
                  "name": ""
                }
                """;

        mockUserService.stubFor(WireMock.post(urlEqualTo("/bookstore-api/authors"))
                .willReturn(WireMock.badRequest()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "date": "2025-01-30T11:27:40.2649889",
                                     "path": "/bookstore-consumer-api/authors",
                                     "method": "POST",
                                     "error": "Name invalid: "
                                }
                                """)));

        mockMvc.perform(post("/authors")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAuthorJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return OK status and updated author when the request is valid")
    void shouldReturnOkStatusAndUpdatedAuthor_WhenRequestIsValid() throws Exception {

        String updatedAuthorJson = """
                {
                  "name": "Homem-Aranha"
                }
                """;

        mockUserService.stubFor(WireMock.put(urlEqualTo("/bookstore-api/authors/1"))
                .willReturn(WireMock.okJson("""
                        {
                          "id": 1,
                          "name": "Homem-Aranha",
                          "books": []
                        }
                        """)));

        mockMvc.perform(put("/authors/1")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAuthorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Homem-Aranha"));
    }

    @Test
    @DisplayName("Should return Not Found status when the author to update does not exist")
    void shouldReturnNotFoundStatus_WhenAuthorToUpdateDoesNotExist() throws Exception {

        String updatedAuthorJson = """
                {
                  "name": "Inexistente"
                }
                """;

        mockUserService.stubFor(WireMock.put(urlEqualTo("/bookstore-api/authors/99"))
                .willReturn(WireMock.notFound()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "date": "2025-01-30T11:27:40.2649889",
                                    "path": "/bookstore-consumer-api/authors/99",
                                    "method": "PUT",
                                    "error": "Author not found"
                                }
                                """)));

        mockMvc.perform(put("/authors/99")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAuthorJson))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Should return Bad Request status when update data is invalid")
    void shouldReturnBadRequestStatus_WhenUpdateDataIsInvalid() throws Exception {

        String invalidAuthorJson = """
                {
                  "name": ""
                }
                """;

        mockUserService.stubFor(WireMock.put(urlEqualTo("/bookstore-api/authors/1"))
                .willReturn(WireMock.badRequest()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "date": "2025-01-30T11:27:40.2649889",
                                    "path": "/bookstore-consumer-api/authors/1",
                                    "method": "PUT",
                                    "error": "Invalid data"
                                }
                                """)));

        mockMvc.perform(put("/authors/1")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAuthorJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return No Content status when author is successfully deleted")
    void shouldReturnNoContentStatus_WhenAuthorIsSuccessfullyDeleted() throws Exception {

        mockUserService.stubFor(WireMock.delete(urlEqualTo("/bookstore-api/authors/1"))
                .willReturn(WireMock.noContent()));

        mockMvc.perform(delete("/authors/1")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return Not Found status when the author to delete does not exist")
    void shouldReturnNotFoundStatus_WhenAuthorToDeleteDoesNotExist() throws Exception {

        mockUserService.stubFor(WireMock.delete(urlEqualTo("/bookstore-api/authors/99"))
                .willReturn(WireMock.notFound()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "date": "2025-01-30T11:27:40.2649889",
                                    "path": "/bookstore-consumer-api/authors/99",
                                    "method": "DELETE",
                                    "error": "Author not found"
                                }
                                """)));

        mockMvc.perform(delete("/authors/99")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private static String getSampleAuthorsJson() {
        return """
                [
                  {
                    "id": 8,
                    "name": "Viúva Negra",
                    "books": [
                      {
                        "id": 8,
                        "title": "Segredos ocultos",
                        "description": "Uma jornada de espionagem e mistério",
                        "category": "POESIA"
                      },
                      {
                        "id": 9,
                        "title": "Raízes da realeza",
                        "description": "A luta pelo poder e pela preservação das tradições",
                        "category": "FICCAO"
                      },
                      {
                        "id": 10,
                        "title": "O guardião do tempo",
                        "description": "História fascinante sobre magia e escolhas",
                        "category": "DRAMA"
                      }
                    ]
                  },
                  {
                    "id": 1,
                    "name": "Thor",
                    "books": []
                  }
                ]
                
                """;
    }


}
