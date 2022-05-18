package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.domains.Game;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // Importante para que Mockito funcione correctamente.
class PlayerServiceImplTest {

    // Creamos un objecto "mocked" playerRepository. Es un objeto "fake" que hará la función de playerRepositoty y+
    // mapper, pero deberemos indicarles qué funciones y valores retornarán, puesto que sólo se usan para testing. De esta
    // manera podemos usarlos sin alterar los objetos reales a los que representan.
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private Mapper mapper;

    // Inyectamos los mocks en la clase playerServiceImpl (mockito no me permite inyectar la interface playerService
    // como hace @Autowired)
    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player;
    private Player player2;
    private PlayerDto playerDto;
    private PlayerDto playerDto2;

    @BeforeEach
    void setup(){
        // player
        player = Player.getInstance("FooFighter");
        player.setPlayerId("1L");
        player.setDate(Calendar.getInstance().getTime());
        player.setTotalWins(4);
        player.setWinningPercentage(35.00);
        player.getGames().add(Game.getInstance());
        player.getGames().add(Game.getInstance());
        player.getGames().add(Game.getInstance());

        // player2
        player2 = Player.getInstance("Quique");
        player2.setPlayerId("2L");
        player2.setWinningPercentage(55.00);

        // playerDto
        playerDto = new PlayerDto();
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setName(player.getName());
        playerDto.setDate(player.getDate());
        playerDto.setWinningPercentage(player.getWinningPercentage());

        // playerDto2
        playerDto2 = new PlayerDto();
        playerDto2.setPlayerId(player2.getPlayerId());
        playerDto2.setName(player2.getName());
        playerDto2.setDate(player2.getDate());
        playerDto2.setWinningPercentage(player2.getWinningPercentage());
    }

    @DisplayName("JUnit test for createPlayer method")
    @Test
    void createPlayer() {
        // given - precondition or setup - en esta parte del test indicamos el comportamiento del @Mock.
        given(mapper.toPlayerDto(playerRepository.save(player))).willReturn(playerDto);
        // when -  action or the behaviour that we are going test
        PlayerDto createdPlayer = playerService.createPlayer(player.getName());
        // then - verify the output
        assertThat(createdPlayer).isNotNull();
    }

    @DisplayName("JUnit test for updatePlayerName method")
    @Test
    void updatePlayerName() {
        // given
        given(playerRepository.findByPlayerId("1L")).willReturn(Optional.of(player));
        playerDto.setName("NotFoo");
        given(mapper.toPlayerDto(playerRepository.save(player))).willReturn(playerDto);
        // when
        PlayerDto updatedPlayer = playerService.updatePlayerName(playerDto);

        // then
        assertEquals("NotFoo", updatedPlayer.getName());
    }

    @DisplayName("JUnit test for deleteAllGamesByPlayerId method")
    @Test
    void deleteAllGamesByPlayerId() {
        // given
        given(playerRepository.findByPlayerId("1L")).willReturn(Optional.of(player));
        // when
        playerService.deleteAllGamesByPlayerId("1L");
        // then
        assertThat(player.getGames().size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getAllPlayers method")
    @Test
    void getAllPlayers() {
        // given
        given(playerRepository.findAll()).willReturn(List.of(player, player2));
        // when
        List<PlayerDto> playerList = playerService.getAllPlayers();
        // then
        assertThat(playerList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getRankingOfAllPlayers method")
    @Test
    void getRankingOfAllPlayers() {
        // given
        given(playerRepository.findAll()).willReturn(List.of(player, player2));
        // when
        Map<String, Double> playerMap = playerService.getRankingOfAllPlayers();
        // then
        assertThat(playerMap.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getLoser method")
    @Test
    void getLoser() {
        // given
        List<Player> playerList = List.of(player, player2);
        given(playerRepository.findAll()).willReturn(playerList);
        given(mapper.toPlayerDto(player)).willReturn(playerDto);
        // when
        PlayerDto playerLoser = playerService.getLoser();
        // then
        assertThat(playerLoser.getPlayerId()).isEqualTo("1L");
    }

    @DisplayName("JUnit test for getWinner method")
    @Test
    void getWinner() {
        // given
        List<Player> playerList = List.of(player, player2);
        given(playerRepository.findAll()).willReturn(playerList);
        given(mapper.toPlayerDto(player2)).willReturn(playerDto2);
        // when
        PlayerDto playerWinnerr = playerService.getWinner();
        // then
        assertThat(playerWinnerr.getPlayerId()).isEqualTo("2L");
    }

    @DisplayName("JUnit test for findPlayer method")
    @Test
    void findPlayer() {
        // given
        given(playerRepository.findByPlayerId("1L")).willReturn(Optional.of(player));
        // when
        Player playerFound = playerService.findPlayer("1L");
        // then
        assertEquals(player, playerFound);
    }

    @DisplayName("JUnit test for findPlayer_NotFound method")
    @Test
    void findPlayer_NotFound() {
        // given
        // when
        Player playerFound = playerService.findPlayer("3L");
        // then
        assertThat(playerFound).isNull();
    }
}