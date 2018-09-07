package ru.bm.eetp.service;

public interface IncomingDocumentValidator {
    void validate(String document);

    boolean validateResult();
}
