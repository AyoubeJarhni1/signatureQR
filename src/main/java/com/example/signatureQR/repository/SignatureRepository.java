package com.example.signatureQR.repository;

import com.example.signatureQR.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SignatureRepository extends JpaRepository<Document, UUID> {

}
