package cat.itacademy.s04.t02.n01.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantityKG;

    public Fruit() {
    }

    public Fruit(Long id, String name, int quantityKG) {
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