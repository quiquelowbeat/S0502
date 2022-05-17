package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.repositories.PlayerRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    // Creamos un objecto "mocked" playerRepository. Es un objeto "fake" que hará la función de playerRepositoty y+
    // mapper, pero deberemos indicarles qué funciones y valores retornarán, puesto que sólo se usan para testing. De esta
    // manera podemos usarlos sin alterar los objetos reales a los que representan.
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private Mapper mapper;

    // Inyectamos los mocks en la clase playerServiceImpl
    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    private Player player;
    private PlayerDto playerDto;

    @BeforeEach
    void setup(){
        player = Player.getInstance("FooFighter");
        player.setPlayerId("1L");
        player.setDate(Calendar.getInstance().getTime());
        player.setTotalWins(0);
        player.setWinningPercentage(0.0);
        playerDto = new PlayerDto();
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setName(player.getName());
        playerDto.setDate(player.getDate());
        playerDto.setWinningPercentage(player.getWinningPercentage());
    }

    @DisplayName("JUnit test for createPlayer method")
    @Test
    void givenPlayerConvertedToDtoObject_whenCreatePlayer_thenReturnCreatedPlayerDto() {
        // given - precondition or setup
        given(mapper.toPlayerDto(playerRepository.save(player))).willReturn(playerDto);

        // when -  action or the behaviour that we are going test
        PlayerDto createdPlayer = playerServiceImpl.createPlayer(player.getName());

        // then - verify the output
        assertThat(createdPlayer).isNotNull();
    }

    @DisplayName("JUnit test for updatePlayerName method")
    @Test
    void givenPlayerObject_whenUpdatePlayer_thenReturnUpdatedPlayer() {
        // given
        given(playerRepository.findByPlayerId("1L")).willReturn(Optional.of(player));
        player.setName("NotFoo");
        playerDto.setName(player.getName());
        given(mapper.toPlayerDto(playerRepository.save(player))).willReturn(playerDto);

        // when
        PlayerDto updatedPlayer = playerServiceImpl.updatePlayerName(playerDto);

        // then
        assertEquals("NotFoo", updatedPlayer.getName());
    }

    /*
    @DisplayName("JUnit test for updatePlayerName method - using fake ID")
    @Test
    void givenPlayerObject_whenUpdatePlayer_thenThrowsException() {
        // given
        given(playerRepository.findByPlayerId("4L")).willReturn(Optional.empty());
        // when
        // then
        verify(playerRepository, never()).save(any(Player.class));
    }

    */

    @DisplayName("JUnit test for newGame method")
    @Test
    void newGame() {
    }

    /*
    @DisplayName("JUnit test for deleteAllGamesByPlayerId method")
    @Test
    void deleteAllGamesByPlayerId() {
    }

    @DisplayName("JUnit test for getAllPlayers method")
    @Test
    void getAllPlayers() {
    }

    @DisplayName("JUnit test for getGamesByPlayerId method")
    @Test
    void getGamesByPlayerId() {
    }

    @DisplayName("JUnit test for getRankingOfAllPlayers method")
    @Test
    void getRankingOfAllPlayers() {
    }

    @DisplayName("JUnit test for getLoser method")
    @Test
    void getLoser() {
    }

    @DisplayName("JUnit test for getWinner method")
    @Test
    void getWinner() {
    }

    @DisplayName("JUnit test for findPlayer method")
    @Test
    void findPlayer() {
    }
    */

}