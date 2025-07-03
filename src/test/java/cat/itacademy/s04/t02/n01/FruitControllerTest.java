
package cat.itacademy.s04.t02.n01;

import cat.itacademy.s04.t02.n01.controllers.FruitController;
import cat.itacademy.s04.t02.n01.dto.FruitDTO;
import cat.itacademy.s04.t02.n01.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.services.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FruitController.class)
public class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FruitService fruitService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllFruits_emptyList() throws Exception {
        when(fruitService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/fruit"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetAllFruits_withData() throws Exception {
        when(fruitService.getAll()).thenReturn(List.of(
                new FruitResponseDTO(1L, "Apple", 10),
                new FruitResponseDTO(2L, "Banana", 5)
        ));

        mockMvc.perform(get("/fruit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[1].quantityKG").value(5));
    }

    @Test
    void testGetFruitById_found() throws Exception {
        when(fruitService.getById(1L)).thenReturn(new FruitResponseDTO(1L, "Pear", 8));

        mockMvc.perform(get("/fruit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pear"))
                .andExpect(jsonPath("$.quantityKG").value(8));
    }

    @Test
    void testGetFruitById_notFound() throws Exception {
        when(fruitService.getById(99L))
                .thenThrow(new ResourceNotFoundException("Fruit not found with id: 99"));

        mockMvc.perform(get("/fruit/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateFruit_valid() throws Exception {
        FruitDTO dto = new FruitDTO("Strawberry", 12);
        FruitResponseDTO responseDTO = new FruitResponseDTO(3L, "Strawberry", 12);

        when(fruitService.create(any(FruitDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Strawberry"))
                .andExpect(jsonPath("$.quantityKG").value(12));
    }

    @Test
    void testCreateFruit_invalidName() throws Exception {
        FruitDTO dto = new FruitDTO("", 10);

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateFruit_negativeQuantity() throws Exception {
        FruitDTO dto = new FruitDTO("Orange", -5);

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteFruit_existingId() throws Exception {
        when(fruitService.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/fruit/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteFruit_nonExistingId() throws Exception {
        when(fruitService.existsById(999L)).thenReturn(false);

        mockMvc.perform(delete("/fruit/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateFruit_invalidContentType() throws Exception {
        String plainText = "Strawberry,12";

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(plainText))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void testCreateFruit_malformedJson() throws Exception {
        String malformedJson = "{\"name\":\"Mango\", \"quantityKG\":}";

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateFruit_valid() throws Exception {
        FruitDTO dto = new FruitDTO("Watermelon", 25);
        FruitResponseDTO responseDTO = new FruitResponseDTO(1L, "Watermelon", 25);

        when(fruitService.update(any(Long.class), any(FruitDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/fruit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Watermelon"))
                .andExpect(jsonPath("$.quantityKG").value(25));
    }
}