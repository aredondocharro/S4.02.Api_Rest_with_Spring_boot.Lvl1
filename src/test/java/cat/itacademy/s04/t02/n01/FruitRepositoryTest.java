
package cat.itacademy.s04.t02.n01;

import cat.itacademy.s04.t02.n01.model.Fruit;
import cat.itacademy.s04.t02.n01.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FruitRepositoryTest {

    @Autowired
    private FruitRepository repo;

    @Test
    void testSaveAndFindById() {
        Fruit fruit = new Fruit(null, "Melon", 3);
        Fruit saved = repo.save(fruit);

        Optional<Fruit> found = repo.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Melon", found.get().getName());
    }

    @Test
    void testExistsById() {
        Fruit fruit = new Fruit(null, "Peach", 5);
        Fruit saved = repo.save(fruit);

        boolean exists = repo.existsById(saved.getId());
        assertTrue(exists);
    }
}
