package ru.bm.eetp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bm.eetp.dto.ErrorDetail;
import ru.bm.eetp.exception.GWFaultError;
import ru.bm.eetp.exception.SimpleError;

@ControllerAdvice
@RestController
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GWFaultError.class)
    public ResponseEntity handleException(GWFaultError e, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(e.getprocessResult().getErrorCode(), e.getprocessResult().getErrorMessage() );
        return new ResponseEntity<ErrorDetail>(errorDetail, e.getprocessResult().getHttpCode());
    }

    @ExceptionHandler(SimpleError.class)
    public ResponseEntity handleException(SimpleError e, WebRequest request) {
        return new ResponseEntity<String>(e.getprocessResult().getErrorMessage(), e.getprocessResult().getHttpCode());
    }
}
