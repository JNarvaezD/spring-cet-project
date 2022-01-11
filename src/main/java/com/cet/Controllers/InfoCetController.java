package com.cet.Controllers;

import com.cet.Models.InfoCet;
import com.cet.Services.InfoCetService;
import com.cet.dtos.InfoCetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/info-cets")
public class InfoCetController {

    @Autowired
    private InfoCetService infoCetService;

    @GetMapping
    public ResponseEntity<List<InfoCet>> get() {
        return new ResponseEntity<>(infoCetService.findAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<InfoCet> update(@RequestBody @Valid InfoCetDto request) {
        return new ResponseEntity<>(infoCetService.update(request.getId(), request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        boolean rowDeleted = infoCetService.delete(id);
        if(rowDeleted) {
            return new ResponseEntity<>("Registro eliminado con exito", HttpStatus.OK);
        }
        return new ResponseEntity<>("E registro con id " + id + " no ha sido encontrado", HttpStatus.NOT_FOUND);
    }

}
