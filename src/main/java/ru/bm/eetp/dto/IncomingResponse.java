package ru.bm.eetp.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Capfunny on 22.08.2018.
 */
@ResponseStatus(code = HttpStatus.OK, reason = "HTTP/1.1 200 Ok")
public class IncomingResponse {

    public IncomingResponse() {
    }

    @Override
    public String toString() {
        return "widhwiuhiuh";
    }
}
