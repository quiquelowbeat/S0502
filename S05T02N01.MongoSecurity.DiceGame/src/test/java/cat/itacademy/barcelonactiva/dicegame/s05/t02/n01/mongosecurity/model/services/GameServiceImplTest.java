package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.repositories.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class GameServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerService playerServiceImpl;

    @Mock
    private Mapper mapper;

    @DisplayName("JUnit test for newGame method")
    @Test
    void newGame() {
    }

    @DisplayName("JUnit test for getGamesByPlayerId method")
    @Test
    void getGamesByPlayerId() {
    }
}