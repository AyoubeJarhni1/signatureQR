package com.example.signatureQR.services;

import com.example.signatureQR.DTO.SignatureResponse;
import com.example.signatureQR.DTO.VerificationResponse;
import com.example.signatureQR.models.Document;
import com.example.signatureQR.repositories.DocumentRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public UUID generateUniqueUUID() {
        return UUID.randomUUID();
    }


    public String generateQRCodeFromUUID(UUID uuid, int width, int height) throws WriterException, IOException {
        String uuidText = uuid.toString();

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(uuidText, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(pngData);
    }


    public Document saveSignedDocument(UUID uuid, String nom, String prenom) {
        Document doc = new Document(uuid.toString(), LocalDateTime.now(), nom, prenom);
        return documentRepository.save(doc);
    }


    public SignatureResponse signerDocument(String nom, String prenom) throws WriterException, IOException {
        UUID uuid = generateUniqueUUID();
        String qrCodeBase64 = generateQRCodeFromUUID(uuid, 250, 250);
        saveSignedDocument(uuid, nom, prenom);
        return new SignatureResponse(uuid.toString(), qrCodeBase64);
    }



    public Document getDocumentByUUID(String uuid) {
       Optional<Document> document = documentRepository.findById(uuid);
       if (document.isPresent()) {
    return document.get();
       }
return null;
    }

    public Boolean validNbrVerification(Document document){
        if (document.getNbrVerifications()<5){
            return true;
        }
        return false;
    }

    public Boolean estExpiré(Document document) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateSignature = document.getDateSignature();

        return dateSignature.isBefore(now.minusYears(1));
    }

 public void incrementerNbrVerifications(Document document) {
        document.setNbrVerifications(document.getNbrVerifications() + 1);
     documentRepository.save(document);
 }


 public VerificationResponse verifierDocumentSigned(String uuid) {
        Document document = getDocumentByUUID(uuid);
        VerificationResponse verificationResponse=new VerificationResponse();
        verificationResponse.setNom(document.getNom());
        verificationResponse.setPrenom(document.getPrenom());

        if(validNbrVerification(document) && !estExpiré(document)){

            verificationResponse.setStatus("valid");
            incrementerNbrVerifications(document);
        }
        else {
            verificationResponse.setStatus("expiré");
            incrementerNbrVerifications(document);
        }
        return verificationResponse;
 }


}
