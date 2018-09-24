package ru.bm.eetp.exception;

import ru.bm.eetp.dto.RequestResult;

public class SimpleError extends RuntimeException {

    RequestResult requestResult;

    public SimpleError(RequestResult requestResult) {
        super(requestResult.getresultBody());
        this.requestResult = requestResult;
    }

    public RequestResult getRequestResult() {
        return requestResult;
    }
}
