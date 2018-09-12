package ru.bm.eetp.service;

import java.util.Optional;

public interface TagExtractor {

    abstract Optional<String> extract(String tagName, String content);
}
