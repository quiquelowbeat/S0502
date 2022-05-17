package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.domains;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void getInstanceWithName() {
        // when
        Player player = Player.getInstance("FooFighter");

        // then
        assertEquals(player.getName(), "FooFighter");
    }

    @Test
    void getInstanceWithoutName() {
        // when
        Player player = Player.getInstance(null);

        // then
        assertEquals(player.getName(), "ANONIMOUS");
    }
}