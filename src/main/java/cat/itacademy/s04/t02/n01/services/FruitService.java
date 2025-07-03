package cat.itacademy.s04.t02.n01.services;

import cat.itacademy.s04.t02.n01.dto.FruitDTO;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDTO;

import java.util.List;

public interface FruitService {
    List<FruitResponseDTO> getAll();
    FruitResponseDTO getById(Long id);
    FruitResponseDTO create(FruitDTO fruitDTO);
    FruitResponseDTO update(Long id, FruitDTO dto);
    boolean existsById(Long id);
    void delete(Long id);

}
