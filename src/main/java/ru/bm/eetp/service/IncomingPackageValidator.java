package ru.bm.eetp.service;

public interface IncomingPackageValidator {
    void validate(String content);

    boolean validateResult();
}
