package com.example.signatureQR.dto;


public class SignatureRequest {
    private String nom;
    private String prenom;

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public SignatureRequest(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public SignatureRequest() {

    }
}
