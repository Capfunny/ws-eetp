package ru.bm.eetp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bm.eetp.config.Constants;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.dto.IncomingResponse;
import ru.bm.eetp.dto.RequestResult;
import ru.bm.eetp.exception.GWFaultError;
import ru.bm.eetp.exception.SimpleError;
import ru.bm.eetp.service.IncomingPackageProcessor;

@RestController
public class IncomingController {

    @Autowired private IncomingPackageProcessor incomingPackageProcessor;
    @Autowired private InternalServiceCaller internalServiceCaller;

    @PostMapping(value="/incoming", produces="text/xml" )
    @ResponseStatus(code = HttpStatus.OK)
    public String endpointIn(@RequestBody String content) throws Exception {
        ProcessResult processResult = incomingPackageProcessor.processing(content);
        RequestResult requestResult = internalServiceCaller.callInternalService(processResult, content);

        if (requestResult.getresultCode() != HttpStatus.OK) {
            processResult = ProcessResult.SERVICE_UNAVAILABLE_ERROR;
        }

        switch(processResult)
        {
            case OK :{
                if (Constants.DEBUG) {
                    return processResult.getErrorMessage() + "\n\n<ResultBody>: \n" + requestResult.getresultBody();
                }
                else{
                    return processResult.getErrorMessage();}
                }
            case SERVICE_UNAVAILABLE_ERROR :{
                throw new SimpleError(processResult);
                }
            default:
                throw new GWFaultError(processResult);
        }
    }

    @PostMapping(value="/error_500", produces="text/xml")
    public IncomingResponse endpoint500(@RequestBody String content) {
        if(1 != 2) {
            throw new GWFaultError(ProcessResult.SIGNATURE_VALIDATION_ERROR);
        }
        return new IncomingResponse();
    }

    @PostMapping(value="/error_503", produces="text/xml")
    public IncomingResponse endpoint503(@RequestBody String content) {
        if(1 != 2) {
            throw new SimpleError(ProcessResult.SERVICE_UNAVAILABLE_ERROR);
        }
        return new IncomingResponse();
    }

    @PostMapping(value="/config", produces="text/xml")
    public IncomingResponse endpointconfig(@RequestBody String content) {
       return new IncomingResponse();
    }
}
