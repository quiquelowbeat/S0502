package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.repositories;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.domains.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
// Those tests will use an embedded in-memory MongoDB process by default if it is available.
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class PlayerRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void notEmpty(){
        assertThat(mongoTemplate.findAll(Player.class)).isNotEmpty();
    }
}