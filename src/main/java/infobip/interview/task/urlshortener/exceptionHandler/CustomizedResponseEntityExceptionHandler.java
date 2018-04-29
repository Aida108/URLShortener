package infobip.interview.task.urlshortener.exceptionHandler;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountIdAlreadyTakenException.class)
    public final ResponseEntity<?> handleAccountAlreadyTakenException(AccountIdAlreadyTakenException ex) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("description", ex.getDescription());
        jsonResponse.put("success", ex.getSuccess());

        return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<?> handleGeneralException(GeneralException ex) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("HttpStatus", ex.getHttpStatus());
        jsonResponse.put("Error message", ex.getExceptionMessage());

        return new ResponseEntity<>(jsonResponse, ex.getHttpStatus());
    }
}
