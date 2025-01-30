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

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
        @ConfigureWireMock(
                name = "bookstore-api-mock",
                port = 8080
        )})
class BookControllerTest {

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
    @DisplayName("Should return OK status with books list for an author")
    void shouldReturnOkStatusWithBooksListForAuthor() throws Exception {

        mockUserService.stubFor(WireMock.get(urlEqualTo("/bookstore-api/authors/8/books"))
                .willReturn(WireMock.okJson("""
                        {
                            "id": 8,
                            "name": "Viúva Negra",
                            "books": [
                                {
                                    "id": 1,
                                    "title": "Segredos ocultos",
                                    "description": "Jornada de espionagem",
                                    "category": "POESIA"
                                }
                            ]
                        }
                        """)));

        mockMvc.perform(get("/authors/8/books")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.nome").value("Viúva Negra"))
                .andExpect(jsonPath("$.livros.length()").value(1))
                .andExpect(jsonPath("$.livros[0].titulo").value("Segredos ocultos"));
    }

    @Test
    @DisplayName("Should return Created status when book is successfully created")
    void shouldReturnCreatedStatusWhenBookIsSuccessfullyCreated() throws Exception {

        mockUserService.stubFor(WireMock.post(urlEqualTo("/bookstore-api/authors/8/books"))
                .willReturn(WireMock.created()
                        .withHeader("Location", "/authors/8/books/5")));

        String bookRequestJson = """
                {
                    "title": "Novo Livro",
                    "description": "Descrição do novo livro",
                    "category": "FICCAO"
                }
                """;

        mockMvc.perform(post("/authors/8/books")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookRequestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/authors/8/books/5"));
    }

    @Test
    @DisplayName("Should return OK status and details of the book")
    void shouldReturnOkStatusAndDetailsOfTheBook() throws Exception {

        mockUserService.stubFor(WireMock.get(urlEqualTo("/bookstore-api/authors/8/books/1"))
                .willReturn(WireMock.okJson("""
                {
                    "id": 1,
                    "name": "Altor desconhecido",
                    "books": [
                        {
                            "id": 1,
                            "title": "Segredos ocultos",
                            "description": "Jornada de espionagem",
                            "category": "POESIA"
                        }
                    ]
                }
                """)));


        mockMvc.perform(get("/authors/8/books/1")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Altor desconhecido"))
                .andExpect(jsonPath("$.livros.length()").value(1))
                .andExpect(jsonPath("$.livros[0].titulo").value("Segredos ocultos"))
                .andExpect(jsonPath("$.livros[0].descricao").value("Jornada de espionagem"))
                .andExpect(jsonPath("$.livros[0].categoria").value("POESIA"));
    }

    @Test
    @DisplayName("Should return Not Found status when book is not found")
    void shouldReturnNotFoundStatusWhenBookIsNotFound() throws Exception {

        mockUserService.stubFor(WireMock.get(urlEqualTo("/bookstore-api/authors/8/books/99"))
                .willReturn(WireMock.notFound()
                        .withBody("""
                                {
                                    "error": "Book not found"
                                }
                                """)));

        mockMvc.perform(get("/authors/8/books/99")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return No Content when book is successfully deleted")
    void shouldReturnNoContentWhenBookIsSuccessfullyDeleted() throws Exception {

        mockUserService.stubFor(WireMock.delete(urlEqualTo("/bookstore-api/authors/8/books/1"))
                .willReturn(WireMock.noContent()));

        mockMvc.perform(delete("/authors/8/books/1")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return OK status when book is successfully updated")
    void shouldReturnOkStatusWhenBookIsSuccessfullyUpdated() throws Exception {

        String updatedBookJson = """
                {
                    "title": "Livro Atualizado",
                    "description": "Descrição Atualizada",
                    "category": "DRAMA"
                }
                """;

        mockUserService.stubFor(WireMock.put(urlEqualTo("/bookstore-api/authors/8/books/1"))
                .willReturn(WireMock.okJson("""
                        {
                            "id": 1,
                            "title": "Livro Atualizado",
                            "description": "Descrição Atualizada",
                            "category": "DRAMA"
                        }
                        """)));

        mockMvc.perform(put("/authors/8/books/1")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Livro Atualizado"));
    }

    @Test
    @DisplayName("Should return Not Found status when the book to update does not exist")
    void shouldReturnNotFoundStatusWhenBookToUpdateDoesNotExist() throws Exception {

        String updatedBookJson = """
                {
                    "title": "Inexistente",
                    "description": "Descrição Inexistente",
                    "category": "DRAMA"
                }
                """;

        mockUserService.stubFor(WireMock.put(urlEqualTo("/bookstore-api/authors/8/books/99"))
                .willReturn(WireMock.notFound()
                        .withBody("""
                                {
                                    "error": "Book not found"
                                }
                                """)));

        mockMvc.perform(put("/authors/8/books/99")
                        .header("Authorization", "Bearer my_token_is_valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(status().isNotFound());
    }
}

