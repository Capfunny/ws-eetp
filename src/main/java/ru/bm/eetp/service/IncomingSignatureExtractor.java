package ru.bm.eetp.service;

import java.util.Optional;

public interface IncomingSignatureExtractor {

    public abstract Optional<String> extract(String content);
}
