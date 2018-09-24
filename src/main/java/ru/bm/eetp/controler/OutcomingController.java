package ru.bm.eetp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.dto.RequestResult;
import ru.bm.eetp.dto.XMLPackage;
import ru.bm.eetp.exception.GWFaultError;
import ru.bm.eetp.exception.SimpleError;
import ru.bm.eetp.logging.Logger;
import ru.bm.eetp.service.OutcomingPackageProcessor;

import java.net.URI;

import static ru.bm.eetp.config.Utils.decodeBase64;


@RestController
public class OutcomingController {

    @Autowired private OutcomingPackageProcessor outcomingPackageProcessor;
    @Autowired private ExternalServiceCallerImpl<XMLPackage> externalServiceCaller;
    @Autowired private Logger logger;

    @PostMapping(value="/outcoming", produces="text/xml" )
    @ResponseStatus(code = HttpStatus.OK)
    public String endpointOut(@RequestBody String content,
                              @RequestParam(name = "remote_url", required=false) String remoteUrlBase64) throws Exception {
        RequestResult requestResult;
        String remoteUrl = null;

        try {
            remoteUrl = decodeBase64(remoteUrlBase64);
           /* UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
            if (! urlValidator.isValid(remoteUrl)){
                throw new Exception("плохой remoteUrl!");
            }*/
        }
        catch (Exception e)
        {
            logger.logError(ProcessResult.REMOTE_URL_ERROR.getErrorMessage(), remoteUrl);
            throw new GWFaultError(ProcessResult.REMOTE_URL_ERROR);
        }

        ProcessResult processResult = outcomingPackageProcessor.processing(content);

        if (processResult == ProcessResult.OK)
        {
            XMLPackage xmlPackage = outcomingPackageProcessor.getXMLPackage();
            requestResult = externalServiceCaller.callExternalService(xmlPackage, new URI(remoteUrl));
            if (requestResult.getresultCode() != HttpStatus.OK){
                logger.logError(processResult.getErrorMessage(), xmlPackage.toString());
                /*//обрабатываем сами - как выяснилось, это неверно!
                processResult = ProcessResult.SERVICE_UNAVAILABLE_ERROR;
                //возвращаем как есть
                return requestResult.getresultBody();*/
                throw new SimpleError(requestResult);
            }
            else {
                logger.logOutcoming(xmlPackage.toString());
                return processResult.getErrorMessage(); //TO-DO - надо понять, что все же возвращать!
            }
        }
        else {
            logger.logError(processResult.getErrorMessage(), content);
            throw new GWFaultError(processResult);
        }
    }

    @PostMapping(value="/dummy", produces="text/xml" )
    @ResponseStatus(code = HttpStatus.OK)
    public String endpointdummy(@RequestBody String content){
        return "HTTP/1.1 200 Ok\n<RequestBody>: \n" + content;
    }

    @PostMapping(value="/dummyerror", produces="text/xml")
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String endpointdummyerror(@RequestBody String content){
        return  "<GWFault xmlns=\"http://www.sbrf.ru/edo/gateway/common-0.1\">\n" +
                " <faultstring>DataPower error</faultstring>\n" +
                " <detail>\n" +
                " <code>-53</code>\n" +
                " <description>IT'S TEST DUMMYERROR!!!</description>\n" +
                " </detail>\n" +
                "</GWFault>";
    }

}
