package com.example.signatureQR.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
public class Document {

    @Id
    private String uuid ;

    private String nom ;
    private String prenom ;
    private LocalDateTime dateSignature;
    private int nbrVerifications ;


    public Document(String uuid, LocalDateTime now, String nom, String prenom) {
        this.uuid = uuid;
        this.nom = nom;
        this.prenom = prenom;
        this.dateSignature = now;

    }

    public Document() {

    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public LocalDateTime getDateSignature() {
        return dateSignature;
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
