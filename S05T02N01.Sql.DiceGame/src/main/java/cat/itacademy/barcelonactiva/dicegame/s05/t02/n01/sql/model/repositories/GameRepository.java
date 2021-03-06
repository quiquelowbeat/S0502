package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.repositories;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
