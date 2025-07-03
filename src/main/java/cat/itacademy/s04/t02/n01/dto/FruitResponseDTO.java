package cat.itacademy.s04.t02.n01.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FruitResponseDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity (KG) is required")
    @Min(value = 1, message = "Quantity must be at least 1 KG")
    private int quantityKG;

    public FruitResponseDTO() {}

    public FruitResponseDTO(Long id, String name, int quantityKG) {
        this.id = id;
        this.name = name;
        this.quantityKG = quantityKG;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityKG() {
        return quantityKG;
    }

    public void setQuantityKG(int quantityKG) {
        this.quantityKG = quantityKG;
    }
}