package com.cet.integration;

import com.cet.CetApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CetApplication.class)
@AutoConfigureMockMvc
class CetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenFileShouldUploadAllData() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\files\\CCF033COVID15012021.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        mockMvc.perform(multipart("/cets/upload-file").file(file))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void givenFileAlreadyUploadedShouldReturn422StatusCode() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\files\\CCF033COVID15012021.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        mockMvc.perform(multipart("/cets/upload-file").file(file))
                .andExpect(status().is(422)).andReturn();
    }

    @Test
    void givenEmptyFileShouldReturn204StatusCode() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\files\\CCF033COVID15012021EMPTY.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        mockMvc.perform(multipart("/cets/upload-file").file(file))
                .andExpect(status().is(204)).andReturn();
    }

    @Test
    void givenFileWithTwoDifferentDelimitersShouldThrowException() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\files\\CCF033COVID15012021ONEROWTWODELIMITERS.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(multipart("/cets/upload-file").file(file)).andReturn()
        );
        assertEquals(
                "More than one delimiters were found in the file",
                exception.getMessage().substring(exception.getMessage().indexOf(": ") + 2)
        );
    }

    @Test
    void givenFileWithoutAllowedDelimitersShouldReturnException() throws Exception {
        Path getFile = Paths.get("C:\\Users\\susje\\Downloads\\files\\CCF033COVID15012021NOTALLOWEDDELIMITERS.TXT");
        String content = Files.readString(getFile, StandardCharsets.ISO_8859_1);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                getFile.toFile().getName(),
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(multipart("/cets/upload-file").file(file)).andReturn()
        );

        assertEquals("File is not separated with the allowed delimiters", exception.getMessage().substring(exception.getMessage().indexOf(": ") + 2));
    }

}