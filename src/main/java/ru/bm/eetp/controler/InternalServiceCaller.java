package ru.bm.eetp.controler;

import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.dto.RequestResult;

public interface InternalServiceCaller {
    abstract RequestResult callInternalService(ProcessResult processResult, String body);
    abstract RequestResult getRequestResult();
}
