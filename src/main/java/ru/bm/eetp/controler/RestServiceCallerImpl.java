package ru.bm.eetp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.bm.eetp.config.Utils;
import ru.bm.eetp.dto.RequestResult;

import java.net.URI;

@Component
public class RestServiceCallerImpl<T> implements RestServiceCaller<T> {

    @Autowired private RestTemplate restTemplate;

    private RequestResult requestResult = null;

    @Override
    public void call(T body, URI uri) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE);
            RequestEntity<T> requestEntity = new RequestEntity(body, httpHeaders, HttpMethod.POST, uri);

            ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
            requestResult = new RequestResult(response.getStatusCode(), response.getBody());
            Utils.debugg("\n\n Sended package to ==>", uri.toString() + "\n\n");
        }
        catch (Exception e)
        {
            requestResult = new RequestResult(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
            Utils.debugg("\n\n Can't send package to ==>", uri.toString() + "\n\n");
        }
    }

    @Override
    public RequestResult getRequestResult(){
        return requestResult;
    }
}

