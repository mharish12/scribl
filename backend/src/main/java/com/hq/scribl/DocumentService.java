package com.hq.scribl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(String id) {
        return documentRepository.findById(id);
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public void deleteDocument(String id) {
        documentRepository.deleteById(id);
    }

    @Transactional
    public Document saveContent(String id, String content) {
        Document doc = documentRepository.findById(id).orElseGet(() -> {
            Document d = new Document();
            d.setId(id);
            return d;
        });
        doc.setContent(content);
        return documentRepository.save(doc);
    }
}