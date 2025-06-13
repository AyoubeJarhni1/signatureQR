package com.example.signatureQR.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class Document {

    @Id
    private UUID id;

    private String nom;
    private String prenom;
    private LocalDateTime dateSignature;
    private int nbrVerifications;


    public Document(UUID uuid, LocalDateTime now, String nom, String prenom) {
        this.id = uuid;
        this.nom = nom;
        this.prenom = prenom;
        this.dateSignature = now;

    }

    public Document() {

    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getDateSignature() {
        return this.dateSignature;
    }

    public void setDateSignature(LocalDateTime dateSignature) {
        this.dateSignature = dateSignature;
    }

    public int getNbrVerifications() {
        return nbrVerifications;
    }

    public void setNbrVerifications(int nbrVerifications) {
        this.nbrVerifications = nbrVerifications;
    }


}
