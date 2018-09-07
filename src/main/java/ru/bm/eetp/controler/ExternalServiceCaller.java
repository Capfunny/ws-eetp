package ru.bm.eetp.controler;

import ru.bm.eetp.dto.RequestResult;

import java.net.URI;

public interface ExternalServiceCaller<T> {
    abstract RequestResult callExternalService(T body, URI uri);
    abstract RequestResult getRequestResult();
}
