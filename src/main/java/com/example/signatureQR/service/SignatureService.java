package com.example.signatureQR.service;

import com.example.signatureQR.dto.SignatureResponse;
import com.example.signatureQR.dto.VerificationResponse;

public interface SignatureService {

    public SignatureResponse signDocument(String nom, String prenom);
    public VerificationResponse verifyDocumentSignature(String id);


}