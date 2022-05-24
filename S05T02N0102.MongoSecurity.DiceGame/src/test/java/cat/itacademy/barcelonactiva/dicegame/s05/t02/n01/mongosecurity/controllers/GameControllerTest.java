package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.controllers;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services.GameServiceImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameServiceImpl gameService;

    private String token;
    private GameDto gameDto;
    private static final Logger logger = Logger.getLogger(GameControllerTest.class.getName());


    @BeforeEach
    void setUp() throws Exception {
        // Llamamos a la API de Auth0 para solicitar el token JWT y poder autenticarnos.
        // El endpoint es una uri HTTPS, por eso usamos la dependencia Unirest.
        HttpResponse<String> result = Unirest.post("https://dev-wi2uwid8.us.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"picC8bkOiMYP0vgLBsoZbeE8NDnurAxQ\",\"client_secret\":\"-Wuk0GJPSsfj8wLB5dwqsMoq0UFEG8blnGIWcAK772lf0xIa3-ZZqYdmx1BxCBjb\",\"audience\":\"http://localhost:9001/private\",\"grant_type\":\"client_credentials\"}")
                .asString();
        // Recuperamos el body de la respuesta y extraemos el JWT para poder hacer las requests del test.
        String response = result.getBody();
        response = response.replace("{\"access_token\":\"", "");
        token =  response.replace("\",\"scope\":\"write create delete\",\"expires_in\":86400,\"token_type\":\"Bearer\"}", "");
        // Objeto Dto para los tests
        gameDto = new GameDto();
        gameDto.setDice1(1);
        gameDto.setDice2(6);
        gameDto.setResult("WIN");
    }

    @DisplayName("JUnit test for newGame_withToken() method")
    @Test
    void newGame_withToken() throws Exception {
        // when - mockeamos el gameService.newGame
        when(gameService.newGame("1L")).thenReturn(gameDto);
        // Hacemos la petición Http.
        String response = mockMvc.perform(post("/private/players/{id}/games", "1L")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)) // Contenido del header en JSON
                .andExpect(status().isCreated())
                // Usamos jsonPath para comprobar la respuesta del mockMvc y ver que coincide con el resultado de gameDto
                .andExpect(jsonPath("$.dice1").value(1))
                .andExpect(jsonPath("$.dice2").value(6))
                .andExpect(jsonPath("$.result").value("WIN")) // .andExpect funciona como assert
                // Devolvemos la respuesta en String para ver el log del resultado
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for newGame_withToken_nullObject method")
    @Test
    void newGame_withToken_nullObject() throws Exception {
        when(gameService.newGame("1L")).thenThrow(NullPointerException.class);
        mockMvc.perform(post("/private/players/{id}/games", "1L")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for newGame_withNoToken method")
    @Test
    void newGame_withNoToken() throws Exception {
        mockMvc.perform(post("/private/players/{id}/games", "1L"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("JUnit test for getGamesByPlayerId_withToken method")
    @Test
    void getGamesByPlayerId_withToken() throws Exception {
        // Creamos un objeto gameDto nuevo para añadirlo a la lista gameDtoList y usarlo en el mock.
        GameDto gameDto2 = new GameDto();
        gameDto2.setDice1(1);
        gameDto2.setDice2(5);
        gameDto2.setResult("LOSE");
        List<GameDto> gameDtoList = List.of(gameDto, gameDto2);

        when(gameService.getGamesByPlayerId("1L")).thenReturn(gameDtoList);

        String response = mockMvc.perform(get("/private/players/{id}/games", "1L")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for getGamesByPlayerId_withToken_NullObject method")
    @Test
    void getGamesByPlayerId_withToken_NullObject() throws Exception {
        when(gameService.getGamesByPlayerId("1L")).thenThrow(NullPointerException.class);
        mockMvc.perform(get("/private/players/{id}/games", "1L")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @DisplayName("JUnit test for getGamesByPlayerId_withNoToken method")
    @Test
    void getGamesByPlayerId_withNoToken() throws Exception {
        mockMvc.perform(get("/private/players/{id}/games", "1L"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}