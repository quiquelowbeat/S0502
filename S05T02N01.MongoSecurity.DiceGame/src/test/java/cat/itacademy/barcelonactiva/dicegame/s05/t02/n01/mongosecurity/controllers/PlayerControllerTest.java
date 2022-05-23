package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.controllers;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services.PlayerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerServiceImpl playerService;

    private String token;
    private PlayerDto playerDto, playerDto2;
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Objecto que mapea objetos a JSON. Muy práctico.
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
        // Objetos Dto para los tests
        playerDto = new PlayerDto();
        playerDto.setPlayerId("1L");
        playerDto.setName("FooFighter");
        playerDto.setDate(Calendar.getInstance().getTime());
        playerDto.setWinningPercentage(50.00);

        playerDto2 = new PlayerDto();
        playerDto2.setPlayerId("2L");
        playerDto2.setName("Foo2");
        playerDto2.setDate(Calendar.getInstance().getTime());
        playerDto2.setWinningPercentage(100.0);
    }

    @DisplayName("JUnit test for createPlayer_withName_withToken method")
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

    @DisplayName("JUnit test for updatePlayerName_withToken method")
    @Test
    void updatePlayerName_withToken() throws Exception {
        playerDto.setName("FooFoo");
        when(playerService.updatePlayerName(playerDto)).thenReturn(playerDto);

        String response = mockMvc.perform(put("/private/players")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FooFoo"))
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for deleteAllGamesByPlayerId method")
    @Test
    void deleteAllGamesByPlayerId() throws Exception {
        mockMvc.perform(delete("/private/players/{id}/games", "1L")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @DisplayName("JUnit test for deleteAllGamesByPlayerId_wrongId method")
    @Test
    void deleteAllGamesByPlayerId_wrongId() throws Exception {
        // De la siguiente manera podemos usar el método deleteAllGamesByPlayerId con void.
        doThrow(NullPointerException.class).when(playerService).deleteAllGamesByPlayerId("2L");
        mockMvc.perform(delete("/private/players/{id}/games", "2L")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for getAllPlayers method")
    @Test
    void getAllPlayers() throws Exception {
        List<PlayerDto> playerDtoList = List.of(playerDto, playerDto2);
        given(playerService.getAllPlayers()).willReturn(playerDtoList);
        String response = mockMvc.perform(get("/private/players")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for getRankingOfAllPlayers method")
    @Test
    void getRankingOfAllPlayers() throws Exception {
        mockMvc.perform(get("/private/players/ranking")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("JUnit test for getLoser method")
    @Test
    void getLoser() throws Exception {
        given(playerService.getLoser()).willReturn(playerDto);
        mockMvc.perform(get("/private/players/ranking/loser")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FooFighter"))
                .andDo(print());
    }

    @DisplayName("JUnit test for getWinner method")
    @Test
    void getWinner() throws Exception {
        given(playerService.getWinner()).willReturn(playerDto2);
        mockMvc.perform(get("/private/players/ranking/winner")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Foo2"))
                .andDo(print());
    }
}