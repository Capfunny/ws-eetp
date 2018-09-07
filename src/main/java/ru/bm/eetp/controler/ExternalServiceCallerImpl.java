package ru.bm.eetp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bm.eetp.dto.RequestResult;

import java.net.URI;

@Component
public class ExternalServiceCallerImpl<T> implements ExternalServiceCaller<T> {

    @Autowired private RestServiceCaller<T> restServiceCaller;

    @Override
    public RequestResult callExternalService(T body, URI uri){
        restServiceCaller.call(body, uri);
        return restServiceCaller.getRequestResult();
    }

    @Override
    public RequestResult getRequestResult(){
        return restServiceCaller.getRequestResult();
    }
}
