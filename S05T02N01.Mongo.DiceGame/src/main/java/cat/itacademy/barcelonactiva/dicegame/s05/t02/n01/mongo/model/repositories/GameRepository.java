package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.repositories;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.domains.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    //List<Game> findByPlayer(Player player);
}
