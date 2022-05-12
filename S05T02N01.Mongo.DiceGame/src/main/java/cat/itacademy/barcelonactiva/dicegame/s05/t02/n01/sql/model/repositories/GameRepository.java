package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.repositories;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains.Game;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayer(Player player);
}
