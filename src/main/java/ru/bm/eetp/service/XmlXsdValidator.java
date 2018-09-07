package ru.bm.eetp.service;

public interface XmlXsdValidator {

    boolean validate(String content, String schemaFile);
}
