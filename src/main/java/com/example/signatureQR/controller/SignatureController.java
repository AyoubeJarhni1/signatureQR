package com.example.signatureQR.controller;

import static com.example.signatureQR.constant.ApiRoutes.*;

import com.example.signatureQR.dto.SignatureRequest;
import com.example.signatureQR.dto.SignatureResponse;
import com.example.signatureQR.exception.ApplicationException;
import com.example.signatureQR.exception.NotFoundException;
import com.example.signatureQR.service.SignatureService;
import com.example.signatureQR.service.impl.SignatureServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(API+ SIGNATURE)
public class SignatureController {

    private final SignatureService signatureService;
    private static final Logger log = LoggerFactory.getLogger(SignatureController.class);


    @Autowired
    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }


    @PostMapping
    public ResponseEntity<?> createSignature(@RequestBody SignatureRequest  signRequest) throws IOException {

            SignatureResponse response = signatureService.signDocument(signRequest.getNom(), signRequest.getPrenom());
            return ResponseEntity.ok(response);
    }

    @GetMapping("/"+ ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> verifyDocument(@PathVariable String id) {
        log.info("verify document {} signature state ",id);
        return ResponseEntity.ok(signatureService.verifyDocumentSignature(id));
    }


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleApplicationException(ApplicationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }




}
