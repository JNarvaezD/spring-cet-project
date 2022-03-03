package com.cet.Services;

import com.cet.Models.Cet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.CetRepository;
import com.cet.Repositories.InfoCetRepository;
import com.cet.dtos.CetDto;
import com.cet.utils.FileDelimitersUtil;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CetService {

    @Autowired
    private CetRepository cetRepository;

    @Autowired
    private InfoCetRepository infoCetRepository;

    public List<Cet> findAll() {
        return cetRepository.findAll();
    }

    public Optional<Cet> findOne(Long id) {
        return cetRepository.findOne(id);
    }

    public Cet save(CetDto cetDto) {
        Cet cet = Cet.builder().nombreArchivo(cetDto.getNombreArchivo())
                .fechaProceso(cetDto.getFechaProceso()).build();
        return cetRepository.save(cet);
    }

    public String uploadData(MultipartFile file) throws Exception {
        if(cetRepository.fileAlreadyUploaded(file.getOriginalFilename())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The file " + file.getOriginalFilename() + " has been already uploaded");
        }

        Character delimiter = FileDelimitersUtil.returnDelimiter(new CsvParser(
                new CsvParserSettings()).parseAllRecords(file.getInputStream()).toString(), '|', ',', ';');

        List<InfoCet> infoCetList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true, delimiter);
        CsvParser parser = new CsvParser(settings);

        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);

        if (parseAllRecords.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No content was found in the file");
        }

        CetDto cetDto = CetDto.builder().nombreArchivo(file.getOriginalFilename())
                .fechaProceso(LocalDate.now()).build();
        Cet cet = save(cetDto);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        parseAllRecords.forEach(record -> {
            InfoCet infoCet = InfoCet.builder()
                    .numeroCaso(record.getString(0))
                    .fechaDiagnostico(LocalDate.parse(record.getString(1), formatter))
                    .bduaAfiliadoId(record.getString(2))
                    .tipoId(record.getString(3))
                    .identificacion(record.getString(4))
                    .apellido1(record.getString(5))
                    .apellido2(record.getString(6))
                    .nombre1(record.getString(7))
                    .nombre2(record.getString(8))
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

        return "Upload successfully";
    }

}
