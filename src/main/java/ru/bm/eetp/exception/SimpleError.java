package ru.bm.eetp.exception;

import ru.bm.eetp.dto.ProcessResult;

public class SimpleError extends RuntimeException {

    ProcessResult processResult;

    public SimpleError(ProcessResult processResult) {
        super("HTTP/1.1 503 Service Unavailable");
        this.processResult = processResult;
    }

    public ProcessResult getprocessResult() {
        return processResult;
    }
}
