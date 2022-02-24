package com.cet.Controllers;

import com.cet.Services.CetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController @RequestMapping("/cets")
public class CetController {

    @Autowired
    private CetService cetService;

    @PostMapping("/upload-data")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        return new ResponseEntity<>(cetService.uploadData(file), HttpStatus.CREATED);
    }

}
