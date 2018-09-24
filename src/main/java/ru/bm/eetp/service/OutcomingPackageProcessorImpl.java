package ru.bm.eetp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.bm.eetp.config.Utils;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.dto.XMLPackage;
import ru.bm.eetp.signature.SignatureProcessor;


@Component
public class OutcomingPackageProcessorImpl implements OutcomingPackageProcessor {

    private XMLPackage xmlPackage;

    private SignatureProcessor signatureProcessor;

    public OutcomingPackageProcessorImpl(@Qualifier("vtbToEetpSignatureProcessor") SignatureProcessor signatureProcessor) {
        this.signatureProcessor = signatureProcessor;
    }

    @Override
    public ProcessResult processing(String content){
        ProcessResult processResult = ProcessResult.SIGNING_ERROR;
        try{
            String signature = signatureProcessor.getSignature(content);
            if (signature != null) {
                xmlPackage = new XMLPackage(content, signature);
                processResult = ProcessResult.OK;
            }
        }
        catch (Exception e)
        { /*DO NOTHING*/
            Utils.debugg("error=>", e.getMessage());
            e.printStackTrace();
        }

        return processResult;
    }

    @Override
    public XMLPackage getXMLPackage(){
        return xmlPackage;
    }

}
