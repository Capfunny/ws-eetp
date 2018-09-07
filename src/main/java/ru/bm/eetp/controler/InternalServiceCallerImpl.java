package ru.bm.eetp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bm.eetp.config.ConfigProperties;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.dto.RequestResult;

import java.net.URI;
import java.net.URISyntaxException;

import static ru.bm.eetp.config.Constants.*;
import static ru.bm.eetp.config.Utils.getDummyURL;

@Component
public class InternalServiceCallerImpl implements InternalServiceCaller {

    @Autowired  private ConfigProperties configProperties;
    @Autowired  private RestServiceCaller<String> restServiceCaller;

    @Override
    public RequestResult callInternalService(ProcessResult processResult, String body){
        URI uri = getURI(processResult != ProcessResult.OK);
        restServiceCaller.call(body, uri);
        return restServiceCaller.getRequestResult();
    }

    @Override
    public RequestResult getRequestResult(){
        return restServiceCaller.getRequestResult();
    }

    public URI getURI(boolean isError){
        String url;

        if (DEBUG){ //если включен DEBUG
            url = getDummyURL();
        }
        else{ //обычгая ветвь исполнения
            if (isError){
                url = configProperties.internal_error_url;
            }
            else {
                url =  configProperties.internal_ok_url;
            }
        }

        try {
            return new URI(url);
        }
        catch (URISyntaxException e)
        {
            return null;
        }
    }
}
