package cat.itacademy.s04.t02.n01.controllers;

import cat.itacademy.s04.t02.n01.dto.FruitDTO;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruit")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @GetMapping
    public List<FruitResponseDTO> list() {
        return fruitService.getAll();
    }

    @PostMapping
    public ResponseEntity<FruitResponseDTO> create(@Valid @RequestBody FruitDTO dto) {
        FruitResponseDTO response = fruitService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponseDTO> updateFruit(@PathVariable Long id, @RequestBody @Valid FruitDTO dto) {
        FruitResponseDTO updated = fruitService.update(id, dto);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FruitResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(fruitService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!fruitService.existsById(id)) {
            throw new ResourceNotFoundException("Fruit with id " + id + " not found");
        }

        fruitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}