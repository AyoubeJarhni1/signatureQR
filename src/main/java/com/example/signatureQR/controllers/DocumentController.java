package com.example.signatureQR.controllers;

import com.example.signatureQR.DTO.SignatureRequest;
import com.example.signatureQR.DTO.SignatureResponse;
import com.example.signatureQR.DTO.VerificationResponse;
import com.example.signatureQR.services.DocumentService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController( DocumentService documentService) {
        this.documentService = documentService;
    }


    @PostMapping("/signer")
    public ResponseEntity<?> signerDocument(@RequestBody SignatureRequest  signRequest) {
        try {
            SignatureResponse response = documentService.signerDocument(signRequest.getNom(), signRequest.getPrenom());
            return ResponseEntity.ok(response);
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de la génération du QR code");
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyDocument(@RequestParam String uuid) {
        try {
            VerificationResponse response = documentService.verifierDocumentSigned(uuid);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur lors de la vérification : " + e.getMessage());
        }
    }



}
