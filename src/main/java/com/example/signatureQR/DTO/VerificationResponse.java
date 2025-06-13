package com.example.signatureQR.DTO;

public class VerificationResponse {
    private String nom;
    private String prenom;
    private String status;

    public VerificationResponse(String nom, String prenom, String status) {
        this.nom = nom;
        this.prenom = prenom;
        this.status = status;
    }

    public VerificationResponse() {

    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getStatus() {
        return status;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
