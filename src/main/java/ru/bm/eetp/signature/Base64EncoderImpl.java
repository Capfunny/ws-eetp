package ru.bm.eetp.signature;

import org.springframework.stereotype.Component;
import ru.bm.eetp.config.Utils;

@Component
public class Base64EncoderImpl implements Base64Encoder {
    @Override
    public String encode(byte[] bytes) {
        return Utils.encodeBase64(bytes);
    }
}
