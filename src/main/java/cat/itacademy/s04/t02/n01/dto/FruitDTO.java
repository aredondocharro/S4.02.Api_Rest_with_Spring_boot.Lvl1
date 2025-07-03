package cat.itacademy.s04.t02.n01.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FruitDTO {


    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity (KG) is required")
    @Min(value = 1, message = "Quantity must be at least 1 KG")
    private int quantityKG;

    public FruitDTO() {}

    public FruitDTO(String name, Integer quantityKG) {
        this.name = name;
        this.quantityKG = quantityKG;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantityKG() {
        return quantityKG;
    }

    public void setQuantityKG(Integer quantityKG) {
        this.quantityKG = quantityKG;
    }
}