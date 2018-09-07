package ru.bm.eetp.service;

import java.util.Optional;

public interface IncomingDocumentExtractor {

    public abstract Optional<String> extract(String content);
}
