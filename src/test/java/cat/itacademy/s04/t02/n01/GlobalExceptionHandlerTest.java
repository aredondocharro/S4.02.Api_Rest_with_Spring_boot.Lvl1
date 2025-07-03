
package cat.itacademy.s04.t02.n01;

import cat.itacademy.s04.t02.n01.controllers.FruitController;
import cat.itacademy.s04.t02.n01.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n01.services.FruitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FruitController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FruitService fruitService;

    @Test
    void testGetNotFound_returns404() throws Exception {
        when(fruitService.getById(999L)).thenThrow(new ResourceNotFoundException("Fruit not found with id: 999"));

        mockMvc.perform(get("/fruit/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.message").value("Fruit not found with id: 999"));
    }

    @Test
    void testValidationError_returns400() throws Exception {
        String invalidBody = """
                {
                  "name": "",
                  "quantityKG": -5
                }
                """;

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages").value(org.hamcrest.Matchers.hasItem("Name is required")))
                .andExpect(jsonPath("$.messages").value(org.hamcrest.Matchers.hasItem("Quantity must be at least 1 KG")));

    }

    @Test
    void testMalformedJson_returns400() throws Exception {
        String malformedJson = """
        {
          "name": "Apple"
          "quantityKG": 5
        }
        """;

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Malformed JSON or invalid request body"));
    }

    @Test
    void testUnsupportedMediaType_returns415() throws Exception {
        String validXml = "<fruit><name>Apple</name><quantityKG>5</quantityKG></fruit>";

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_XML)
                        .content("<fruit><name>Apple</name><quantityKG>5</quantityKG></fruit>"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().string(containsString("Unsupported Content-Type: application/xml")));
    }

    @Test
    void testUnhandledException_returns500() throws Exception {
        String validJson = """
        {
          "name": "Apple",
          "quantityKG": 3
        }
        """;

        when(fruitService.create(org.mockito.ArgumentMatchers.any()))
                .thenThrow(new RuntimeException("Unexpected failure"));

        mockMvc.perform(post("/fruit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}
