package integration;

import com.cet.CetApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CetApplication.class)
@AutoConfigureMockMvc
class InfoCetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void get() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/info-cets");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void update() {
    }

    @Test
    void delete() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/info-cets/" + 1);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }
}