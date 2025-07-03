package cat.itacademy.s04.t02.n01;

import cat.itacademy.s04.t02.n01.dto.FruitDTO;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.model.Fruit;
import cat.itacademy.s04.t02.n01.repository.FruitRepository;
import cat.itacademy.s04.t02.n01.services.FruitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FruitServiceImplTest {

    @Mock
    private FruitRepository repo;

    @InjectMocks
    private FruitServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Fruit> fruits = List.of(
                new Fruit(1L, "Apple", 5),
                new Fruit(2L, "Banana", 10)
        );
        when(repo.findAll()).thenReturn(fruits);

        List<FruitResponseDTO> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).getName());
    }

    @Test
    void testGetByIdFound() {
        Fruit fruit = new Fruit(1L, "Pear", 4);
        when(repo.findById(1L)).thenReturn(Optional.of(fruit));

        FruitResponseDTO result = service.getById(1L);

        assertEquals("Pear", result.getName());
        assertEquals(4, result.getQuantityKG());
    }

    @Test
    void testGetByIdNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void testCreate() {
        FruitDTO dto = new FruitDTO("Peach", 7);
        Fruit savedFruit = new Fruit(1L, "Peach", 7);
        when(repo.save(any(Fruit.class))).thenReturn(savedFruit);

        FruitResponseDTO result = service.create(dto);

        assertEquals("Peach", result.getName());
        assertEquals(7, result.getQuantityKG());
    }

    @Test
    void testUpdateExisting() {
        Fruit existing = new Fruit(1L, "OldName", 3);
        FruitDTO update = new FruitDTO("NewName", 6);

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Fruit.class))).thenReturn(existing);

        FruitResponseDTO result = service.update(1L, update);

        assertEquals("NewName", result.getName());
        assertEquals(6, result.getQuantityKG());
    }

    @Test
    void testUpdateNotFound() {
        FruitDTO dto = new FruitDTO("Kiwi", 2);
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, dto));
    }

    @Test
    void testDeleteExisting() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repo).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }

    @Test
    void testExistsById() {
        when(repo.existsById(1L)).thenReturn(true);
        assertTrue(service.existsById(1L));
    }
}