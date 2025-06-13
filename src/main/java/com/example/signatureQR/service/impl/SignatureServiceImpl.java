package com.example.signatureQR.service.impl;

import com.example.signatureQR.constant.ApiErrorMessage;
import com.example.signatureQR.dto.SignatureResponse;
import com.example.signatureQR.dto.SignatureStatus;
import com.example.signatureQR.dto.VerificationResponse;
import com.example.signatureQR.exception.ApplicationException;
import com.example.signatureQR.exception.NotFoundException;
import com.example.signatureQR.model.Document;
import com.example.signatureQR.repository.SignatureRepository;
import com.example.signatureQR.service.SignatureService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;


@Service
public class SignatureServiceImpl implements SignatureService {

    public static final int QR_WIDTH = 250;
    public static final int QR_HEIGHT = 250;
    public static final String PNG = "PNG";
    private final SignatureRepository documentRepository;
    private static final Logger log = LoggerFactory.getLogger(SignatureServiceImpl.class);
    @Autowired
    public SignatureServiceImpl(SignatureRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public String generateQRCodeById(UUID id, int width, int height) throws  IOException {
        String uuidText = id.toString();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(uuidText, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, PNG, pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        }
        catch (WriterException e) {
            log.error("an error has occured while generating QR");
            throw new ApplicationException("An error has occured while generating QR",e);
        }
    }


    public SignatureResponse signDocument(String nom, String prenom) {
        try {
            UUID id = generateUniqueUUID();
            String qrCodeBase64 = generateQRCodeById(id, QR_WIDTH, QR_HEIGHT);
            saveSignedDocument(id, nom, prenom);
            return new SignatureResponse(id.toString(), qrCodeBase64);
        } catch (IOException e) {
            throw new ApplicationException("Erreur lors de la génération du QR code", e);
        }
    }



    public VerificationResponse verifyDocumentSignature(String id) {
        Document document = getDocumentByID(id);
        VerificationResponse verificationResponse=new VerificationResponse();
        verificationResponse.setNom(document.getNom());
        verificationResponse.setPrenom(document.getPrenom());

        if(validateNbrOfVerification(document) && !isExpired(document)){

            verificationResponse.setStatus(SignatureStatus.VALID.name());
            incrementNbrOfVerifications(document);
        }
        else {
            verificationResponse.setStatus(SignatureStatus.EXPIRED.name());
            incrementNbrOfVerifications(document);
        }
        return verificationResponse;
    }

    private Document getDocumentByID(String id) {
        Optional<Document> document = documentRepository.findById(UUID.fromString(id));
        return document.orElseThrow(()-> new NotFoundException(ApiErrorMessage.DOCUMENT_NOT_FOUND));
    }


    private Boolean validateNbrOfVerification(Document document){
        return document.getNbrVerifications() < 5;
    }

    private Boolean isExpired(Document document) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateSignature = document.getDateSignature();

        return dateSignature.isBefore(now.minusYears(1));
    }

    private void incrementNbrOfVerifications(Document document) {
        document.setNbrVerifications(document.getNbrVerifications() + 1);
        documentRepository.save(document);
    }
    private void saveSignedDocument(UUID id, String nom, String prenom) {
        Document doc = new Document(id, LocalDateTime.now(), nom, prenom);
        documentRepository.save(doc);
    }
    public UUID generateUniqueUUID() {
        return UUID.randomUUID();
    }
}

