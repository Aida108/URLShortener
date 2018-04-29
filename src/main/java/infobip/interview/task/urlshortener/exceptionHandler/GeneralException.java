package infobip.interview.task.urlshortener.exceptionHandler;

import org.springframework.http.HttpStatus;

public class GeneralException extends RuntimeException{
    private String exceptionMessage;
    private HttpStatus httpStatus;

    public GeneralException (String exceptionMessage, HttpStatus httpStatus) {
        super();
        this.exceptionMessage = exceptionMessage;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
