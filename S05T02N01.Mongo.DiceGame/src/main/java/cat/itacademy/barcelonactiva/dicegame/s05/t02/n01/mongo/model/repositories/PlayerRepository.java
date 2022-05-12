package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.repositories;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.domains.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
    Optional<Player> findByPlayerId(String id);
    // List<Game> findByPlayerID(String id);
}
