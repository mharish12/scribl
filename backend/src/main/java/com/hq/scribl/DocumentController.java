package com.hq.scribl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hq.scribl.dto.DocumentMessageDto;

import java.util.List;

@RestController
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/api/documents")
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/api/documents/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable String id) {
        return documentService.getDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/documents")
    public Document createDocument(@RequestBody Document document) {
        return documentService.saveDocument(document);
    }

    @DeleteMapping("/api/documents/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @org.springframework.messaging.handler.annotation.MessageMapping("/edit")
    @org.springframework.messaging.handler.annotation.SendTo("/topic/doc")
    public DocumentMessageDto broadcastChange(DocumentMessageDto message) {
        documentService.saveContent(message.getDocumentId(), message.getContent());
        return message;
    }

}