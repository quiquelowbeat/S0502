package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.controllers;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services.PlayerServiceImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerServiceImpl playerService;

    private String token;
    private PlayerDto playerDto;
    private static final Logger logger = Logger.getLogger(PlayerControllerTest.class.getName());

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
        playerDto = new PlayerDto();
        playerDto.setPlayerId("1L");
        playerDto.setName("FooFighter");
        playerDto.setDate(Calendar.getInstance().getTime());
        playerDto.setWinningPercentage(50.00);
    }

    @Test
    void createPlayer_withName_withToken() throws Exception {
        when(playerService.createPlayer("FooFighter")).thenReturn(playerDto);
        String response = mockMvc.perform(post("/private/players/{name}", "FooFighter")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("FooFighter"))
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @Test
    void updatePlayerName() {
    }

    @Test
    void deleteAllGamesByPlayerId() {
    }

    @Test
    void getAllPlayers() {
    }

    @Test
    void getRankingOfAllPlayers() {
    }

    @Test
    void getLoser() {
    }

    @Test
    void getWinner() {
    }
}