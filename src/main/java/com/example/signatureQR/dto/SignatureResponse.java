package com.example.signatureQR.dto;

public  class SignatureResponse {
    public String uuid;
    public String qrCode;

    public SignatureResponse(String uuid, String qrCode) {
        this.uuid = uuid;
        this.qrCode = qrCode;
    }

}