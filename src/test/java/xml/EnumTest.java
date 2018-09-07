package xml;

import org.springframework.http.HttpStatus;

public class EnumTest {

    public enum ProcessResult {
        OK(HttpStatus.OK, 0, ""),
        PACKAGE_VALIDATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -41, "Валидация пакета по XSD не пройдена"),
        MESSAGE_VALIDATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -41, "Валидация документа по XSD не пройдена"),
        SIGNATURE_VALIDATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -53, "Проверка ЭЦП не пройдена");

        private final HttpStatus httpCode;
        private final int errorCode;
        private final String errorMessage;

        ProcessResult(HttpStatus httpCode, int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.httpCode = httpCode;
            this.errorMessage = errorMessage;
        }

        public HttpStatus getHttpCode() {
            return httpCode;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

        public static void main(String[] args) {
            ProcessResult p = ProcessResult.MESSAGE_VALIDATION_ERROR;
            System.out.println(String.valueOf(p) +" " + p.httpCode +" " + p.errorCode + " " + p.errorMessage   );
        }
     }

