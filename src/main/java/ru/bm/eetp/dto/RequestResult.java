package ru.bm.eetp.dto;

import org.springframework.http.HttpStatus;

public class RequestResult {

    private HttpStatus resultCode;
    private String resultBody;

    public RequestResult(HttpStatus resultCode, String resultBody) {
        this.resultCode = resultCode;
        this.resultBody = resultBody;
    }

    public HttpStatus getresultCode() {
        return resultCode;
    }

    public String getresultBody() {
        return resultBody;
    }
}
