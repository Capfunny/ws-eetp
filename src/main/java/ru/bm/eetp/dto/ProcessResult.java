package ru.bm.eetp.dto;

import org.springframework.http.HttpStatus;

public enum ProcessResult {
    OK(HttpStatus.OK, 0, "HTTP/1.1 200 Ok"),
    PACKAGE_VALIDATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -41, "Валидация пакета по XSD не пройдена"),
    DOCUMENT_VALIDATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -41, "Валидация документа по XSD не пройдена"),
    SIGNATURE_VALIDATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -53, "Проверка ЭЦП не пройдена"),
    SERVICE_UNAVAILABLE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, 0, "HTTP/1.1 503 Service Unavailable"),
    SIGNING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 0, "600 Signing error");

    private final HttpStatus httpCode;
    private final int errorCode;
    private final String errorMessage;

     ProcessResult(HttpStatus httpCode, int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}