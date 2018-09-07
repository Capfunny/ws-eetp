package ru.bm.eetp.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import ru.bm.eetp.config.Constants;

@JacksonXmlRootElement(localName = "GWFault", namespace = Constants.ERROR_NAMESPACE)
public class ErrorDetail {
    @JacksonXmlProperty(namespace = Constants.ERROR_NAMESPACE)
    private String faultString;
    @JacksonXmlProperty(namespace = Constants.ERROR_NAMESPACE)
    private Detail detail;

    public ErrorDetail(int code, String description) {
        this.faultString = "DataPower error";
        this.detail = new Detail(code, description);
    }

    public String getFaultString() {
        return faultString;
    }

    public Detail getDetail() {
        return detail;
    }

    public static class Detail {
        @JacksonXmlProperty(namespace = Constants.ERROR_NAMESPACE)
        private int code;
        @JacksonXmlProperty(namespace = Constants.ERROR_NAMESPACE)
        private String description;

        public Detail(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

}
