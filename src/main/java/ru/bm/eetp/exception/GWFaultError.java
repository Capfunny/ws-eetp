package ru.bm.eetp.exception;

import ru.bm.eetp.dto.ProcessResult;

public class GWFaultError extends RuntimeException {

    ProcessResult processResult;

    public GWFaultError(ProcessResult processResult) {
        super("HTTP/1.1 500 Internal Server error");
        this.processResult = processResult;
    }

    public ProcessResult getprocessResult() {
        return processResult;
    }

}
