package ru.bm.eetp.controler;

import ru.bm.eetp.dto.RequestResult;

import java.net.URI;


public interface RestServiceCaller<T> {

    abstract void call(T body, URI uri);

    abstract RequestResult getRequestResult();

}

