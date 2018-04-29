package infobip.interview.task.urlshortener.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class AccountIdAlreadyTakenException extends RuntimeException {

    private String description;
    private Boolean success;

    public AccountIdAlreadyTakenException(String description, Boolean success) {
        super();
        this.description = description;
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


}
