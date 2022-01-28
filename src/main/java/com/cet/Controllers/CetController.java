package com.cet.Controllers;

import com.cet.Models.Cet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.InfoCetRepository;
import com.cet.Services.CetService;
import com.cet.dtos.CetDto;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController @RequestMapping("/cets")
public class CetController {

    @Autowired
    private CetService cetService;

    @Autowired
    private InfoCetRepository infoCetRepository;

    @PostMapping("/upload-data")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            if(cetService.findByNombreArchivo(file.getOriginalFilename())) {
                return new ResponseEntity<>(
                        "The file " + file.getOriginalFilename() + " has been already uploaded",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }

            List<InfoCet> infoCetList = new ArrayList<>();

            InputStream inputStream = file.getInputStream();
            CsvParserSettings settings = new CsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            settings.setDelimiterDetectionEnabled(true, '|', ',');
            CsvParser parser = new CsvParser(settings);

            List<Record> parseAllRecords = parser.parseAllRecords(inputStream);

            if (parseAllRecords.size() == 0) {
                return new ResponseEntity<>("No content was found in the file", HttpStatus.NO_CONTENT);
            }

            CetDto cetDto = CetDto.builder().nombreArchivo(file.getOriginalFilename())
                    .fechaProceso(LocalDate.now())
                    .build();

            Cet cet = cetService.save(cetDto);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            parseAllRecords.forEach(record -> {
                InfoCet infoCet = InfoCet.builder()
                        .numeroCaso(record.getString(0))
                        .fechaDiagnostico(LocalDate.parse(record.getString(1), formatter))
                        .bduaAfiliadoId(record.getString(2))
                        .tipoId(record.getString(3))
                        .identificacion(record.getString(4))
                        .nombre1(record.getString(5))
                        .nombre2(record.getString(6))
                        .apellido1(record.getString(7))
                        .apellido2(record.getString(8))
                        .fechaNacimiento(LocalDate.parse(record.getString(9), formatter))
                        .sexo(record.getString(10))
                        .codEps(record.getString(11))
                        .telefonoFijo(record.getString(12))
                        .celular(record.getString(13))
                        .covidContacto(Integer.parseInt(record.getString(14)))
                        .cetId(cet.getId())
                        .build();
                infoCetList.add(infoCet);
            });
            infoCetRepository.saveAll(infoCetList);

            return new ResponseEntity<>("Upload successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

}
