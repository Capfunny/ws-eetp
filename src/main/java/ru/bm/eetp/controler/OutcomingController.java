package ru.bm.eetp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.dto.RequestResult;
import ru.bm.eetp.dto.XMLPackage;
import ru.bm.eetp.exception.SimpleError;
import ru.bm.eetp.service.OutcomingPackageProcessor;
import java.net.URI;

import static ru.bm.eetp.config.Constants.DEBUG;
import static ru.bm.eetp.config.Utils.*;


@RestController
public class OutcomingController {

    @Autowired private OutcomingPackageProcessor outcomingPackageProcessor;
    @Autowired private ExternalServiceCallerImpl<XMLPackage> externalServiceCaller;

    @PostMapping(value="/outcoming", produces="text/xml" )
    @ResponseStatus(code = HttpStatus.OK)
    public String endpointOut(@RequestBody String content,
                              @RequestParam(name = "remote_url", required=false) String remoteUrlBase64) throws Exception {
        RequestResult requestResult;
        String remoteUrl;

        if (DEBUG) {
            remoteUrl = getDummyURL();
        }
        else {
            remoteUrl = decodeBase64(remoteUrlBase64);
        }

        ProcessResult processResult = outcomingPackageProcessor.processing(content);

        if (processResult == ProcessResult.OK)
        {
            XMLPackage xmlPackage = outcomingPackageProcessor.getXMLPackage();
            requestResult = externalServiceCaller.callExternalService(xmlPackage, new URI(remoteUrl));
            return requestResult.getresultBody();
            //return processResult.getErrorMessage(); //TO-DO - надо понять, что все же возвращать!
        }
        throw new SimpleError(processResult);
    }

    @PostMapping(value="/dummy", produces="text/xml" )
    @ResponseStatus(code = HttpStatus.OK)
    public String endpointOut(@RequestBody String content){
        System.out.println(content);
        return "HTTP/1.1 200 Ok\n<RequestBody>: \n" + content;
    }

}
