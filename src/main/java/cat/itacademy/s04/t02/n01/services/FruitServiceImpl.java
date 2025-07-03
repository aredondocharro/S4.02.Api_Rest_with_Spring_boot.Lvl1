package cat.itacademy.s04.t02.n01.services;

import cat.itacademy.s04.t02.n01.dto.FruitDTO;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.model.Fruit;
import cat.itacademy.s04.t02.n01.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FruitServiceImpl implements FruitService {

    @Autowired
    private FruitRepository repo;

    @Override
    public List<FruitResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FruitResponseDTO getById(Long id) {
        Fruit fruit = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + id));
        return toDTO(fruit);
    }

    @Override
    public FruitResponseDTO create(FruitDTO dto) {
        Fruit fruit = new Fruit(null, dto.getName(), dto.getQuantityKG());
        Fruit saved = repo.save(fruit);
        return toDTO(saved);
    }
    @Override
    public FruitResponseDTO update(Long id, FruitDTO dto) {
        Fruit fruit = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + id));
        fruit.setName(dto.getName());
        fruit.setQuantityKG(dto.getQuantityKG());
        repo.save(fruit);
        return convertToResponseDTO(fruit);
    }

    private FruitResponseDTO convertToResponseDTO(Fruit fruit) {
        return new FruitResponseDTO(
                fruit.getId(),
                fruit.getName(),
                fruit.getQuantityKG()
        );
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Fruit with id " + id + " does not exist.");
        }
        repo.deleteById(id);
    }

    private FruitResponseDTO toDTO(Fruit fruit) {
        return new FruitResponseDTO(fruit.getId(), fruit.getName(), fruit.getQuantityKG());
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}